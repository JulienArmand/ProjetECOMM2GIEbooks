package beans;

import java.time.Instant;
import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Client;
import model.Livre;
import model.Promotion;

@Stateless
public class GestionPromotion {

	@PersistenceContext(unitName = "Database-unit")
	private EntityManager em;

	public Promotion creerPromotion(Livre l, int i) {

		Date debut = Date.from(Instant.now());
		Date fin = Date.from(Instant.now());
		fin.setYear(2017);

		Promotion p = new Promotion(i, debut, fin);

		p.setLivre(l);
		l.setPromotion(p);

		em.persist(p);
		return p;
	}

	public Promotion creerPromotion(int i) {

		Date debut = Date.from(Instant.now());
		Date fin = Date.from(Instant.now());
		fin.setYear(2017);

		Promotion p = new Promotion(i, debut, fin);

		em.persist(p);
		return p;
	}

	public void supprimerTous() {
		Query q = em.createNativeQuery("DELETE FROM Promotion");
		Query q2 = em.createNativeQuery("ALTER TABLE Promotion {ALTER id RESTART WITH 0} ");
		q.executeUpdate();
		q2.executeUpdate();
	}

}
