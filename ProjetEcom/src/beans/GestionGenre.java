package beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Genre;

/**
 * bean gérant les genres de livres
 * @author Clement
 *
 */
@Stateless
public class GestionGenre {

	@PersistenceContext(unitName = "Database-unit")
	private EntityManager em;

	/**
	 * Fonction servant a creer un genre proprement
	 * @param nom Le nom du genre
	 * @return Un nouveau genre, ou celui deja existant 
	 */
	public Genre creerGenre(String nom) {

		Query q = em.createQuery("select OBJECT(g) from Genre g where g.nom = \"" + nom + "\"");
		List<Genre> lg = q.getResultList();
		Genre g;
		if (lg == null || lg.isEmpty()) {
			g = new Genre(nom);
			em.persist(g);
		} else
			g = lg.get(0);

		return g;
	}
	
	/**
	 * Fonction donnant un genre identifié par son id
	 * @param id L'id du genre a chercher
	 * @return Un genre d'id  id ou null si l'id est invalide
	 */
	public Genre getGenre(long id){
		return em.find(Genre.class, id);
	}

	/**
	 * Fonction qui supprime tous les genres connus
	 */
	public void supprimerTous() {
		Query q = em.createNativeQuery("DELETE FROM Genre");
		q.executeUpdate();
	}

	/** Fonction donnant tous les genres de la base
	 * @return Une liste d'editeurs
	 */
	public List<Genre> getLesGenres() {
		Query q = em.createQuery("select OBJECT(b) from Genre b");
		return (List<Genre>) q.getResultList();
	}
	
	/**
	 * Fonction modifiant le nom d'un genre d'id id
	 * @param id L'id du genre a modifier
	 * @param nom Le nouveau nom du genre
	 */
	public void modifierGenre(Long id, String nom) {
		Genre g = em.find(Genre.class, id);
		if (g != null) {
			g.setNom(nom);
			em.persist(g);

		} else {
			return;
		}

	}

}
