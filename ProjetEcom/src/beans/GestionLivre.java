package beans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import model.Auteur;
import model.Editeur;
import model.Genre;
import model.Livre;
import model.Promotion;
import tools.ElasticSearchTools;

/**
 * Bean gérant les livres
 * @author Clement
 *
 */
@Stateless
public class GestionLivre {

	@PersistenceContext(unitName = "Database-unit")
	private EntityManager			em;

	@EJB()
	private ConfigurationGenerale	conf;
	@EJB()
	private GestionPromotion		promoBean;

	/**
	 * Fonction servant a creer un livre proprement
	 * @param titre Le titre du livre
	 * @param a La liste des auteurs du livre
	 * @param e L'editeur du livre
	 * @param g Le genre du livre
	 * @param isbn L'isbn du livre
	 * @param nbpage Le nombre de pages du livre
	 * @param prix Le prix du livre
	 * @param langue La langue du livre
	 * @param langueOriginale La langue originale du livre
	 * @param couverture L'url vers la couverture du livre
	 * @param promo L'eventuelle promotion du livre
	 * @param resume Le resume du livre
	 * @param datePub La date de publication du livre (dd,mm,yyyy)
	 * @return Un nouveau livre
	 */
	public Livre creerLivre(String titre, List<Auteur> a, Editeur e, Genre g, String isbn, int nbpage, float prix, String langue, String langueOriginale, String couverture, Promotion promo, String resume, Date datePub) {

		Logger logger = Logger.getAnonymousLogger();
		Livre l = new Livre(titre, isbn, datePub, nbpage, (float) prix, langue, langueOriginale);

		l.setNomCouverture(couverture);

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
			ElasticSearchTools.enregistrerDansLIndexage("http://" + conf.get("IP_ELASTICSEARCH") + ":" + conf.get("PORT_ELASTICSEARCH"), l);
		} catch (Exception ex) {
			logger.log(Level.FINE, "an exception was thrown : Erreur durant l'indexage du livre dans elasticsearch :", ex);
		}

		return l;
	}
	
	/** Fonction donnant tous les livres de la base
	 * @return Une lisye d'editeurs
	 */
	public List<Livre> getLesLivres() {
		Query q = em.createQuery("select OBJECT(b) from Livre b");
		return (List<Livre>) q.getResultList();
	}

	/** Fonction donnant tous les livres en promotion de la base
	 * @return Une lisye d'editeurs
	 */
	public List<Livre> getLesLivresEnPromotion() {

		Query q = em.createQuery("select p.livre from Promotion p where p.dateFin > CURRENT_DATE");
		return (List<Livre>) q.getResultList();
	}

	/**
	 * Fonction donnant un livre identifié par son id
	 * @param id L'id du livre a chercher
	 * @return Un livre d'id  id ou null si l'id est invalide
	 */
	public Livre getLivreAvecId(long id) {
		return em.find(Livre.class, id);
	}

	/** Fonction donnant les 10 livres les plus vendus de la base
	 * @return Une lisye d'editeurs
	 */
	public Set<Livre> getDixLivresLesPlusVendu() {

		Query q = em.createQuery("SELECT v.livre, count(v.livre) as truc FROM Vente v GROUP BY v.livre order by truc Desc");
		q.setMaxResults(10);
		List<Object[]> list = q.getResultList();

		Map<Livre, Long> resultMap = new HashMap<>(list.size());
		for (Object[] result : list)
			resultMap.put((Livre) result[0], (Long) result[1]);

		return resultMap.keySet();
	}

	/**
	 * Fonction effectuant la recherche d'un livre via le service elsaticSearch
	 * @param requeteBarre La requete de l'utilisateur
	 * @param d Le prix minimal requis (ou -1 si indiférent)
	 * @param e Le prix maximal requis (ou -1 si indiférent)
	 * @param genre Le genre de livre (ou @ si indiférent)
	 * @param avisMin Le seuil de moyenne d'avis utilisateur (ou -1 si indiferent)
	 * @return Une liste de livre correspondant à la recherche
	 * @throws IOException
	 */
	public List<CoupleLivreVente> recherche(String requeteBarre, double d, double e, String genre, int avisMin) throws IOException {

		String req = ElasticSearchTools.rechercheElasticSearch(requeteBarre, d, e, genre, avisMin);

		InputStream is = ElasticSearchTools.doRequest("http://" + conf.get("IP_ELASTICSEARCH") + ":" + conf.get("PORT_ELASTICSEARCH") + "/livres/_search", "GET", req);

		BufferedReader rd = new BufferedReader(new InputStreamReader(is));

		JSONObject json = null;
		Logger logger = Logger.getAnonymousLogger();
		try {
			json = (JSONObject) new JSONParser().parse(rd);
		} catch (Exception e1) {
			logger.log(Level.SEVERE, "an exception was thrown ", e1);
		}
		rd.close();
		String str = "select OBJECT(b) from Livre b where b.id=";
		StringBuilder requete = new StringBuilder();
		requete.append(str);
		if (json != null && json.get("hits") != null) {
			JSONObject hits1 = (JSONObject) json.get("hits");
			JSONArray hits2 = (JSONArray) hits1.get("hits");

			if (hits2.size() != 0) {
				for (int i = 0; i < hits2.size(); i++) {
					JSONObject tmp = (JSONObject) hits2.get(i);
					String id = (String) tmp.get("_id");
					if (i != hits2.size() - 1)
						requete.append(id + " or b.id= ");
					else
						requete.append(id);
				}
				Query q = em.createQuery(requete.toString());
				List<Livre> list = (List<Livre>) q.getResultList();
				List<CoupleLivreVente> res = new LinkedList<>();
				for (int i = 0; i < list.size(); i++) {
					res.add(new CoupleLivreVente(list.get(i), list.get(i).getLesVentes().size()));
				}
				return res;
			} else {
				return new LinkedList<>();
			}
		} else {
			return new LinkedList<>();
		}
	}

	/**
	 * Fonction qui supprime tous les livres connus
	 */
	public void supprimerTous() {
		Query q = em.createNativeQuery("DELETE FROM Livre");
		q.executeUpdate();
	}

	/**
	 * Fonction permettant d'ajouter une promotion a un livre
	 * @param id L'id du livre a promouvoir
	 * @param taux Le taux de reduction en % (0-100)
	 * @param dateD La date de début de la promotion
	 * @param dateF La date de fin de la promotion
	 */
	public void ajouterPromo(Long id, int taux, String dateD, String dateF) {
		Livre l = em.find(Livre.class, id);
		if (l != null) {

			promoBean.creerPromotion(l, taux, dateD, dateF);

		} else {
			return;
		}

	}

}
