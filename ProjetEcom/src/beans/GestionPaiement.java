package beans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.CarteBancaire;
import model.Client;
import model.MoyenPaiement;
import model.Paypal;

@Stateless
public class GestionPaiement {

	@PersistenceContext(unitName = "Database-unit")
	private EntityManager em;

	public MoyenPaiement creerMoyenPaiement(String type) {
		MoyenPaiement mp = null;
		
		if(type.equals("Paypal")){
			mp = new Paypal();
		} else {
			mp = new CarteBancaire();
		}

		em.persist(mp);
		return mp;
	}
	
	public void supprimerTous() {
		Query q = em.createNativeQuery("DELETE FROM MoyenPaiement");
		Query q2 = em.createNativeQuery("ALTER TABLE MoyenPaiement {ALTER id RESTART WITH 0} ");
		q.executeUpdate();
		q2.executeUpdate();
	}
}
