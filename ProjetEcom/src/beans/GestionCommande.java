package beans;

import java.util.List;
import java.util.Collection;
import java.util.Date;
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

	public Commande creerCommande(Date dateVente, float prixTotal, Client leClient, MoyenPaiement paiement) {

		Commande c = new Commande(dateVente, prixTotal);
		em.persist(c);
		c.setLeClient(leClient);
		c.setLeMoyenDePaiement(paiement);
		em.persist(c);
		return c;
	}

	public Commande setVentesCommande(long idCommande, Collection<Vente> lesVentes) {
		Commande c = getCommande(idCommande);
		c.setLesVentes(lesVentes);
		em.persist(c);
		return c;
	}

	public Commande getCommande(long id) {

		Query q = em.createQuery("select OBJECT(b) from Commande b where b.id =" + id);
		List<Commande> list = (List<Commande>) q.getResultList();
		return (Commande) q.getSingleResult();
	}

	public List<Commande> getCommandeClient(Client leClient) {

		Query q = em.createQuery("select OBJECT(b) from Commande b where b.leClient.id =" + leClient);
		List<Commande> list = (List<Commande>) q.getResultList();
		return list;
	}

	public void supprimerTous() {
		Query q = em.createNativeQuery("DELETE FROM Commande");
		Query q2 = em.createNativeQuery("ALTER TABLE Commande {ALTER id RESTART WITH 0} ");
		q.executeUpdate();
		q2.executeUpdate();
	}

}
