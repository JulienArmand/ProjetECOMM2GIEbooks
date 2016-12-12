package beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Commande;
import model.Livre;
import model.Vente;

@Stateless
public class GestionVente {

	@PersistenceContext(unitName = "Database-unit")
	private EntityManager em;

	public Vente creerVente(Livre l) {
		Vente v;
		v = new Vente(l.getPrixAvecPromo());
		v.setLivre(l);
		em.persist(v);
		return v;
	}
	
	public Vente creerVente(Livre l, Commande cmd) {
		Vente v;
		v = new Vente(l.getPrixAvecPromo());
		v.setLivre(l);
		v.setLaCommande(cmd);
		em.persist(v);
		return v;
	}

	public List<Vente> getVentes(Commande c) {
		Query q = em.createQuery("select OBJECT(b) from Vente b where b.laCommande =" + c);
		return (List<Vente>) q.getResultList();
	}
	
	public List<Vente> getLesVentes() {
		Query q = em.createQuery("select OBJECT(b) from Vente b");
		return (List<Vente>) q.getResultList();
	}

	public void supprimerTous() {
		Query q = em.createNativeQuery("DELETE FROM Vente");
		Query q2 = em.createNativeQuery("ALTER TABLE Vente {ALTER id RESTART WITH 0} ");
		q.executeUpdate();
		q2.executeUpdate();
	}
	
	public boolean aAchete(long idClient, long idLivre) {
		Query q = em.createQuery("select OBJECT(v) from Vente v, Client c, Livre l, Commande co where v.livre.id = " + idLivre + " and v.laCommande.leClient.id = " + idClient);
		List<Livre> livre = (List<Livre>) q.getResultList();
		return !livre.isEmpty();
	}

}
