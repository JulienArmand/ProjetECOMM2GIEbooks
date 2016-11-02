package beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
		Auteur herge = creerAuteur("HergÃ©", "");

		Editeur galimard = creerEditeur("Galimard");
		Editeur casterman = creerEditeur("Casterman");

		Genre romans = creerGenre("Romans");
		Genre bd = creerGenre("BD");

		Livre miserable = creerLivre("Les misÃ©rables", hugo, galimard, romans, "isbn0325465", 124, 9.99, "Francais",
				"Francais", "images/defaultCouv.png");
		Livre claudeGueux = creerLivre("Claude Gueux", hugo, galimard, romans, "isbn0325465", 124, 9.42, "Francais",
				"Francais", "images/defaultCouv.png");
		Livre notreDame = creerLivre("Notre-Dame de Paris", hugo, galimard, romans, "isbn0325465", 124, 9.12,
				"Francais", "Francais", "images/defaultCouv.png");
		Livre tintinCongo = creerLivre("Tintin au Congo", herge, casterman, bd, "isbn0325465", 124, 9.99, "Francais",
				"Francais", "images/defaultCouv.png");
		Livre lotusBleu = creerLivre("Tintin : le lotus bleu", herge, casterman, bd, "isbn0325465", 124, 9.98,
				"Francais", "Francais", "images/defaultCouv.png");
		Livre cigarePharaon = creerLivre("Tintin : les cigares du pharaon", herge, casterman, bd, "isbn0325465", 124,
				9.01, "Francais", "Francais", "images/defaultCouv.png");

		creerPromotion(miserable,20);
		creerPromotion(claudeGueux,20);
		creerPromotion(notreDame,20);
		creerPromotion(tintinCongo,20);

		creerAvis(tintinCongo, 3);
		creerAvis(tintinCongo, 4);
		creerAvis(tintinCongo, 1);
	}

	public Livre creerLivre(String nom, Auteur a, Editeur e, Genre g, String isbn, int nbpage, double prix,
			String langue, String langueOriginale, String couverture) {

		Date d = Date.from(Instant.now());
		Random r = new Random();
		d.setMonth(r.nextInt(12) + 1);

		Livre l = new Livre(nom, isbn, d, nbpage, (float) prix, langue, langueOriginale);
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

	public Avis creerAvis(Livre l, int note) {

		Date datePublication = Date.from(Instant.now());

		Avis a = new Avis(note, "commentaire", datePublication);

		a.setLeLivre(l);
		l.getLesAvis().add(a);

		em.persist(a);
		return a;
	}

	public Promotion creerPromotion(Livre l, int i) {

		Date debut = Date.from(Instant.now());
		Date fin = Date.from(Instant.now());
		fin.setYear(2017);

		Promotion p = new Promotion(i, debut, fin);

		p.setLivre(l);
		l.setPromotion(p);

		em.persist(p);
		return p;
	}

	public void suppressionBD() {

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
		// Query q13 = em.createNativeQuery("DELETE FROM CarteBancaire");
		// Query q14 = em.createNativeQuery("DELETE FROM Paypal");

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
		// q13.executeUpdate();
		// q14.executeUpdate();

	}

	public Auteur creerAuteur(String nom, String prenom) {
		
		Query q = em.createQuery("select OBJECT(a) from Auteur a where a.nom = \"" + nom + "\" AND a.prenom = \"" + prenom + "\"");
		List<Auteur> la = q.getResultList();
		Auteur a = null;
		if(la == null || la.size() == 0){
			a = new Auteur(nom, prenom);
			System.out.println("Auteur "+a.getNom() + " " + a.getPrenom() + " créé");
			em.persist(a);
		}else
			a = la.get(0);

		return a;
	}

	public Editeur creerEditeur(String nom) {
		
		Query q = em.createQuery("select OBJECT(e) from Editeur e where e.nom = \"" + nom + "\"");
		List<Editeur> le = q.getResultList();
		Editeur e = null;
		if(le == null || le.size() == 0){
			e = new Editeur(nom);
			System.out.println("Editeur "+e.getNom() + " créé");
			em.persist(e);
		}else
			e = le.get(0);

		return e;
	}

	public Genre creerGenre(String nom) {
		
		Query q = em.createQuery("select OBJECT(g) from Genre g where g.nom = \"" + nom + "\"");
		List<Genre> lg = q.getResultList();
		Genre g = null;
		if(lg == null || lg.size() == 0){
			g = new Genre(nom);
			System.out.println("Genre "+g.getNom() + " créé");
			em.persist(g);
		}else
			g = lg.get(0);

		return g;
	}

	public Livre getLivreAvecId(int id) {
		Query q = em.createQuery("select OBJECT(b) from Livre b where b.id = " + id);
		Livre livre = (Livre) q.getSingleResult();
		return livre;
	}

	public List<Livre> getLesLivres() {

		Query q = em.createQuery("select OBJECT(b) from Livre b");
		List<Livre> list = (List<Livre>) q.getResultList();
		return list;
	}

	public List<Livre> getLesLivresEnPromotion() {

		Query q = em.createQuery("select p.livre from Promotion p where p.dateFin > CURRENT_DATE");
		List<Livre> list = (List<Livre>) q.getResultList();
		return list.subList(0, list.size()-1);
	}

	public List<Auteur> getLesAuteurs() {

		Query q = em.createQuery("select OBJECT(b) from Auteur b");
		List<Auteur> list = (List<Auteur>) q.getResultList();
		return list;
	}

	public void InitBDFromCSV() throws IOException, URISyntaxException {
		suppressionBD();
		
		URL url = new URL("http://localhost:8080/exemplesBD.csv");
		InputStream is = url.openStream();
		
		//File f = new File(new URI("file:///localhost:8080/exemplesBD.csv"));
		BufferedReader r = new BufferedReader(new InputStreamReader(is));
		r.readLine();
		String line = r.readLine();
		while (line != null && !line.equals("")) {
			
			//String data[] = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
			String data[] = line.split(";", -1);
			//System.out.println(line);
			String titre = data[0];
			if (titre.equals(""))
				continue;
			String isbn = data[1];
			String date = data[2];
			String nbPages = data[3];
			String prix = data[4].replace(",", ".");
			String langue = data[5];
			String langueO = data[6];
			String couv = data[7];
			String resume = data[8];
			String genre = data[9];
			String auteurs = data[10];
			String editeur = data[11];
			String promo = data[12];
			String collection = data[13];
			Livre livre = new Livre();
			livre.setTitre(titre);
			livre.setIsbn(isbn);
			livre.setDateDePublication(new Date(date));
			livre.setNbPages(Integer.parseInt(nbPages));
			livre.setPrix(Float.parseFloat(prix));
			livre.setLangue(langue);
			livre.setLangueOrigine(langueO);
			livre.setNomCouverture(couv);
			livre.setResume(resume);
			livre.setEditeur(this.creerEditeur(editeur));
			livre.setGenre(this.creerGenre(genre));
			em.persist(livre);
			if(!promo.equals(""))
				livre.setPromotion(this.creerPromotion(livre,Integer.parseInt(promo)));
			String a[] = auteurs.split(",");
			for(int i =0; i<a.length;i++){
				
				String str[] = a[i].split("/s");		
				Auteur x;
				if(str.length>1)
					x = creerAuteur(str[1].replace("/s", ""), str[0].replace("/s", ""));
				else if(str.length==1)
					x = creerAuteur(str[0].replace("/s", ""), "");
				else
					continue;
				livre.getLesAuteurs().add(x);
			}
			
			line = r.readLine();
		}

		r.close();
	}

}
