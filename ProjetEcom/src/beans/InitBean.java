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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Auteur;
import model.Avis;
import model.Client;
import model.Editeur;
import model.Genre;
import model.Livre;
import model.Promotion;
import model.Vente;

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

		Livre miserable = creerLivre("Les misérables", hugo, galimard, romans, "isbn0325465", 124, 9.99, "Francais",
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

		creerAvis(tintinCongo, 3, "commentaire1");
		creerAvis(tintinCongo, 4, "commentaire1");
		creerAvis(tintinCongo, 1, "commentaire1");
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
	
	public Client creerClient(String nom, String prenom) {

		Client c = new Client("sevaeb","monemail", "monMDP", nom, prenom);

		em.persist(c);
		return c;
	}
	
	public Vente creerVente(Livre l) {

		Vente c = new Vente(9);
		c.setLivre(l);

		em.persist(c);
		return c;
	}

	public Avis creerAvis(Livre l, int note, String commentaire) {

		Date datePublication = Date.from(Instant.now());

		Avis a = new Avis(note, commentaire, datePublication);

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
			System.out.println("Auteur "+a.getNom() + " " + a.getPrenom() + " cr��");
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
			System.out.println("Editeur "+e.getNom() + " cr��");
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
			System.out.println("Genre "+g.getNom() + " cr��");
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
//		return getLivreRecherche();
	}

	public List<Auteur> getLesAuteurs() {

		Query q = em.createQuery("select OBJECT(b) from Auteur b");
		List<Auteur> list = (List<Auteur>) q.getResultList();
		return list;
	}
	
	public Set<Livre> getDixLivresLesPlusVendu() {

		Query q = em.createQuery("SELECT v.livre, count(v.livre) as truc FROM Vente v GROUP BY v.livre order by truc Desc");
		q.setMaxResults(10);
		List<Object[]> list = q.getResultList();
		
		Map<Livre, Long> resultMap = new HashMap<Livre, Long>(list.size());
		for (Object[] result : list)
		  resultMap.put((Livre)result[0], (Long)result[1]);

		return resultMap.keySet();
	}
	
	/**
	 * Recherche les livres qui possedent 
	 * 		-tous les mots du champ texte 
	 * 		-un des genres
	 */
	public List<Livre> getLivreRecherche(/*String texte,List<String> genre*/) {
		//A supprimer 
		String texte ="walking et";

		List<String> genre= new ArrayList<>();
		String manga ="Manga";
		String comics="Comics";
//		genre.add(manga);
//		genre.add(comics);
		//fin supprimer
		
		String[] s=texte.split(" ");
		String requete="SELECT OBJECT(l) FROM Livre l ";
		if(s.length>0 || !genre.isEmpty()){
			requete=requete+ "WHERE ";
			if(s.length>0 ){
				int compteur =0;
				while(compteur<s.length-1){
					requete= requete + "UPPER(l.titre) LIKE '%"+s[compteur].toUpperCase()+"%' AND ";
					compteur++;
				}
				requete= requete + "UPPER(l.titre) LIKE '%"+s[compteur].toUpperCase()+"%' ";
			}
			
			if(!genre.isEmpty()){
				int compteur =0;
				while(compteur<genre.size()-1){
					requete= requete + "l.genre.nom = \""+genre.get(compteur)+"\" OR ";
					compteur++;
				}
				requete= requete + "l.genre.nom = \""+genre.get(compteur)+"\" ";
			}
		}
//		requete="SELECT OBJECT(l) FROM Livre l Where l.titre LIKE '%et%' AND l.titre LIKE '%Walking%' OR l.genre.nom=\"Manga\"";
		Query q = em.createQuery(requete);
		List<Livre> list = (List<Livre>) q.getResultList();
		return list;
	}
	
	public void InitBDFromCSV() throws IOException, URISyntaxException {
		suppressionBD();
		
		URL url = new URL("http://localhost:8080/exemplesBD.csv");
		InputStream is = url.openStream();
		
		ArrayList<Livre> livres = new ArrayList<Livre>();
		//File f = new File(new URI("file:///localhost:8080/exemplesBD.csv"));
		BufferedReader r = new BufferedReader(new InputStreamReader(is));
		r.readLine();
		String line = r.readLine();
		while (line != null && !line.equals("")) {
			System.out.println(line);
			//String data[] = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
			String data[] = line.split(";", -1);
			//System.out.println(line);
			String titre = data[0];
			if (titre.equals(""))
				break;
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
			livres.add(livre);
			livre.setTitre(titre);
			livre.setIsbn(isbn);
			String pattern = "dd/MM/yyyy";
		    SimpleDateFormat format = new SimpleDateFormat(pattern);
			try {
				livre.setDateDePublication(format.parse(date));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			em.persist(livre);
			line = r.readLine();
		}

		r.close();
		creerVente(livres.get(0));
		creerVente(livres.get(1));
		creerVente(livres.get(0));
		creerVente(livres.get(1));
		creerVente(livres.get(0));
		creerVente(livres.get(1));
		
		
		creerVente(livres.get(2));
		creerVente(livres.get(2));
		creerVente(livres.get(2));
		creerVente(livres.get(2));
		
		String commentaire = "Je referme \"le premier miracle\" de Gilles Legardinier. \nHabitu� aux com�dies loufoques qui m'ont valu des fous rires m�morables, l'auteur revient un peu � ses premi�res amours, le thriller.Ce nouveau roman, savant m�lange d'aventure et d'humour, nous prouve que Gilles a plus d'une corde � son arc.L'impression d'�tre dans un Indiana Jones, parcourant le monde avec les personnages, d�couvrant des pans entiers de l'histoire de l'humanit�, tentant de percer le secret du premier miracle. Un vrai r�gal.Le tout bourr� d'humour.Les personnages sont touchants, attachants, � la personnalit� riche, que l'on d�couvre au fil des pages. Avec une jolie histoire d'amour � la cl�.Un bon moment de lecture.";
		creerAvis(livres.get(2), 0, commentaire);
		creerAvis(livres.get(2), 1, commentaire);
		creerAvis(livres.get(2), 1, commentaire);
		
		creerAvis(livres.get(0), 4, commentaire);
		creerAvis(livres.get(0), 4, commentaire);
		creerAvis(livres.get(0), 5, commentaire);
		
		creerAvis(livres.get(1), 3, commentaire);
		creerAvis(livres.get(1), 2, commentaire);
		creerAvis(livres.get(1), 2, commentaire);
	}

}
