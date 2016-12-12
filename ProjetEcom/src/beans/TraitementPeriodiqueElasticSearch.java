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

@Stateless
@LocalBean
public class TraitementPeriodiqueElasticSearch {

	DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
	
	@PersistenceContext(unitName = "Database-unit")
	private EntityManager em;

	@EJB()
	private ConfigurationGenerale config;
	
	@Schedule(minute = "*/30", hour = "*")
	public void traiterTrenteSecondes() {
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
