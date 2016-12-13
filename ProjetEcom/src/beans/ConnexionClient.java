package beans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Client;

/**
 * Bean gérant la connexion d'un client
 * @author Clement
 *
 */
@Stateless
public class ConnexionClient {
	
	@PersistenceContext(unitName = "Database-unit") 
	private EntityManager em;
	
	/**
	 * Fonction verifiant si un pseudo est déjà enregistré.
	 * @param pseudo Le pseudo a vérifier
	 * @return True si le pseudo existe déjà, False sinon.
	 */
	public boolean checkPseudoExiste(String pseudo){
		boolean result = false;
		Query q = em.createQuery("select OBJECT(b) from Client b WHERE b.pseudo LIKE :reqpseudo AND b.desinscrit = false").setParameter("reqpseudo", pseudo);
		List<Client> list = (List<Client>) q.getResultList();
		if(!list.isEmpty()){
			result = true;
		}
		return result;
	}
	
	/**
	 *  Fonction verifiant si la paire pseudo/mot de passe est valide
	 * @param pseudo Le pseudo du client
	 * @param motDePasse Le mot de passe du client
	 * @return True si le mot de passe correspond au pseudo spécifié, False sinon.
	 */
	public boolean checkMotDePasseCorrect(String pseudo, String motDePasse){
		boolean result = false;
		Query q = em.createQuery("select OBJECT(b) from Client b WHERE b.pseudo LIKE :reqpseudo AND b.motDePasse LIKE :reqmotDePasse AND b.desinscrit = false").setParameter("reqpseudo", pseudo).setParameter("reqmotDePasse", motDePasse); 
		List<Client> list = (List<Client>) q.getResultList(); 
		if(!list.isEmpty()){
			result = true;
		}
		return result;
	}
	
	/**
	 * Fonction donnant l'id du client identifé par son pseudo
	 * @param pseudo Le pseudo du client
	 * @return Un long etant l'id du client, -1 sinon
	 */
	public long getIdClient(String pseudo){
		Query q = em.createQuery("select OBJECT(b) from Client b where b.pseudo = '" + pseudo+"' AND b.desinscrit = false");
		Client c = (Client)q.getSingleResult();
		if(c == null)
			return -1;
		return c.getId();
	}

}
