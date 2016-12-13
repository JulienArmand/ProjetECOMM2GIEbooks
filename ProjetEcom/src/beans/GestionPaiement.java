package beans;

import java.util.Iterator;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.CarteBancaire;
import model.Client;
import model.MoyenPaiement;
import model.Paypal;

/**
 * Bean gérant les moyens de paiement utilisateur
 * @author Clement
 *
 */
@Stateless
public class GestionPaiement {

	@PersistenceContext(unitName = "Database-unit")
	private EntityManager em;
	
	/**
	 * Paiement par Paypal
	 */
	private static final String PAYPAL = "Paypal";
	/**
	 * Paiement par carte bancaire
	 */
	private static final String CB = "CB";

	/**
	 * Fonction servant a creer un moyen de paiement proprement
	 * @param type Le type de moyen de paiement
	 * @return Un nouveau moyen de paiement pour l'utilisateur
	 */
	public MoyenPaiement creerMoyenPaiement(String type) {
		MoyenPaiement mp;
		
		if (type.equals(PAYPAL)) {
			mp = new Paypal();
		} else {
			mp = new CarteBancaire();
		}

		em.persist(mp);
		return mp;
	}

	/**
	 * Fonction donnant le moyen de paiement d'un client
	 * @param leClient Le client concerné
	 * @param type Le type de moyen de paiement a achercher
	 * @return Le moyen de paiement de l'utilisateur, ou null si invalide
	 */
	public MoyenPaiement getMoyenPaiement(Client leClient, String type) {
		MoyenPaiement mp = null;
		
		if (leClient.getLesMoyenDePaiement().isEmpty()) {
			mp = creerMoyenPaiement(type);
		} else {
			Iterator<MoyenPaiement> it = leClient.getLesMoyenDePaiement().iterator();
			while (it.hasNext()) {
				mp = (MoyenPaiement) it.next();
				if ((type.equals(CB) && mp instanceof CarteBancaire) || (type.equals(PAYPAL) && mp instanceof Paypal))
					break;
			}
		}

		return mp;
	}

	/**
	 * Fonction qui supprime tous les moyens de paiement connus
	 */
	public void supprimerTous() {
		Query q = em.createNativeQuery("DELETE FROM MoyenPaiement");
		Query q2 = em.createNativeQuery("ALTER TABLE MoyenPaiement {ALTER id RESTART WITH 0} ");
		q.executeUpdate();
		q2.executeUpdate();
	}
}
