package beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import model.Client;
import tools.ChiffrageCookies;
import tools.GestionCookies;

/**
 * Bean gerant les clients
 * @author Clement
 *
 */
@Stateless
public class GestionClient {

	@PersistenceContext(unitName = "Database-unit")
	private EntityManager em;

	/**Fonction servant a creer un client proprement
	 * @param pseudo Le pseudo du client
	 * @param email L'email du client
	 * @param mdp Le mot de passe du client
	 * @param nom Le nom du client
	 * @param prenom Le prenom du client
	 * @return Un client nouvlement créer et enregisté dans la base
	 */
	public Client creerClient(String pseudo, String email, String mdp, String nom, String prenom) {

		Client c = new Client(pseudo, email, mdp, nom, prenom);

		em.persist(c);
		return c;
	}

	/**
	 * Fonction donnant le client d'id id
	 * @param id L'id du client a chercher
	 * @return Le client d'id id ou null s'il n'existe pas
	 */
	public Client getClient(long id) {
		return em.find(Client.class, id);
	}

	/**
	 * Fonction donnant le client enregister dans un cookie
	 * @param request Le conteneur de cookie
	 * @return Le client du cookie ou null s'il n'existe pas
	 */
	public Client getClientByCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		GestionCookies g = new GestionCookies();
		String idClient = ChiffrageCookies.dechiffreString(g.getCookieByName(cookies, "idClient").getValue());
		return em.find(Client.class, idClient);
	}

	/**
	 * Fonction donnant tous les clients de la base
	 * @return Une liste de clients
	 */
	public List<Client> getLesClients() {
		Query q = em.createQuery("select OBJECT(b) from Client b");
		return (List<Client>) q.getResultList();
	}

	/**
	 * Fonction supprimant tous les clients de la base
	 */
	public void supprimerTous() {
		Query q = em.createNativeQuery("DELETE FROM Client");
		Query q2 = em.createNativeQuery("ALTER TABLE Client {ALTER id RESTART WITH 0} ");
		q.executeUpdate();
		q2.executeUpdate();
	}

	/**
	 * Fonction donnant un client en fonction de son pseudo
	 * @param pseudo Le pseudo du client a chercher
	 * @return Un client avec pour pseudo pseudo, null s'il n'existe pas
	 */
	public Client getClientFromPseudo(String pseudo) {
		Query q = em.createQuery("select OBJECT(b) from Client b where b.pseudo = '" + pseudo + "' AND b.desinscrit = false");
		List<Client> list = (List<Client>) q.getResultList();
		if (!list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Fonction donnant un client en fonction de son email
	 * @param email L'email du client a chercher
	 * @return Un client avec pour email email, null s'il n'existe pas
	 */
	public Client getClientFromEmail(String email) {
		Query q = em.createQuery("select OBJECT(b) from Client b where b.email = '" + email + "' AND b.desinscrit = false");
		List<Client> list = (List<Client>) q.getResultList();
		if (!list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Fonction mettant a jour le pseudo du client c 
	 * @param c Le client a mettre a jour
	 * @param pseudo Le nouveau pseudo du client
	 */
	public void updateClientPseudo(Client c, String pseudo) {
		c.setPseudo(pseudo);
		em.merge(c);
	}

	/**
	 * Fonction mettant a jour le nom du client c 
	 * @param c Le client a mettre a jour
	 * @param nom Le nouveau nom du client
	 */
	public void updateClientNom(Client c, String nom) {
		c.setNom(nom);
		em.merge(c);
	}

	/**
	 * Fonction mettant a jour le prenom du client c 
	 * @param c Le client a mettre a jour
	 * @param prenom Le nouveau prenom du client
	 */
	public void updateClientPrenom(Client c, String prenom) {
		c.setPrenom(prenom);
		em.merge(c);
	}
	
	/**
	 * Fonction mettant a jour l' email du client c 
	 * @param c Le client a mettre a jour
	 * @param email Le nouveau email du client
	 */
	public void updateClientEmail(Client c, String email) {
		c.setEmail(email);
		em.merge(c);
	}

	/**
	 * Fonction mettant a jour le motDePasse du client c 
	 * @param c Le client a mettre a jour
	 * @param motDePasse Le nouveau motDePasse du client
	 */
	public void updateClientMotDePasse(Client c, String motDePasse) {
		c.setMotDePasse(motDePasse);
		em.merge(c);
	}

	/**
	 * Fonction desinscrivant le client c
	 * @param c Le client a desinscrire
	 */
	public void desinscriptionClient(Client c) {
		c.setDesinscrit(true);
		em.merge(c);
	}
}
