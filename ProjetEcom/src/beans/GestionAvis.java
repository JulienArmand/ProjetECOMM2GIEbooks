package beans;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import Tools.ElasticSearchTools;
import model.Avis;
import model.Client;
import model.Livre;

@Stateless
public class GestionAvis {

	@PersistenceContext(unitName = "Database-unit")
	private EntityManager em;

	public Avis creerAvis(Livre l, Client c, int note, String commentaire) {

		Date datePublication = Date.from(Instant.now());

		Avis a = new Avis(note, commentaire, datePublication);

		a.setLeLivre(l);
		l.getLesAvis().add(a);

		a.setLeClient(c);
		c.getLesAvis().add(a);
		
		try {
			ElasticSearchTools.updateAvis(l);
		} catch (Exception e) {
			e.printStackTrace();
		}

		em.persist(a);
		return a;
	}
	
	public List<Avis> getLesAvis() {
		Query q = em.createQuery("select OBJECT(b) from Avis b");
		List<Avis> list = (List<Avis>) q.getResultList();
		return list;
	}

	public void supprimerTous() {
		Query q = em.createNativeQuery("DELETE FROM Avis");
		Query q2 = em.createNativeQuery("ALTER TABLE Avis {ALTER id RESTART WITH 0} ");
		q.executeUpdate();
		q2.executeUpdate();
	}
	
	public boolean existe(long idClient, long idLivre) {
		Query q = em.createQuery("select OBJECT(a) from Avis a, Client c, Livre l where a.leClient.id = " + idClient + " and a.leLivre.id = " + idLivre);
		List<Livre> livre = (List<Livre>) q.getResultList();
		System.out.println(livre.size());
		return livre.size()>0;
	}
}
