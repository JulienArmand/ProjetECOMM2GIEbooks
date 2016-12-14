package beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Auteur;

/**
 * Bean gérant les auteurs
 * @author Clement
 *
 */
@Stateless
public class GestionAuteur {

	@PersistenceContext(unitName = "Database-unit")
	private EntityManager em;

	/**
	 * Fonction créeant proprement un auteur avec un nom et un prenom s'il n'existe pas encore.
	 * @param nom Le nom de l'auteur
	 * @param prenom Le prenom de l'auteur
	 * @return Un auteur qui nouvelemnt créer (et enregistré dans la bdd) ou l'auteur déjà existant. 
	 */
	public Auteur creerAuteur(String nom, String prenom) {

		Query q = em.createQuery("select OBJECT(a) from Auteur a where a.nom = \"" + nom + "\" AND a.prenom = \"" + prenom + "\"");
		List<Auteur> la = q.getResultList();
		Auteur a;
		if (la == null || la.isEmpty()) {
			a = new Auteur(nom, prenom);
			em.persist(a);
		} else
			a = la.get(0);

		return a;
	}
	
	/**
	 * Fonction donnant un auteur identifié par son id
	 * @param id L'id de l'auteur a chercher
	 * @return Un auteur d'id id, null s'il n'existe pas
	 */
	public Auteur getAuteur(long id){
		return em.find(Auteur.class, id);
	}

	/**
	 * Fonction donnant tous les auteurs de la base de donnée
	 * @return Une liste contenant tous les auteurs de la base de donnée
	 */
	public List<Auteur> getLesAuteurs() {
		Query q = em.createQuery("select OBJECT(b) from Auteur b");
		return (List<Auteur>) q.getResultList();
	}

	/**
	 * Fonction qui supprime tous les auteurs connus
	 */
	public void supprimerTous() {
		Query q = em.createNativeQuery("DELETE FROM Auteur");
		q.executeUpdate();
	}

	/**
	 * Fonction servant a modifier les donnée d'un auteur (nom, prenom)
	 * @param id L'id de l'auteur a modifier
	 * @param prenom Le nouveau prenom de l'auteur
	 * @param nom Le nouveau nom de l'auteur
	 */
	public void modifierAuteur(long id, String prenom, String nom) {
		
		Auteur g = em.find(Auteur.class, id);
		if (g != null) {
			g.setNom(nom);
			g.setPrenom(prenom);
			em.persist(g);
		}
	}

}
