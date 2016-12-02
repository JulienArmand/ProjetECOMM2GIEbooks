package beans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Client;

@Stateless
public class ConnexionClient {
	
	@PersistenceContext(unitName = "Database-unit") 
	private EntityManager em;
	
	public boolean checkPseudoExiste(String pseudo){
		boolean result = false;
		Query q = em.createQuery("select OBJECT(b) from Client b WHERE b.pseudo LIKE :reqpseudo").setParameter("reqpseudo", pseudo);
		List<Client> list = (List<Client>) q.getResultList();
		if(list.size() != 0){
			result = true;
		}
		return result;
	}
	
	public boolean checkMotDePasseCorrect(String pseudo, String motDePasse){
		boolean result = false;
		Query q = em.createQuery("select OBJECT(b) from Client b WHERE b.pseudo LIKE :reqpseudo AND b.motDePasse LIKE :reqmotDePasse").setParameter("reqpseudo", pseudo).setParameter("reqmotDePasse", motDePasse); 
		List<Client> list = (List<Client>) q.getResultList(); 
		if(list.size() != 0){
			result = true;
		}
		return result;
	}
	
	public long getIdClient(String pseudo){
		Query q = em.createQuery("select OBJECT(b) from Client b where b.pseudo = '" + pseudo+"'");
		Client c = (Client)q.getSingleResult();
		return c.getId();
	}

}
