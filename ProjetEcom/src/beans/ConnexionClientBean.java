package beans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Client;

@Stateless
public class ConnexionClientBean {
	
	@PersistenceContext(unitName = "Database-unit") 
	private EntityManager em; 
	
	public boolean checkPseudoExiste(String pseudo){
		boolean result = false;
		System.out.println("Check2");
		Query q = em.createQuery("select OBJECT(b) from Client b");
		System.out.println("Check3");
		List<Client> list = (List<Client>) q.getResultList();
		System.out.println("Check4");
		System.out.println(list.size());
		int i = 0;
		while(i< list.size() || !result){
			if(list.get(i).getPseudo().equals(pseudo)){
				result = true;
			}
			i++;
		}
		return result;
	}
	
	public boolean checkMotDePasseCorrect(String pseudo, String motDePasse){
		boolean result = false;
		Query q = em.createQuery("select OBJECT(b) from Client b"); 
		List<Client> list = (List<Client>) q.getResultList(); 
		int i = 0;
		while(i< list.size() || !result){
			if(list.get(i).getPseudo().equals(pseudo) && list.get(i).getMotDePasse().equals(motDePasse)){
				result = true;
			}
			i++;
		}
		return result;
	}

}
