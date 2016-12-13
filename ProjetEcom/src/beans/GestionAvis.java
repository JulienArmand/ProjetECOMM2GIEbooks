package beans;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Avis;
import model.Client;
import model.Livre;
import tools.ElasticSearchTools;

/**
 * Bean gérant les avis client
 * @author Clement
 *
 */
@Stateless
public class GestionAvis {

	@PersistenceContext(unitName = "Database-unit")
	private EntityManager em;

	/**
	 * Fonction servant a créer un avis proprement.
	 * @param l Le livre associé a l'avis
	 * @param c Le client donnant son avis
	 * @param note La note du livre
	 * @param commentaire Le commentaire du livre
	 * @return Un avis concernant le livre l, du client c, avec une note et un commentaire
	 */
	public Avis creerAvis(Livre l, Client c, int note, String commentaire) {

		Date datePublication = Date.from(Instant.now());

		Avis a = new Avis(note, commentaire, datePublication);

		a.setLeLivre(l);
		l.getLesAvis().add(a);

		a.setLeClient(c);
		c.getLesAvis().add(a);
		
		Logger logger = Logger.getAnonymousLogger();
		
		try {
			ElasticSearchTools.updateAvis(l);
		} catch (Exception e) {
			logger.log(Level.FINE, "an exception was thrown", e);
		}

		em.persist(a);
		return a;
	}
	
	/**
	 * Fonction donannts tous les avis clients
	 * @return Une liste d'avis de tous les clients
	 */
	public List<Avis> getLesAvis() {
		Query q = em.createQuery("select OBJECT(b) from Avis b");
		return q.getResultList();
	}

	/**
	 * Fonction supprimant tous les avis clients
	 */
	public void supprimerTous() {
		Query q = em.createNativeQuery("DELETE FROM Avis");
		Query q2 = em.createNativeQuery("ALTER TABLE Avis {ALTER id RESTART WITH 0} ");
		q.executeUpdate();
		q2.executeUpdate();
	}
	
	/**
	 * Fonction permettant si un client a deja posté un avis sur un livre
	 * @param idClient L'id du client emmeteur de l'avis
	 * @param idLivre L'id du livre sujet de l'avis
	 * @return True si le client d'id idClient a deja poster un avis sur le livre d'id idLivre, False sinon.
	 */
	public boolean existe(long idClient, long idLivre) {
		Query q = em.createQuery("select OBJECT(a) from Avis a, Client c, Livre l where a.leClient.id = " + idClient + " and a.leLivre.id = " + idLivre);
		List<Livre> livre = (List<Livre>) q.getResultList();
		return !livre.isEmpty();
	}
}
