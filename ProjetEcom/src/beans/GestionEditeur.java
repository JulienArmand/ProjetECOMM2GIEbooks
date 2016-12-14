package beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Editeur;

/**
 * Bean gerant les editeurs
 * @author Clement
 *
 */
@Stateless
public class GestionEditeur {

	@PersistenceContext(unitName = "Database-unit")
	private EntityManager em;

	/**
	 * Fonction servant a creer un editeur proprement
	 * @param nom Le nom de l'editeur
	 * @return Un nouvel editeur, ou celui deja existant si le nom a été touvé dans la base
	 */
	public Editeur creerEditeur(String nom) {

		Query q = em.createQuery("select OBJECT(e) from Editeur e where e.nom = \"" + nom + "\"");
		List<Editeur> le = q.getResultList();
		Editeur e;
		if (le == null || le.isEmpty()) {
			e = new Editeur(nom);
			em.persist(e);
		} else
			e = le.get(0);

		return e;
	}

	/**
	 * Fonction donnant un editeur identifié par son id
	 * @param id L'id de l'editeur a chercher
	 * @return Un editeur d'id  id ou null si l'id est invalide
	 */
	public Editeur getEditeur(long id){
		return em.find(Editeur.class, id);
	}
	
	/** Fonction donnant tous les editeurs de la base
	 * @return Une liste d'editeurs
	 */
	public List<Editeur> getLesEditeurs() {
		Query q = em.createQuery("select OBJECT(b) from Editeur b");
		return (List<Editeur>) q.getResultList();
	}
	
	/**
	 * Fonction qui supprime tous les editeurs connus
	 */
	public void supprimerTous() {
		Query q = em.createNativeQuery("DELETE FROM Editeur");
		q.executeUpdate();
	}

	/**
	 * Fonction modifiant le nom d'un editeur d'id id
	 * @param id L'id de l'editeur a modifier
	 * @param nom Le nouveau nom de l'editeur
	 */
	public void modifierEditeur(Long id, String nom) {
		Editeur g = em.find(Editeur.class, id);
		if (g != null) {
			g.setNom(nom);
			em.persist(g);

		} else {
			return;
		}

	}
}
