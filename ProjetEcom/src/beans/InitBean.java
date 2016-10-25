package beans;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Auteur;
import model.Avis;
import model.Editeur;
import model.Genre;
import model.Livre;
import model.Promotion;

@Stateless
public class InitBean {
	

	
	@PersistenceContext(unitName = "Database-unit") 
	private EntityManager em; 
	public void init() {
		suppressionBD();
		
		Auteur hugo = creerAuteur("Hugo", "Victor");
		Auteur herge = creerAuteur("Hergé", "");
		
		Editeur galimard = creerEditeur("Galimard");
		Editeur casterman = creerEditeur("Casterman");
		
		Genre romans = creerGenre("Romans");
		Genre bd = creerGenre("BD");
		
		Livre miserable = creerLivre("Les misérables", hugo, galimard, romans, "isbn0325465", 124, 9.99, "Francais", "Francais", "defaultCouv.png");
		Livre claudeGueux = creerLivre("Claude Gueux", hugo, galimard, romans, "isbn0325465", 124, 9.42, "Francais", "Francais", "defaultCouv.png");
		Livre notreDame = creerLivre("Notre-Dame de Paris", hugo, galimard, romans, "isbn0325465", 124, 9.12, "Francais", "Francais", "defaultCouv.png");
		Livre tintinCongo = creerLivre("Tintin au Congo", herge, casterman, bd, "isbn0325465", 124, 9.99, "Francais", "Francais", "defaultCouv.png");
		Livre lotusBleu = creerLivre("Tintin : le lotus bleu", herge, casterman, bd, "isbn0325465", 124, 9.98, "Francais", "Francais", "defaultCouv.png");
		Livre cigarePharaon = creerLivre("Tintin : les cigares du pharaon", herge, casterman, bd, "isbn0325465", 124, 9.01, "Francais", "Francais", "defaultCouv.png");	

		creerPromotion(miserable);
		creerPromotion(claudeGueux);
		creerPromotion(notreDame);
		creerPromotion(tintinCongo);
		
		creerAvis(tintinCongo, 3);
		creerAvis(tintinCongo, 4);
		creerAvis(tintinCongo, 1);
	}
	
	public Livre creerLivre(String nom, Auteur a, Editeur e, Genre g, String isbn, int nbpage, double prix, String langue, String langueOriginale, String couverture){
		
		Livre l = new Livre(nom, isbn, Date.from(Instant.now()), nbpage, (float)prix, langue, langueOriginale);
		l.setNomCouverture(couverture);

		
		l.setEditeur(e);
		l.getLesAuteurs().add(a);
		a.getLesLivres().add(l);
		e.getLesLivres().add(l);
		l.setGenre(g);
		g.getLesLivres().add(l);
		
		em.persist(l);
		return l;
	}
	
	public Avis creerAvis(Livre l, int note){
		
		Date datePublication = Date.from(Instant.now());
		
		Avis a = new Avis(note, "commentaire", datePublication);

		a.setLeLivre(l);
		l.getLesAvis().add(a);
		
		em.persist(a);
		return a;
	}
	
	public Promotion creerPromotion(Livre l){
		
		Date debut = Date.from(Instant.now());
		Date fin = Date.from(Instant.now());
		fin.setYear(2017);
		
		Promotion p = new Promotion(20, debut, fin);

		p.setLivre(l);
		l.setPromotion(p);
		
		em.persist(p);
		return p;
	}
	
	public void suppressionBD(){
		
		Query q1 = em.createNativeQuery("DELETE FROM Genre");
	    Query q2 = em.createNativeQuery("DELETE FROM Vente");
	    Query q3 = em.createNativeQuery("DELETE FROM Editeur");
	    Query q4 = em.createNativeQuery("DELETE FROM Livre");
		Query q5 = em.createNativeQuery("DELETE FROM Serie");
	    Query q6 = em.createNativeQuery("DELETE FROM Avis");
	    Query q7 = em.createNativeQuery("DELETE FROM Promotion");
	    Query q8 = em.createNativeQuery("DELETE FROM LIVRE_AUTEUR_LIEN");
		Query q9 = em.createNativeQuery("DELETE FROM Auteur");
	    Query q10 = em.createNativeQuery("DELETE FROM Commande");
	    Query q11 = em.createNativeQuery("DELETE FROM MoyenPaiement");
	    Query q12 = em.createNativeQuery("DELETE FROM Client");
		//Query q13 = em.createNativeQuery("DELETE FROM CarteBancaire");
	    //Query q14 = em.createNativeQuery("DELETE FROM Paypal");

	    q1.executeUpdate();
	    q2.executeUpdate();
	    q3.executeUpdate();
	    q4.executeUpdate();
	    q5.executeUpdate();
	    q6.executeUpdate();
	    q7.executeUpdate();
	    q8.executeUpdate();
	    q9.executeUpdate();
	    q10.executeUpdate();
	    q11.executeUpdate();
	    q12.executeUpdate();
	    //q13.executeUpdate();
	    //q14.executeUpdate();


	}
	
	public Auteur creerAuteur(String nom, String prenom){
		Auteur a = new Auteur(nom, prenom);
		em.persist(a);
		
		return a;
	}
	
	public Editeur creerEditeur(String nom){
		Editeur e = new Editeur(nom);
		em.persist(e);
		return e;
	}
	
	public Genre creerGenre(String nom){
		Genre g = new Genre(nom);
		em.persist(g);
		return g;
	}
	
	public List<Livre> getLesLivres(){
		
		Query q = em.createQuery("select OBJECT(b) from Livre b"); 
		List<Livre> list = (List<Livre>) q.getResultList(); 
		return list;
	}
	
	public List<Livre> getLesLivresEnPromotion(){
		
		Query q = em.createQuery("select p.livre from Promotion p where p.dateFin > CURRENT_DATE"); 
		List<Livre> list = (List<Livre>) q.getResultList(); 
		return list;
	}
	
	public List<Auteur> getLesAuteurs(){
		
		Query q = em.createQuery("select OBJECT(b) from Auteur b"); 
		List<Auteur> list = (List<Auteur>) q.getResultList(); 
		return list;
	}

	
}
