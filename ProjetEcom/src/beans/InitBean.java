package beans;

import java.time.Instant;
import java.util.Date;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Auteur;
import model.Editeur;
import model.Genre;
import model.Livre;

@Stateless
public class InitBean {
	
	@PersistenceContext 
	private EntityManager em; 
	public void init() {
		
		LinkedList<Auteur> a1 = new LinkedList<>();
		a1.add(new Auteur("Hugo", "Victor"));
		Editeur e1 = new Editeur("EditeurBidon");
		Genre g1 = new Genre("Romans");
		Livre l1 = new Livre("Les misérables", "isbn0325465", Date.from(Instant.now()), 124, 9, "Francais", "Francais");
		l1.setEditeur(e1);
		
		l1.setLesAuteurs(new LinkedList<>());
		l1.setLesAuteurs(a1);
		l1.setGenre(g1);
		
		em.persist(l1); 
	} 
	
}
