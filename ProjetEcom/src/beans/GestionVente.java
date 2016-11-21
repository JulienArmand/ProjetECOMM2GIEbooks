package beans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Client;
import model.Livre;
import model.Vente;

@Stateless
public class GestionVente {

	@PersistenceContext(unitName = "Database-unit")
	private EntityManager em;

	public Vente creerVente(Livre l) {

		Vente c = new Vente(9);
		c.setLivre(l);

		em.persist(c);
		return c;
	}

	public void supprimerTous() {
		Query q = em.createNativeQuery("DELETE FROM Vente");
		Query q2 = em.createNativeQuery("ALTER TABLE Vente {ALTER id RESTART WITH 0} ");
		q.executeUpdate();
		q2.executeUpdate();
	}

}
