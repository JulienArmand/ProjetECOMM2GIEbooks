package beans;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Iterator;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import Tools.ElasticSearchTools;
import model.Livre;

@Stateless
@LocalBean
public class TraitementPeriodiqueElasticSearch {

	DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
	
	@PersistenceContext(unitName = "Database-unit")
	private EntityManager em;

	@Schedule(minute = "*/30", hour = "*")
	public void traiterTrenteSecondes() {
		Query q = em.createQuery("select l from Livre l");
		List<Livre> list = (List<Livre>) q.getResultList();
		
		Iterator<Livre> it = list.iterator();
		while(it.hasNext()){
			try {
				ElasticSearchTools.enregistrerDansLIndexage(it.next());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("-------------------------------------------Rechargement");
	}
	
	//@Schedule(second = "*/40", minute = "*", hour = "*")
	/*public void traiterTrenteSecondesSuppressionPromo() throws IOException {
		Query q = em.createQuery("select l from Livre l");
		List<Livre> list = (List<Livre>) q.getResultList();
		
		Iterator<Livre> it = list.iterator();
		Livre tmpLibre;
		while(it.hasNext()){
			tmpLibre = it.next();
			if(tmpLibre.getPromotion() != null)
				tmpLibre.getPromotion().setDateFin(Date.from(Instant.now()));;
		}
		System.out.println("-------------------------------------------SuppressionPromo");
	}*/
	
	//@Schedule(second = "*/55", minute = "*/1", hour = "*")
	/*public void traiterTrenteSecondesCreationPromo() throws IOException {
		Query q = em.createQuery("select l from Livre l");
		List<Livre> list = (List<Livre>) q.getResultList();
		
		Iterator<Livre> it = list.iterator();
		Livre tmpLibre;
		while(it.hasNext()){
			tmpLibre = it.next();
			if(tmpLibre.getPromotion() != null) {
				Date dateFin = tmpLibre.getPromotion().getDateFin();
				dateFin.setYear(2017);
				tmpLibre.getPromotion().setDateFin(dateFin);
			}
		}
		System.out.println("-------------------------------------------CreationPromo");
	}*/
}
