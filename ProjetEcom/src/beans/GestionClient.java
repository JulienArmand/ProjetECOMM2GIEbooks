package beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import Tools.GestionCookies;
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
		return em.find(Client.class, id);
	}
	
	public Client getClientByCookie(HttpServletRequest request){
		Cookie[] cookies = request.getCookies();
		GestionCookies g = new GestionCookies();
		String idClient = g.getCookieByName(cookies, "idClient").getValue();
		return em.find(Client.class, idClient);
	}
	
	public List<Client> getLesClients() {
		Query q = em.createQuery("select OBJECT(b) from Client b");
		List<Client> list = (List<Client>) q.getResultList();
		return list;
	}

	public void supprimerTous() {
		Query q = em.createNativeQuery("DELETE FROM Client");
		Query q2 = em.createNativeQuery("ALTER TABLE Client {ALTER id RESTART WITH 0} ");
		q.executeUpdate();
		q2.executeUpdate();
	}

	public Client getClientFromPseudo(String pseudo) {
		Query q = em.createQuery("select OBJECT(b) from Client b where b.pseudo = '" + pseudo+"'");
		List<Client> list = (List<Client>) q.getResultList(); 
		if(list.size()>1){
//			throw new Exception("Le pseudo: "+pseudo+" est utilise plusieurs fois");
			return list.get(0);
		}else if(list.size()==1){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public Client getClientFromEmail(String email) {
		Query q = em.createQuery("select OBJECT(b) from Client b where b.email = '" + email+"'");
		List<Client> list = (List<Client>) q.getResultList(); 
		if(list.size()>1){
//			throw new Exception("L'email: "+email+" est utilise plusieurs fois");
			return list.get(0);
		}else if(list.size()==1){
			return list.get(0);
		}else{
			return null;
		}
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
	
	public void updateClientMotDePasse(Client c, String motDePasse){
		c.setMotDePasse(motDePasse);
		em.merge(c);
	}
}
