package beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Client;

/**
 * A quoi sert cette classe ?????????
 * @author Clement
 *
 */
@Stateless
public class InscriptionClientBean {
	

	
	@PersistenceContext(unitName = "Database-unit") 
	private EntityManager em; 
	
	/**
	 * 
	 * @param pseudo
	 * @param email
	 * @param motDePasse
	 * @param nom
	 * @param prenom
	 * @return
	 */
	public Client creerClient(String pseudo, String email, String motDePasse, String nom, String prenom){
		Client c = null;
		if(!pseudoDejaPris(pseudo) && !emailDejaPris(email) ){
			c = new Client(pseudo, email, motDePasse, nom, prenom);
			em.persist(c);
		}
		return c;
	}
	
	/**
	 * @return
	 */
	public List<Client> getLesClients(){
		
		Query q = em.createQuery("select OBJECT(b) from Client b"); 
		return(List<Client>) q.getResultList(); 
	} 
		
	/**
	 * 
	 */
	public void suppressionClients(){
		
	    Query q1 = em.createNativeQuery("DELETE FROM Client");
	    
	    q1.executeUpdate();

	}
	
	/**
	 * @param pseudo
	 * @return
	 */
	public boolean pseudoDejaPris(String pseudo){
		List<Client> list = getLesClients();
		for(Client c : list){
			if( c.getPseudo().equals(pseudo) && !c.getDesinscrit()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param email
	 * @return
	 */
	public boolean emailDejaPris(String email){
		List<Client> list = getLesClients();
		for(Client c : list){
			if( c.getEmail().equals(email) && !c.getDesinscrit()){
				return true;
			}
		}
		return false;
	}
		
}
