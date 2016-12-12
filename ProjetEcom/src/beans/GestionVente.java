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
		Vente v = null;
		v = new Vente(l.getPrixAvecPromo());
		v.setLivre(l);
		em.persist(v);
		return v;
	}
	
	public Vente creerVente(Livre l, Commande cmd) {
		Vente v = null;
		v = new Vente(l.getPrixAvecPromo());
		System.out.println("GESTION VENTE : " + v.getPrix());
		v.setLivre(l);
		v.setLaCommande(cmd);
		em.persist(v);
		return v;
	}

	public List<Vente> getVentes(Commande c) {
		Query q = em.createQuery("select OBJECT(b) from Vente b where b.laCommande =" + c);
		List<Vente> list = (List<Vente>) q.getResultList();
		return list;
	}
	
	public List<Vente> getLesVentes() {
		Query q = em.createQuery("select OBJECT(b) from Vente b");
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
