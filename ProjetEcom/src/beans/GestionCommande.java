package beans;

import java.util.List;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Client;
import model.Commande;
import model.MoyenPaiement;
import model.Vente;

/**
 * Bean gerant les commandes client
 * @author Clement
 *
 */
@Stateless
public class GestionCommande {

	@PersistenceContext(unitName = "Database-unit")
	private EntityManager em;

	/**
	 * Fonction servant a creer une commande proprement
	 * @param dateVente La date de la commande
	 * @param leClient Le client qui a passé commande
	 * @param paiement Le moyen de paiement utilisé
	 * @return Une commande qui est enregistrée
	 */
	public Commande creerCommande(Date dateVente, Client leClient, MoyenPaiement paiement) {

		Commande c = new Commande(dateVente);
		em.persist(c);
		c.setLeClient(leClient);
		c.setLeMoyenDePaiement(paiement);
		em.persist(c);
		return c;
	}

	/**
	 * Fonction ajoutant la liste des ventes a une commande.
	 * @param idCommande L'id de la commande
	 * @param lesVentes La liste des ventes a ajouter
	 * @return La commande d'id idCommande modifiée ou null si idCommande est invalide
	 */
	public Commande setVentesCommande(long idCommande, Collection<Vente> lesVentes) {
		Commande c = getCommande(idCommande);
		if(c == null)
			return null;
		c.setLesVentes(lesVentes);
		float prix =0;
		Iterator<Vente> it = lesVentes.iterator();
		while(it.hasNext()){
			Vente v = (Vente) it.next();
			prix += v.getPrix();
		}
		c.setPrixTotal(prix);
		em.merge(c);
		return c;
	}

	/**
	 * Fonction donnant une commande par son id
	 * @param id L'id de la commande a chercher
	 * @return La commande d'id id ou null si l'id est invalide
	 */
	public Commande getCommande(long id) {
		return em.find(Commande.class, id);
	}

	/**
	 * Fonctionnant donnant toutes les commandes effectuées par un cleint
	 * @param leClient Le client dont on veut les commandes
	 * @return La liste des commandes du client leClient, la liste est vide s'il n'a pas effectué de commandes
	 */
	public List<Commande> getCommandeClient(Client leClient) {
		Query q = em.createQuery("select OBJECT(b) from Commande b where b.leClient.id =" + leClient.getId());
		return (List<Commande>) q.getResultList();
	}
	
	/**
	 * Focntionnant toutes les commandes effectuées.
	 * @return Une liste de commandes.
	 */
	public List<Commande> getLesCommandes() {
		Query q = em.createQuery("select OBJECT(b) from Commande b");
		return (List<Commande>) q.getResultList();
	}

	/**
	 * Fonction supprimant toutes les commandes de la base
	 */
	public void supprimerTous() {
		Query q = em.createNativeQuery("DELETE FROM Commande");
		q.executeUpdate();
	}

}
