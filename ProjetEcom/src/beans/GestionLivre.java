package beans;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.myfaces.util.FilenameUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import Tools.ElasticSearchTools;
import model.Auteur;
import model.Editeur;
import model.Genre;
import model.Livre;
import model.Promotion;

@Stateless
public class GestionLivre {

	@PersistenceContext(unitName = "Database-unit")
	private EntityManager em;
	
	@EJB()
	private ConfigurationGenerale conf;
	@EJB()
	private GestionPromotion promoBean;
	
	public Livre creerLivre(String nom, List<Auteur> a, Editeur e, Genre g, String isbn, int nbpage, float prix,
			String langue, String langueOriginale, String couverture, Promotion promo, String resume, Date datePub) throws Exception {

		Livre l = new Livre(nom, isbn, datePub, nbpage, (float) prix, langue, langueOriginale);
		String name ="";
		try {
			URL url = new URL(couverture);
			name = FilenameUtils.getName(url.getPath());
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		String couvertureUrl = conf.get("PATH_SERVER")+"/images/"+name+".png";
		/*System.out.println(couvertureUrl);
		try {
			Tools.sauvegarderImage(couverture, 400, 600, couvertureUrl);
			couvertureUrl = "/images/"+name+".png";
		} catch (Exception e1) {
			e1.printStackTrace();
			couvertureUrl = couverture;//"images/defaultCouv.png";
		}*/
		couvertureUrl = couverture;
		l.setNomCouverture(couvertureUrl);

		l.setResume(resume);
		l.setPromotion(promo);
		l.setEditeur(e);
		l.setGenre(g);

		if (e != null)
			e.addLivre(l);
		if (g != null)
			g.addLivre(l);
		if (promo != null)
			promo.setLivre(l);

		if (a != null) {
			Iterator<Auteur> ita = a.iterator();
			while (ita.hasNext()) {
				Auteur atemp = ita.next();
				l.addAuteur(atemp);
				atemp.addLivre(l);
			}
		}

		em.persist(l);
		
		try {
			ElasticSearchTools.enregistrerDansLIndexage("http://"+conf.get("IP_ELASTICSEARCH")+":"+conf.get("PORT_ELASTICSEARCH"), l);
		} catch (Exception ex) {
			System.err.println("Erreur durant l'indexage du livre dans elasticsearch : " + ex.getMessage());
		}
		
		return l;
	}

	public List<Livre> getLesLivres() {
		Query q = em.createQuery("select OBJECT(b) from Livre b");
		List<Livre> list = (List<Livre>) q.getResultList();
		return list;
	}

	public List<Livre> getLesLivresEnPromotion() {

		Query q = em.createQuery("select p.livre from Promotion p where p.dateFin > CURRENT_DATE");
		List<Livre> list = (List<Livre>) q.getResultList();
		return list;
	}

	public Livre getLivreAvecId(long id) {
		Query q = em.createQuery("select OBJECT(b) from Livre b where b.id = " + id);
		Livre livre = (Livre) q.getSingleResult();
		return livre;
	}

	public Set<Livre> getDixLivresLesPlusVendu() {

		Query q = em
				.createQuery("SELECT v.livre, count(v.livre) as truc FROM Vente v GROUP BY v.livre order by truc Desc");
		q.setMaxResults(10);
		List<Object[]> list = q.getResultList();

		Map<Livre, Long> resultMap = new HashMap<Livre, Long>(list.size());
		for (Object[] result : list)
			resultMap.put((Livre) result[0], (Long) result[1]);

		return resultMap.keySet();
	}

	public List<CoupleLivreVente> recherche(String requeteBarre, int prixMin, int prixMax, String genre, int avisMin)
			throws Exception {

		String req = ElasticSearchTools.rechercheElasticSearch(requeteBarre, prixMin, prixMax, genre, avisMin);
		System.out.println(req);

		InputStream is = ElasticSearchTools.doRequest("http://"+conf.get("IP_ELASTICSEARCH")+":"+conf.get("PORT_ELASTICSEARCH")+"/livres/_search", "GET", req);

		BufferedReader rd = new BufferedReader(new InputStreamReader(is));

		JSONObject json = (JSONObject) new JSONParser().parse(rd);
		System.out.println(json);
		rd.close();
		String str = "select OBJECT(b) from Livre b where b.id=";

		JSONObject hits1 = (JSONObject) json.get("hits");
		System.out.println(hits1.size());
		JSONArray hits2 = (JSONArray) hits1.get("hits");
		System.out.println(hits2.size());
		if (hits2.size() != 0) {
			for (int i = 0; i < hits2.size(); i++) {
				JSONObject tmp = (JSONObject) hits2.get(i);
				String id = (String) tmp.get("_id");
				if (i != hits2.size() - 1)
					str += id + " or b.id= ";
				else
					str += id;
			}
			Query q = em.createQuery(str);
			List<Livre> list = (List<Livre>) q.getResultList();
			List<CoupleLivreVente> res = new LinkedList<>();
			for (int i = 0; i < list.size(); i++) {
				res.add(new CoupleLivreVente(list.get(i), list.get(i).getLesVentes().size()));
			}
			System.out.println(res.size());
			return res;
		} else {
			return new LinkedList<>();
		}
	}

	/**
	 * Recherche les livres qui possedent -tous les mots du champ texte -un des
	 * genres
	 */
	public List<Livre> getLivreRecherche(String texte, List<String> genre) {

		String[] s = texte.split(" "); // recupÃ©ration de la liste des mots
		String requete = "SELECT OBJECT(l) FROM Livre l ";
		if (s.length > 0 || !genre.isEmpty()) {
			requete = requete + "WHERE ";
			boolean b = false; // ajout d'un AND / OR ?
			int compteur = 0;
			// recherche a partir de l'entree de la barre de recherche

			if (s.length > 0) {
				// Les livres possedent dans leur titre tous les mots prÃ©sents
				// dans texte
				requete = requete + "( ";
				compteur = 0;
				while (compteur < s.length - 1) {
					requete = requete + "UPPER(l.titre) LIKE '%" + s[compteur].toUpperCase() + "%' AND ";
					compteur++;
				}
				requete = requete + "UPPER(l.titre) LIKE '%" + s[compteur].toUpperCase() + "%' ";
				b = true;

				requete = requete + "OR ";
				// PARTIE AUTEUR A MODIF
				compteur = 0;
				while (compteur < s.length - 1) {
					requete = requete + "UPPER(l.titre) LIKE '%" + s[compteur].toUpperCase() + "%' AND ";
					compteur++;
				}
				requete = requete + "UPPER(l.titre) LIKE '%" + s[compteur].toUpperCase() + "%' ";
				requete = requete + ") ";

			}

			// Recherche a partir de la liste de genre
			// Les livres font parties d'un des genres de la liste :genre
			if (!genre.isEmpty()) {
				if (b)
					requete = requete + "AND ( ";
				compteur = 0;
				while (compteur < genre.size() - 1) {
					requete = requete + "l.genre.nom = \"" + genre.get(compteur) + "\" OR ";
					compteur++;
				}
				requete = requete + "l.genre.nom = \"" + genre.get(compteur) + "\" ";
				if (b)
					requete = requete + ") ";
				b = true;
			}
		}
		// requete="SELECT OBJECT(l) FROM Livre l Where l.titre LIKE '%et%' AND
		// l.titre LIKE '%Walking%' OR l.genre.nom=\"Manga\"";
		Query q = em.createQuery(requete);
		List<Livre> list = (List<Livre>) q.getResultList();
		return list;
	}

	public void supprimerTous() {
		Query q = em.createNativeQuery("DELETE FROM Livre");
		Query q2 = em.createNativeQuery("ALTER TABLE Livre {ALTER id RESTART WITH 0} ");
		q.executeUpdate();
		q2.executeUpdate();
	}

	public void ajouterPromo(Long id, int taux, String dateD, String dateF) {
		Livre l = em.find(Livre.class, id);
		if(l != null){
			
			promoBean.creerPromotion(l, taux, dateD, dateF);
			
		}else{
			
			System.out.println("Ajout promo :  Livre null !!!!!");
			return;
			
		}
		
	}

}
