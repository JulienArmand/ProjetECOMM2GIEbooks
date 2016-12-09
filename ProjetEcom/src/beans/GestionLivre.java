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
import java.util.logging.Level;
import java.util.logging.Logger;

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

		Logger logger = Logger.getAnonymousLogger();
		Livre l = new Livre(nom, isbn, datePub, nbpage, (float) prix, langue, langueOriginale);
		String name ="";
		try {
			URL url = new URL(couverture);
			name = FilenameUtils.getName(url.getPath());
		} catch (MalformedURLException e2) {
			logger.log(Level.FINE, "an exception was thrown", e);
		}
		
//		String couvertureUrl = conf.get("PATH_SERVER")+"/images/"+name+".png";
		
//		couvertureUrl = couverture;
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
			ElasticSearchTools.enregistrerDansLIndexage("http://"+conf.get("IP_ELASTICSEARCH")+":"+conf.get("PORT_ELASTICSEARCH"), l);
		} catch (Exception ex) {
			logger.log(Level.FINE, "an exception was thrown : Erreur durant l'indexage du livre dans elasticsearch :", ex);
		}
		
		return l;
	}

	public List<Livre> getLesLivres() {
		Query q = em.createQuery("select OBJECT(b) from Livre b");
		return (List<Livre>) q.getResultList();
	}

	public List<Livre> getLesLivresEnPromotion() {

		Query q = em.createQuery("select p.livre from Promotion p where p.dateFin > CURRENT_DATE");
		return (List<Livre>) q.getResultList();
	}

	public Livre getLivreAvecId(long id) {
		return em.find(Livre.class, id);
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

	public List<CoupleLivreVente> recherche(String requeteBarre, double d, double e, String genre, int avisMin)
			throws Exception {

		String req = ElasticSearchTools.rechercheElasticSearch(requeteBarre, d, e, genre, avisMin);

		InputStream is = ElasticSearchTools.doRequest("http://"+conf.get("IP_ELASTICSEARCH")+":"+conf.get("PORT_ELASTICSEARCH")+"/livres/_search", "GET", req);

		BufferedReader rd = new BufferedReader(new InputStreamReader(is));

		JSONObject json = (JSONObject) new JSONParser().parse(rd);
		rd.close();
		String str = "select OBJECT(b) from Livre b where b.id=";

		JSONObject hits1 = (JSONObject) json.get("hits");
		JSONArray hits2 = (JSONArray) hits1.get("hits");
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
			return res;
		} else {
			return new LinkedList<>();
		}
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
			return;
		}
		
	}

}
