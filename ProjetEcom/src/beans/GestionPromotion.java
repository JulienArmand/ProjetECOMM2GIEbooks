package beans;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Livre;
import model.Promotion;

/**
 * Bean gérant les promotions
 * @author Clement
 *
 */
@Stateless
public class GestionPromotion {

	@PersistenceContext(unitName = "Database-unit")
	private EntityManager em;

	
	/**
	 * Fonction permettant de créer une promotion proprement
	 * @param livre Le livre concerné par la promotion
	 * @param taux Le taux de reduction
	 * @param dateD La date de début de promotion
	 * @param dateF La date de fin de promotion
	 */
	public void creerPromotion(Livre livre, int taux, String dateD, String dateF) {
		
		String pattern = "dd/MM/yyyy";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Logger logger = Logger.getAnonymousLogger();
		try {
			Date dateDD = format.parse(dateD);
			Date dateFF = format.parse(dateF);
			Promotion p = new Promotion(taux, dateDD, dateFF);

			p.setLivre(livre);
			livre.setPromotion(p);
			em.persist(p);
			em.persist(livre);
			
		}catch(Exception e){
			logger.log(Level.FINE, "an exception was thrown", e);
		}
	}
	
	
	/**
	 * Fonction permettant de créer une promotion proprement, avec les dates de debut et de fin a definir par la suite
	 * @param livre Le livre concerné par la promotion
	 * @param taux Le taux de reduction
	 * @return Une promotion sur le livre l
	 */
	public Promotion creerPromotion(Livre livre, int taux) {

		Date debut = Date.from(Instant.now());
		Date fin = Date.from(Instant.now());
		fin.setYear(2017);

		Promotion p = new Promotion(taux, debut, fin);

		p.setLivre(livre);
		livre.setPromotion(p);

		em.persist(p);
		return p;
	}

	/**
	 * Fonction permettant de créer une promotion proprement, avec les dates de debut et de fin et le livre concerné a definir par la suite
	 * @param taux Le taux de reduction
	 * @return Une promotion
	 */
	public Promotion creerPromotion(int taux) {

		Date debut = Date.from(Instant.now());
		Date fin = Date.from(Instant.now());
		fin.setYear(2017);

		Promotion p = new Promotion(taux, debut, fin);

		em.persist(p);
		return p;
	}

	/**
	 * Fonction donnant une promotion identifié par son id
	 * @param id L'id de la promotion a chercher
	 * @return Une promotion d'id id ou null si l'id est invalide
	 */
	public Promotion getPromotion(long id){
		return em.find(Promotion.class, id);
	}
	
	/** Fonction donnant toutes les promotions de la base
	 * @return Une lisye d'editeurs
	 */
	public List<Promotion> getLesPromotions() {
		Query q = em.createQuery("select OBJECT(b) from Promotion b");
		return (List<Promotion>) q.getResultList();
	}
	
	/**
	 * Fonction qui supprime toutes les promotions connus
	 */
	public void supprimerTous() {
		Query q = em.createNativeQuery("DELETE FROM Promotion");
		q.executeUpdate();
	}
}
