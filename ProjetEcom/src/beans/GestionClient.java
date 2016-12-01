package beans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Client;

@Stateless
public class GestionClient {

	@PersistenceContext(unitName = "Database-unit")
	private EntityManager em;

	public Client creerClient(String pseudo, String email, String mdp, String nom, String prenom) {

		Client c = new Client(pseudo, email, mdp, nom, prenom);

		em.persist(c);
		return c;
	}
	
	public Client getClient(long id){
		Query q = em.createQuery("select OBJECT(b) from Client b where b.id =" + id);
		return (Client)q.getSingleResult();
	}

	public void supprimerTous() {
		Query q = em.createNativeQuery("DELETE FROM Client");
		Query q2 = em.createNativeQuery("ALTER TABLE Client {ALTER id RESTART WITH 0} ");
		q.executeUpdate();
		q2.executeUpdate();
	}

	public Client getClientFromPseudo(String pseudo) {
		Query q = em.createQuery("select OBJECT(b) from Client b where b.pseudo = '" + pseudo+"'");
		return (Client)q.getSingleResult();
	}
	
	public void updateClientPseudo(Client c, String pseudo){
		c.setPseudo(pseudo);
		em.merge(c);
	}
	
	public void updateClientNom(Client c, String nom){
		c.setNom(nom);
		em.merge(c);
	}
	
	public void updateClientPrenom(Client c, String prenom){
		c.setPrenom(prenom);
		em.merge(c);
	}
	
	public void updateClientEmail(Client c, String email){
		c.setEmail(email);
		em.merge(c);
	}
}
