package beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Client;
import model.Commande;
import model.Livre;
import model.Vente;

@Stateless
public class GestionVente {

	@PersistenceContext(unitName = "Database-unit")
	private EntityManager em;

	public Vente creerVente(Livre l) {
		Vente c = null;
		if (l.getPromotion() != null)
			c = new Vente(l.getPrix() * ((100 - l.getPromotion().getTauxReduc()) / 100));
		else
			c = new Vente(l.getPrix());
		c.setLivre(l);
		em.persist(c);
		return c;
	}
	
	public Vente creerVente(Livre l, Commande cmd) {
		Vente c = null;
		if (l.getPromotion() != null)
			c = new Vente(l.getPrix() * ((100 - l.getPromotion().getTauxReduc()) / 100));
		else
			c = new Vente(l.getPrix());
		c.setLivre(l);
		c.setLaCommande(cmd);
		em.persist(c);
		return c;
	}

	public List<Vente> getVentes(Commande c) {
		Query q = em.createQuery("select OBJECT(b) from Vente b where b.laCommande =" + c);
		List<Vente> list = (List<Vente>) q.getResultList();
		return list;
	}

	public void supprimerTous() {
		Query q = em.createNativeQuery("DELETE FROM Vente");
		Query q2 = em.createNativeQuery("ALTER TABLE Vente {ALTER id RESTART WITH 0} ");
		q.executeUpdate();
		q2.executeUpdate();
	}

}
