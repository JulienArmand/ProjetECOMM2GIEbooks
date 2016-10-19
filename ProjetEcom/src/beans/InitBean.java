package beans;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import model.Auteur;
import model.Editeur;
import model.Genre;
import model.Livre;

@Stateless
public class InitBean {
	

	
	@PersistenceContext(unitName = "Database-unit") 
	private EntityManager em; 
	public void init() {
		
		
		//Collection<Auteur> al1 = new LinkedList<>();
		Auteur a1 = new Auteur("Hugo", "Victor");
		Editeur e1 = new Editeur("EditeurBidon");
		Genre g1 = new Genre("Romans");
		Livre l1 = new Livre("Les misérables", "isbn0325465", Date.from(Instant.now()), 124, 9, "Francais", "Francais");

		
		l1.setEditeur(e1);
		l1.getLesAuteurs().add(a1);
		l1.setLesAuteurs(new LinkedList<>());
		//l1.setLesAuteurs(al1);
		l1.setGenre(g1);
		
		em.persist(e1);
		em.persist(g1);
		em.persist(a1);
		//em.persist(al1);
		em.persist(l1);
	}
	
	public Livre getFirstLivre(){
		
		Query q = em.createQuery("select OBJECT(b) from Livre b"); 
		List<Livre> list = (List<Livre>) q.getResultList(); 
		return list.get(list.size()-1);
	}
	
}
