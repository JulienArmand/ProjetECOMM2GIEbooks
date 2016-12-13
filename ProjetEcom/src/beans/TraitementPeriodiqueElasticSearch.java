package beans;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Livre;
import tools.ElasticSearchTools;

/**
 * Bean servant a verifier et mettre a jour periodiquement les prix des livres dans l'indexage d'elastisearch
 *  etant donné que les promotions sont bornées temporellement.
 * @author Clement
 *
 */
@Stateless
@LocalBean
public class TraitementPeriodiqueElasticSearch {

	/**
	 * 
	 */
	DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
	
	@PersistenceContext(unitName = "Database-unit")
	private EntityManager em;

	@EJB()
	private ConfigurationGenerale config;
	
	/**
	 * Traitement toutes les trentes minutes (tous les jours suffirai en réalité)
	 */
	@Schedule(minute = "*/30", hour = "*")
	public void traiterTrenteMinutes() {
		Query q = em.createQuery("select l from Livre l");
		List<Livre> list = (List<Livre>) q.getResultList();
		Logger logger = Logger.getAnonymousLogger();
		Iterator<Livre> it = list.iterator();
		while(it.hasNext()){
			try {
				ElasticSearchTools.enregistrerDansLIndexage("http://"+config.get("IP_ELASTICSEARCH")+":"+config.get("PORT_ELASTICSEARCH"), it.next());
			} catch (IOException e) {
				logger.log(Level.FINE, "an exception was thrown", e);
			}
		}
	}
	
}
