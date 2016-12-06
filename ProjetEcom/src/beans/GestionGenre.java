package beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Genre;
import model.Livre;

@Stateless
public class GestionGenre {

	@PersistenceContext(unitName = "Database-unit")
	private EntityManager em;

	public Genre creerGenre(String nom) {

		Query q = em.createQuery("select OBJECT(g) from Genre g where g.nom = \"" + nom + "\"");
		List<Genre> lg = q.getResultList();
		Genre g = null;
		if (lg == null || lg.size() == 0) {
			g = new Genre(nom);
			System.out.println("Genre " + g.getNom() + " créé");
			em.persist(g);
		} else
			g = lg.get(0);

		return g;
	}

	public void supprimerTous() {
		Query q = em.createNativeQuery("DELETE FROM Genre");
		Query q2 = em.createNativeQuery("ALTER TABLE Genre {ALTER id RESTART WITH 0} ");
		q.executeUpdate();
		q2.executeUpdate();
	}

	public List<Genre> getTousLesGenres() {
		Query q = em.createQuery("select OBJECT(g) from Genre g");
		List<Genre> list = (List<Genre>) q.getResultList();
		return list;
	}

	public void modifierGenre(Long id, String nom) {
		Genre g = em.find(Genre.class, id);
		if (g != null) {
			System.out.println("Modif du genre d'id : " + g.getId());
			g.setNom(nom);
			em.persist(g);

		} else {
			System.out.println("Modification d'n genre null !!!!!");
			return;
		}

	}

}
