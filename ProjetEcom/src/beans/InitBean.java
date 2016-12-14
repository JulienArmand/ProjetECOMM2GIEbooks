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
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Auteur;
import model.Client;
import model.Editeur;
import model.Genre;
import model.Livre;
import model.Promotion;
import tools.ElasticSearchTools;

/**
 * Bean servant a initialiser la base
 * @author Clement
 *
 */
@Stateless
public class InitBean {
 
	@PersistenceContext(unitName = "Database-unit")
	private EntityManager em;

	@EJB 
	private GestionLivre gestionLivre;

	@EJB
	private GestionAvis gestionAvis;
	
	@EJB
	private GestionAuteur gestionAuteur;
	
	@EJB
	private GestionClient gestionClient;
	
	@EJB
	private GestionCommande gestionCommande;

	@EJB 
	private GestionGenre gestionGenre;
	
	@EJB
	private GestionPaiement gestionPaiement;
	
	@EJB
	private GestionPromotion gestionPromotion;

	@EJB 
	private GestionVente gestionVente;

	@EJB
	private GestionEditeur gestionEditeur;
	
	@EJB()
	private ConfigurationGenerale config;
	
	private static final String MSGERREUR = "an exception was thrown";

	/**
	 * Purge l'intégralité de la base
	 * Créé l'index d'elasticseach
	 */
	public void suppressionBD() {

//		Query q1 = em.createNativeQuery("DELETE FROM Genre");
//		Query q2 = em.createNativeQuery("DELETE FROM Vente");
//		Query q3 = em.createNativeQuery("DELETE FROM Editeur");
//		Query q4 = em.createNativeQuery("DELETE FROM Livre");
//		Query q6 = em.createNativeQuery("DELETE FROM Avis");
//		Query q7 = em.createNativeQuery("DELETE FROM Promotion");
		Query q8 = em.createNativeQuery("DELETE FROM LIVRE_AUTEUR_LIEN");
//		Query q9 = em.createNativeQuery("DELETE FROM Auteur");
//		Query q10 = em.createNativeQuery("DELETE FROM Commande");
//		Query q11 = em.createNativeQuery("DELETE FROM MoyenPaiement");
//		Query q12 = em.createNativeQuery("DELETE FROM Client");
//
//		q1.executeUpdate();
//		q2.executeUpdate();
//		q3.executeUpdate();
//		q4.executeUpdate();
//		q6.executeUpdate();
//		q7.executeUpdate();
		q8.executeUpdate();
//		q9.executeUpdate();
//		q10.executeUpdate();
//		q11.executeUpdate();
//		q12.executeUpdate();
		
		gestionGenre.supprimerTous();
		gestionVente.supprimerTous();
		gestionEditeur.supprimerTous();
		gestionLivre.supprimerTous();
		gestionAvis.supprimerTous();
		gestionPromotion.supprimerTous();
		gestionAuteur.supprimerTous();
		gestionCommande.supprimerTous();
		gestionPaiement.supprimerTous();
		gestionClient.supprimerTous();		
		
		Logger logger = Logger.getAnonymousLogger();
		
		try {
			ElasticSearchTools.creerIndex("http://"+config.get("IP_ELASTICSEARCH")+":"+config.get("PORT_ELASTICSEARCH")+"/livres");
		} catch (Exception e) {
			logger.log(Level.SEVERE, MSGERREUR +  " Erreur durant la céation de l'index : ", e);
		}
	}

	/**
	 * Initialise la base
	 * Lit un fichier csv de livres et les enregistres dans la base
	 * Ajoutes des ventes et des avis 
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public void initBDFromCSV() throws IOException, URISyntaxException {
		Logger logger = Logger.getAnonymousLogger();
		try {
			suppressionBD();
		} catch (Exception e1) {
			logger.log(Level.SEVERE, MSGERREUR, e1);
		}

		URL url = new URL("http://localhost:8080/exemplesBD.csv");
		InputStream is = url.openStream();

		ArrayList<Livre> livres = new ArrayList<>();
		BufferedReader r = new BufferedReader(new InputStreamReader(is));
		String line = r.readLine();
		String empty = "";
		while ((line = r.readLine()) != null && !line.equals(empty)) {
			String[] data = line.split(";", -1);
			String titre = data[0];
			if (titre.equals(empty))
				break;

			String isbn = data[1];
			String date = data[2];
			int nbPages = Integer.parseInt(data[3]);
			float prix = Float.parseFloat(data[4].replace(",", "."));
			String langue = data[5];
			String langueO = data[6];
			String couv = data[7];
			String resume = "D'après une nouvelle histoire originale de J.K. Rowling, John Tiffany et Jack Thorne, la nouvelle pièce de théâtre de Jack Thorne, Harry Potter et l'Enfant Maudit est la huitième histoire de la saga Harry Potter et la première histoire de Harry Potter officiellement destinée à  la scène. La première mondiale de la pièce a eu lieu à  Londres dans un théâtre du West End le 30 juillet 2016. Être Harry Potter n'a jamais été facile et ne l'est pas davantage depuis qu'il est un employé surmené du Ministère de la Magie, marié et pére de trois enfants. Tandis que Harry se débat avec un passé qui refuse de le laisser en paix, son plus jeune fils, Albus, doit lutter avec le poids d'un héritage familial dont il n'a jamais voulu. Le destin vient fusionner passé et présent. Père et fils se retrouvent face à une dure vérité : parfois, les ténebres surviennent des endroits les plus inattendus.";
			Genre genre = gestionGenre.creerGenre(data[9].replace(" ", "_"));
			Editeur editeur = gestionEditeur.creerEditeur(data[11]);
			Promotion promo = null;
			if (!data[12].equals(empty))
				promo = gestionPromotion.creerPromotion(Integer.parseInt(data[12]));

			Date datePub = null;
			String pattern = "dd/MM/yyyy";
			SimpleDateFormat format = new SimpleDateFormat(pattern);

			try {
				datePub = format.parse(date);
			} catch (ParseException e) {
				logger.log(Level.WARNING, MSGERREUR + " Mauvais format de date : ", e);
				datePub = Date.from(Instant.now());
			}

			String auteurs = data[10];
			String[] a = auteurs.split(",");
			List<Auteur> lesAuteurs = new LinkedList<>();

			for (int i = 0; i < a.length; i++) {
				String[] str = a[i].split("/s");
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
				logger.log(Level.SEVERE, MSGERREUR, e);
				
			}
		}

		r.close();
	}

}
