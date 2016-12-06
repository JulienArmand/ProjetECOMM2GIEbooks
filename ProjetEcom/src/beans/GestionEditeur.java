package beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Editeur;

@Stateless
public class GestionEditeur {

	@PersistenceContext(unitName = "Database-unit")
	private EntityManager em;

	public Editeur creerEditeur(String nom) {

		Query q = em.createQuery("select OBJECT(e) from Editeur e where e.nom = \"" + nom + "\"");
		List<Editeur> le = q.getResultList();
		Editeur e = null;
		if (le == null || le.size() == 0) {
			e = new Editeur(nom);
			System.out.println("Editeur " + e.getNom() + " crï¿½ï¿½");
			em.persist(e);
		} else
			e = le.get(0);

		return e;
	}

	public Editeur getEditeur(long id){
		return em.find(Editeur.class, id);
	}
	
	public List<Editeur> getLesEditeurs() {
		Query q = em.createQuery("select OBJECT(b) from Editeur b");
		List<Editeur> list = (List<Editeur>) q.getResultList();
		return list;
	}
	
	public void supprimerTous() {
		Query q = em.createNativeQuery("DELETE FROM Editeur");
		Query q2 = em.createNativeQuery("ALTER TABLE Editeur {ALTER id RESTART WITH 0} ");
		q.executeUpdate();
		q2.executeUpdate();
	}
}
