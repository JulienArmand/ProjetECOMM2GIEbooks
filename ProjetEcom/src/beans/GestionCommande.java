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

@Stateless
public class GestionCommande {

	@PersistenceContext(unitName = "Database-unit")
	private EntityManager em;

	public Commande creerCommande(Date dateVente, Client leClient, MoyenPaiement paiement) {

		Commande c = new Commande(dateVente);
		em.persist(c);
		c.setLeClient(leClient);
		c.setLeMoyenDePaiement(paiement);
		em.persist(c);
		return c;
	}

	public Commande setVentesCommande(long idCommande, Collection<Vente> lesVentes) {
		Commande c = getCommande(idCommande);
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

	public Commande getCommande(long id) {
		return em.find(Commande.class, id);
	}

	public List<Commande> getCommandeClient(Client leClient) {
		Query q = em.createQuery("select OBJECT(b) from Commande b where b.leClient.id =" + leClient.getId());
		return (List<Commande>) q.getResultList();
	}
	
	public List<Commande> getLesCommandes() {
		Query q = em.createQuery("select OBJECT(b) from Commande b");
		return (List<Commande>) q.getResultList();
	}

	public void supprimerTous() {
		Query q = em.createNativeQuery("DELETE FROM Commande");
		Query q2 = em.createNativeQuery("ALTER TABLE Commande {ALTER id RESTART WITH 0} ");
		q.executeUpdate();
		q2.executeUpdate();
	}

}
