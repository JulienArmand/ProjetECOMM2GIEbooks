package beans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Client;

@Stateless
public class InscriptionClientBean {
	

	
	@PersistenceContext(unitName = "Database-unit") 
	private EntityManager em; 
	
	public Client creerClient(String pseudo, String email, String motDePasse, String nom, String prenom){
		
		Client c = new Client(pseudo, email, motDePasse, nom, prenom);
		em.persist(c);
		return c;
	}
	
	public List<Client> getLesClients(){
		
		Query q = em.createQuery("select OBJECT(b) from Client b"); 
		List<Client> list = (List<Client>) q.getResultList(); 
		return list;
	} 
	
	public void suppressionClients(){
		
	    Query q1 = em.createNativeQuery("DELETE FROM Client");
	    
	    q1.executeUpdate();

	}
		
}
