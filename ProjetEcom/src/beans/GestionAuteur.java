package beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Auteur;

@Stateless
public class GestionAuteur {

	@PersistenceContext(unitName = "Database-unit")
	private EntityManager em;

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
	
	public Auteur getAuteur(long id){
		return em.find(Auteur.class, id);
	}

	public List<Auteur> getLesAuteurs() {
		Query q = em.createQuery("select OBJECT(b) from Auteur b");
		return (List<Auteur>) q.getResultList();
	}

	public void supprimerTous() {
		Query q = em.createNativeQuery("DELETE FROM Auteur");
		Query q2 = em.createNativeQuery("ALTER TABLE Auteur {ALTER id RESTART WITH 0} ");
		q.executeUpdate();
		q2.executeUpdate();
	}

	public void modifierAuteur(long id, String prenom, String nom) {
		
		Auteur g = em.find(Auteur.class, id);
		if (g != null) {
			g.setNom(nom);
			g.setPrenom(prenom);
			em.persist(g);
		}
	}

}
