package beans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import Tools.ElasticSearchTools;
import Tools.Tools;
import model.Auteur;
import model.Client;
import model.Editeur;
import model.Genre;
import model.Livre;
import model.Promotion;

@Stateless
public class InitBean {
 
	@PersistenceContext(unitName = "Database-unit")
	private EntityManager em;

	@EJB // ou @EJB si nom par défaut
	private GestionLivre gestionLivre;

	@EJB // ou @EJB si nom par défaut
	private GestionAvis gestionAvis;
	
	@EJB // ou @EJB si nom par défaut
	private GestionAuteur gestionAuteur;
	
	@EJB // ou @EJB si nom par défaut
	private GestionClient gestionClient;

	@EJB // ou @EJB si nom par défaut
	private GestionGenre gestionGenre;

	@EJB // ou @EJB si nom par défaut
	private GestionPromotion gestionPromotion;

	@EJB // ou @EJB si nom par défaut
	private GestionVente gestionVente;

	@EJB // ou @EJB si nom par défaut
	private GestionEditeur gestionEditeur;

	public void suppressionBD() throws Exception {

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
		try {
			ElasticSearchTools.creerIndex();
		} catch (Exception e) {
			System.err.println("Erreur durant la céation de l'index : " + e.getMessage());
		}
	}

	public void InitBDFromCSV() throws IOException, URISyntaxException {
		try {
			suppressionBD();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		URL url = new URL("http://localhost:8080/exemplesBD.csv");
		InputStream is = url.openStream();

		ArrayList<Livre> livres = new ArrayList<Livre>();
		// File f = new File(new URI("file:///localhost:8080/exemplesBD.csv"));
		BufferedReader r = new BufferedReader(new InputStreamReader(is));
		r.readLine();
		String line = r.readLine();
		while (line != null && !line.equals("")) {
			System.out.println(line);
			// String data[] = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)",
			// -1);
			String data[] = line.split(";", -1);
			// System.out.println(line);
			String titre = data[0];
			if (titre.equals(""))
				break;

			String isbn = data[1];
			String date = data[2];
			int nbPages = Integer.parseInt(data[3]);
			double prix = Double.parseDouble(data[4].replace(",", "."));
			String langue = data[5];
			String langueO = data[6];
			String couv = data[7];
			String resume = "D'après une nouvelle histoire originale de J.K. Rowling, John Tiffany et Jack Thorne, la nouvelle pièce de théâtre de Jack Thorne, Harry Potter et l'Enfant Maudit est la huitième histoire de la saga Harry Potter et la première histoire de Harry Potter officiellement destinée à  la scène. La première mondiale de la pièce a eu lieu à  Londres dans un théâtre du West End le 30 juillet 2016. Être Harry Potter n'a jamais été facile et ne l'est pas davantage depuis qu'il est un employé surmené du Ministère de la Magie, marié et pére de trois enfants. Tandis que Harry se débat avec un passé qui refuse de le laisser en paix, son plus jeune fils, Albus, doit lutter avec le poids d'un héritage familial dont il n'a jamais voulu. Le destin vient fusionner passé et présent. Père et fils se retrouvent face à une dure vérité : parfois, les ténebres surviennent des endroits les plus inattendus.";
			Genre genre = gestionGenre.creerGenre(data[9]);
			Editeur editeur = gestionEditeur.creerEditeur(data[11]);
			Promotion promo = null;
			if (!data[12].equals(""))
				promo = gestionPromotion.creerPromotion(Integer.parseInt(data[12]));

			String collection = data[13];
			Date datePub = null;
			String pattern = "dd/MM/yyyy";
			SimpleDateFormat format = new SimpleDateFormat(pattern);

			try {
				datePub = format.parse(date);
			} catch (ParseException e) {
				System.err.println("Mauvais format de date : " + date);
				datePub = Date.from(Instant.now());
			}

			String auteurs = data[10];
			String a[] = auteurs.split(",");
			List<Auteur> lesAuteurs = new LinkedList<>();

			for (int i = 0; i < a.length; i++) {
				String str[] = a[i].split("/s");
				Auteur x;
				if (str.length > 1)
					x = gestionAuteur.creerAuteur(str[1].replace("/s", ""), str[0].replace("/s", ""));
				else if (str.length == 1)
					x = gestionAuteur.creerAuteur(str[0].replace("/s", ""), "");
				else
					continue;
				lesAuteurs.add(x);
			}

			Livre livre;
			try {
				livre = gestionLivre.creerLivre(titre, lesAuteurs, editeur, genre, isbn, nbPages, prix, langue,
						langueO, couv, promo, resume, datePub);
				livres.add(livre);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//try {
			//	ElasticSearchTools.enregistrerDansLIndexage(livre);
			//} catch (Exception e) {
			//	System.err.println("Erreur durant l'indexage du livre dans elasticsearch : " + e.getMessage());
			//}

			line = r.readLine();
		}

		r.close();
		gestionVente.creerVente(livres.get(0));
		gestionVente.creerVente(livres.get(1));
		gestionVente.creerVente(livres.get(0));
		gestionVente.creerVente(livres.get(1));
		gestionVente.creerVente(livres.get(0));
		gestionVente.creerVente(livres.get(1));

		gestionVente.creerVente(livres.get(2));
		gestionVente.creerVente(livres.get(2));
		gestionVente.creerVente(livres.get(2));
		gestionVente.creerVente(livres.get(2));

		String commentaire = "Je referme \"le premier miracle\" de Gilles Legardinier. \nHabituï¿½ aux comï¿½dies loufoques qui m'ont valu des fous rires mï¿½morables, l'auteur revient un peu ï¿½ ses premiï¿½res amours, le thriller.Ce nouveau roman, savant mï¿½lange d'aventure et d'humour, nous prouve que Gilles a plus d'une corde ï¿½ son arc.L'impression d'ï¿½tre dans un Indiana Jones, parcourant le monde avec les personnages, dï¿½couvrant des pans entiers de l'histoire de l'humanitï¿½, tentant de percer le secret du premier miracle. Un vrai rï¿½gal.Le tout bourrï¿½ d'humour.Les personnages sont touchants, attachants, ï¿½ la personnalitï¿½ riche, que l'on dï¿½couvre au fil des pages. Avec une jolie histoire d'amour ï¿½ la clï¿½.Un bon moment de lecture.";
		try {
			Client s = gestionClient.creerClient("sevaeb", "seb@alcoholiquesAnonymes.com", "prout", "Sebastien", "O.");
			gestionAvis.creerAvis(livres.get(2), s, 0, commentaire);
			gestionAvis.creerAvis(livres.get(2), s, 1, commentaire);
			gestionAvis.creerAvis(livres.get(2), s, 1, commentaire);

			gestionAvis.creerAvis(livres.get(0), s, 4, commentaire);
			gestionAvis.creerAvis(livres.get(0), s, 4, commentaire);
			gestionAvis.creerAvis(livres.get(0), s, 5, commentaire);

			gestionAvis.creerAvis(livres.get(1), s, 3, commentaire);
			gestionAvis.creerAvis(livres.get(1), s, 2, commentaire);
			gestionAvis.creerAvis(livres.get(1), s, 2, commentaire);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
