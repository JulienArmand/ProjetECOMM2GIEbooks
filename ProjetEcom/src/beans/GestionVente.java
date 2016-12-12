package beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Commande;
import model.Livre;
import model.Vente;

/**
 * Bean gérant les ventes
 * @author Clement
 *
 */
@Stateless
public class GestionVente {

	@PersistenceContext(unitName = "Database-unit")
	private EntityManager em;

	/**
	 * Fonction servant a creer un editeur proprement
	 * @param livre Le livre vendu
	 * @return Une vente concernant le livre livre.
	 */
	public Vente creerVente(Livre livre) {
		Vente v;
		v = new Vente(livre.getPrixAvecPromo());
		v.setLivre(livre);
		em.persist(v);
		return v;
	}
	
	/**
	 * Fonction servant a creer un editeur proprement
	 * @param livre Le livre vendu
	 * @param cmd La commande a laquelle fait partie la vente
	 * @return Une vente concernant le livre livre.
	 */
	public Vente creerVente(Livre livre, Commande cmd) {
		Vente v;
		v = new Vente(livre.getPrixAvecPromo());
		v.setLivre(livre);
		v.setLaCommande(cmd);
		em.persist(v);
		return v;
	}

	/**
	 *  Fonction donnant toutes les ventes d'une commande
	 * @param laCommande La commande concernée
	 * @return Une liste de ventes
	 */
	public List<Vente> getVentes(Commande laCommande) {
		Query q = em.createQuery("select OBJECT(b) from Vente b where b.laCommande =" + laCommande);
		return (List<Vente>) q.getResultList();
	}
	
	/** Fonction donnant toutes les ventes de la base
	 * @return Une liste de ventes
	 */
	public List<Vente> getLesVentes() {
		Query q = em.createQuery("select OBJECT(b) from Vente b");
		return (List<Vente>) q.getResultList();
	}

	/**
	 * Fonction qui supprime toutes les ventes connues
	 */
	public void supprimerTous() {
		Query q = em.createNativeQuery("DELETE FROM Vente");
		Query q2 = em.createNativeQuery("ALTER TABLE Vente {ALTER id RESTART WITH 0} ");
		q.executeUpdate();
		q2.executeUpdate();
	}
	
	
	/**
	 * Fonction permettant de savoir si un client a deja acheté un livre
	 * @param idClient L'id du client acheteur
	 * @param idLivre L'id du livre a acheter
	 * @return True si le client a deja acheté le livre, False sinon
	 */
	public boolean aAchete(long idClient, long idLivre) {
		Query q = em.createQuery("select OBJECT(v) from Vente v, Client c, Livre l, Commande co where v.livre.id = " + idLivre + " and v.laCommande.leClient.id = " + idClient);
		List<Livre> livre = (List<Livre>) q.getResultList();
		return !livre.isEmpty();
	}

}
