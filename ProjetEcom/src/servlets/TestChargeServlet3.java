package servlets;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.GestionAuteur;
import beans.GestionAvis;
import beans.GestionClient;
import beans.GestionCommande;
import beans.GestionEditeur;
import beans.GestionGenre;
import beans.GestionLivre;
import beans.GestionPromotion;
import beans.GestionVente;
import model.Auteur;
import model.Avis;
import model.Client;
import model.Commande;
import model.Editeur;
import model.Genre;
import model.Livre;
import model.Promotion;
import model.Vente;

public class TestChargeServlet3 extends HttpServlet {

	private static final long serialVersionUID = -3401197851263972685L;
	

		
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().println("[{\"id\":2802,\"titre\":\"Harry Potter à L\u0027école des Sorciers\",\"isbn\":\"isbn2\",\"dateDePublication\":\"Jun 6, 2013 12:00:00 AM\",\"nbPages\":85,\"prix\":8.99,\"langue\":\"Français\",\"langueOrigine\":\"Anglais\",\"nomCouverture\":\"https://ebook.nolim.fr/covers/original/9781781/9781781101032.jpg\",\"resume\":\"D\u0027après une nouvelle histoire originale de J.K. Rowling, John Tiffany et Jack Thorne, la nouvelle pièce de théâtre de Jack Thorne, Harry Potter et l\u0027Enfant Maudit est la huitième histoire de la saga Harry Potter et la première histoire de Harry Potter officiellement destinée à  la scène. La première mondiale de la pièce a eu lieu à  Londres dans un théâtre du West End le 30 juillet 2016. Être Harry Potter n\u0027a jamais été facile et ne l\u0027est pas davantage depuis qu\u0027il est un employé surmené du Ministère de la Magie, marié et pére de trois enfants. Tandis que Harry se débat avec un passé qui refuse de le laisser en paix, son plus jeune fils, Albus, doit lutter avec le poids d\u0027un héritage familial dont il n\u0027a jamais voulu. Le destin vient fusionner passé et présent. Père et fils se retrouvent face à une dure vérité : parfois, les ténebres surviennent des endroits les plus inattendus.\",\"promotion\":{\"id\":2851,\"tauxReduc\":10,\"dateDebut\":\"Dec 8, 2016 12:00:00 AM\",\"dateFin\":\"Dec 8, 3917 12:00:00 AM\"},\"lesAuteurs\":[{\"id\":2751,\"nom\":\"J.K. Rowling\",\"prenom\":\"\"}],\"lesAvis\":[{\"id\":3007,\"note\":3,\"commentaire\":\"Je refermdinier. \nHabitué aux comédies loufoques qui m\u0027ont valu des fous rires mémorables, l\u0027auteur revient un peu à ses premières amours, le thriller. Ce nouveau roman, savant mélange d\u0027aventure et d\u0027humour, nous prouve que Gilles a plus d\u0027une corde à son arc. L\u0027impression d\u0027être dans un Indiana Jones, parcourant le monde avec les personnages, découvrant des pans entiers de l\u0027histoire de l\u0027humanité, tentant de percer le secret du premier miracle. Un vrai régal. Le tout bourré d\u0027humour. Les personnages sont touchants, attachants, à la personnalité riche, que l\u0027on découvre au fil des pages. Avec une jolie histoire d\u0027amour à la clé. Un bon moment de lecture.\",\"dateDePublication\":\"Dec 8, 2016 6:02:08 PM\",\"leClient\":{\"id\":2951,\"pseudo\":\"sevaeb\",\"email\":\"seb@alcoholiquesAnonymes.com\",\"motDePasse\":\"prout\",\"nom\":\"Sebastien\",\"prenom\":\"O.\",\"desinscrit\":false}},{\"id\":3008,\"note\":2,\"commentaire\":\"Je refermeardinier. \nHabitué aux comédies loufoques qui m\u0027ont valu des fous rires mémorables, l\u0027auteur revient un peu à ses premières amours, le thriller. Ce nouveau roman, savant mélange d\u0027aventure et d\u0027humour, nous prouve que Gilles a plus d\u0027une corde à son arc. L\u0027impression d\u0027être dans un Indiana Jones, parcourant le monde avec les personnages, découvrant des pans entiers de l\u0027histoire de l\u0027humanité, tentant de percer le secret du premier miracle. Un vrai régal. Le tout bourré d\u0027humour. Les personnages sont touchants, attachants, à la personnalité riche, que l\u0027on découvre au fil des pages. Avec une jolie histoire d\u0027amour à la clé. Un bon moment de lecture.\",\"dateDePublication\":\"Dec 8, 2016 6:02:08 PM\",\"leClient\":{\"id\":2951,\"pseudo\":\"sevaeb\",\"email\":\"seb@alcoholiquesAnonymes.com\",\"motDePasse\":\"prout\",\"nom\":\"Sebastien\",\"prenom\":\"O.\",\"desinscrit\":false}},{\"id\":3009,\"note\":2,\"commentaire\":\"Je referme nHabitué aux comédies loufoques qui m\u0027ont valu des fous rires mémorables, l\u0027auteur revient un peu à ses premières amours, le thriller. Ce nouveau roman, savant mélange d\u0027aventure et d\u0027humour, nous prouve que Gilles a plus d\u0027une corde à son arc. L\u0027impression d\u0027être dans un Indiana Jones, parcourant le monde avec les personnages, découvrant des pans entiers de l\u0027histoire de l\u0027humanité, tentant de percer le secret du premier miracle. Un vrai régal. Le tout bourré d\u0027humour. Les personnages sont touchants, attachants, à la personnalité riche, que l\u0027on découvre au fil des pages. Avec une jolie histoire d\u0027amour à la clé. Un bon moment de lecture.\",\"dateDePublication\":\"Dec 8, 2016 6:02:08 PM\",\"leClient\":{\"id\":2951,\"pseudo\":\"sevaeb\",\"email\":\"seb@alcoholiquesAnonymes.com\",\"motDePasse\":\"prout\",\"nom\":\"Sebastien\",\"prenom\":\"O.\",\"desinscrit\":false}}],\"genre\":{\"id\":2651,\"nom\":\"Fantastique\"},\"editeur\":{\"id\":2701,\"nom\":\"PotterMore\"}},{\"id\":2803,\"titre\":\"Harry Potter et la Chambre des Secrets\",\"isbn\":\"isbn3\",\"dateDePublication\":\"Oct 1, 2016 12:00:00 AM\",\"nbPages\":174,\"prix\":8.99,\"langue\":\"Français\",\"langueOrigine\":\"Anglais\",\"nomCouverture\":\"https://ebook.nolim.fr/covers/original/9781781/9781781101049.jpg\",\"resume\":\"D\u0027après une nouvelle histoire originale de J.K. Rowling, John Tiffany et Jack Thorne, la nouvelle pièce de théâtre de Jack Thorne, Harry Potter et l\u0027Enfant Maudit est la huitième histoire de la saga Harry Potter et la première histoire de Harry Potter officiellement destinée à  la scène. La première mondiale de la pièce a eu lieu à  Londres dans un théâtre du West End le 30 juillet 2016. Être Harry Potter n\u0027a jamais été facile et ne l\u0027est pas davantage depuis qu\u0027il est un employé surmené du Ministère de la Magie, marié et pére de trois enfants. Tandis que Harry se débat avec un passé qui refuse de le laisser en paix, son plus jeune fils, Albus, doit lutter avec le poids d\u0027un héritage familial dont il n\u0027a jamais voulu. Le destin vient fusionner passé et présent. Père et fils se retrouvent face à une dure vérité : parfois, les ténebres surviennent des endroits les plus inattendus.\",\"promotion\":{\"id\":2852,\"tauxReduc\":5,\"dateDebut\":\"Dec 8, 2016 12:00:00 AM\",\"dateFin\":\"Dec 8, 3917 12:00:00 AM\"},\"lesAuteurs\":[{\"id\":2751,\"nom\":\"J.K. Rowling\",\"prenom\":\"\"}],\"lesAvis\":[{\"id\":3001,\"note\":0,\"commentaire\":\"Je refeGilles Legardinier. \nHabitué aux comédies loufoques qui m\u0027ont valu des fous rires mémorables, l\u0027auteur revient un peu à ses premières amours, le thriller. Ce nouveau roman, savant mélange d\u0027aventure et d\u0027humour, nous prouve que Gilles a plus d\u0027une corde à son arc. L\u0027impression d\u0027être dans un Indiana Jones, parcourant le monde avec les personnages, découvrant des pans entiers de l\u0027histoire de l\u0027humanité, tentant de percer le secret du premier miracle. Un vrai régal. Le tout bourré d\u0027humour. Les personnages sont touchants, attachants, à la personnalité riche, que l\u0027on découvre au fil des pages. Avec une jolie histoire d\u0027amour à la clé. Un bon moment de lecture.\",\"dateDePublication\":\"Dec 8, 2016 6:02:08 PM\",\"leClient\":{\"id\":2951,\"pseudo\":\"sevaeb\",\"email\":\"seb@alcoholiquesAnonymes.com\",\"motDePasse\":\"prout\",\"nom\":\"Sebastien\",\"prenom\":\"O.\",\"desinscrit\":false}},{\"id\":3002,\"note\":1,\"commentaire\":\"Je refelles Legardinier. \nHabitué aux comédies loufoques qui m\u0027ont valu des fous rires mémorables, l\u0027auteur revient un peu à ses premières amours, le thriller. Ce nouveau roman, savant mélange d\u0027aventure et d\u0027humour, nous prouve que Gilles a plus d\u0027une corde à son arc. L\u0027impression d\u0027être dans un Indiana Jones, parcourant le monde avec les personnages, découvrant des pans entiers de l\u0027histoire de l\u0027humanité, tentant de percer le secret du premier miracle. Un vrai régal. Le tout bourré d\u0027humour. Les personnages sont touchants, attachants, à la personnalité riche, que l\u0027on découvre au fil des pages. Avec une jolie histoire d\u0027amour à la clé. Un bon moment de lecture.\",\"dateDePublication\":\"Dec 8, 2016 6:02:08 PM\",\"leClient\":{\"id\":2951,\"pseudo\":\"sevaeb\",\"email\":\"seb@alcoholiquesAnonymes.com\",\"motDePasse\":\"prout\",\"nom\":\"Sebastien\",\"prenom\":\"O.\",\"desinscrit\":false}},{\"id\":3003,\"note\":1,\"commentaire\":\"Je referardinier. \nHabitué aux comédies loufoques qui m\u0027ont valu des fous rires mémorables, l\u0027auteur revient un peu à ses premières amours, le thriller. Ce nouveau roman, savant mélange d\u0027aventure et d\u0027humour, nous prouve que Gilles a plus d\u0027une corde à son arc. L\u0027impression d\u0027être dans un Indiana Jones, parcourant le monde avec les personnages, découvrant des pans entiers de l\u0027histoire de l\u0027humanité, tentant de percer le secret du premier miracle. Un vrai régal. Le tout bourré d\u0027humour. Les personnages sont touchants, attachants, à la personnalité riche, que l\u0027on découvre au fil des pages. Avec une jolie histoire d\u0027amour à la clé. Un bon moment de lecture.\",\"dateDePublication\":\"Dec 8, 2016 6:02:08 PM\",\"leClient\":{\"id\":2951,\"pseudo\":\"sevaeb\",\"email\":\"seb@alcoholiquesAnonymes.com\",\"motDePasse\":\"prout\",\"nom\":\"Sebastien\",\"prenom\":\"O.\",\"desinscrit\":false}}],\"genre\":{\"id\":2651,\"nom\":\"Fantastique\"},\"editeur\":{\"id\":2701,\"nom\":\"PotterMore\"}},{\"id\":2805,\"titre\":\"Une mort esthétique - Une enquête d\u0027Adam Dalgliesh\",\"isbn\":\"isbn5\",\"dateDePublication\":\"Oct 28, 2016 12:00:00 AM\",\"nbPages\":52,\"prix\":9.99,\"langue\":\"Français\",\"langueOrigine\":\"Français\",\"nomCouverture\":\"https://ebook.nolim.fr/covers/original/9782213/9782213704111.jpg\",\"resume\":\"D\u0027après une nouvelle histoire originale de J.K. Rowling, John Tiffany et Jack Thorne, la nouvelle pièce de théâtre de Jack Thorne, Harry Potter et l\u0027Enfant Maudit est la huitième histoire de la saga Harry Potter et la première histoire de Harry Potter officiellement destinée à  la scène. La première mondiale de la pièce a eu lieu à  Londres dans un théâtre du West End le 30 juillet 2016. Être Harry Potter n\u0027a jamais été facile et ne l\u0027est pas davantage depuis qu\u0027il est un employé surmené du Ministère de la Magie, marié et pére de trois enfants. Tandis que Harry se débat avec un passé qui refuse de le laisser en paix, son plus jeune fils, Albus, doit lutter avec le poids d\u0027un héritage familial dont il n\u0027a jamais voulu. Le destin vient fusionner passé et présent. Père et fils se retrouvent face à une dure vérité : parfois, les ténebres surviennent des endroits les plus inattendus.\",\"promotion\":{\"id\":2853,\"tauxReduc\":20,\"dateDebut\":\"Dec 8, 2016 12:00:00 AM\",\"dateFin\":\"Dec 8, 3917 12:00:00 AM\"},\"lesAuteurs\":[{\"id\":2754,\"nom\":\"P.D. James\",\"prenom\":\"\"}],\"lesAvis\":[],\"genre\":{\"id\":2652,\"nom\":\"Roman\"},\"editeur\":{\"id\":2702,\"nom\":\"Fayard\"}},{\"id\":2807,\"titre\":\"Dybouk\",\"isbn\":\"isbn7\",\"dateDePublication\":\"Oct 28, 2016 12:00:00 AM\",\"nbPages\":74,\"prix\":1.01,\"langue\":\"Français\",\"langueOrigine\":\"Français\",\"nomCouverture\":\"https://ebook.nolim.fr/covers/original/9791034/9791034200573.jpg\",\"resume\":\"D\u0027après une nouvelle histoire originale de J.K. Rowling, John Tiffany et Jack Thorne, la nouvelle pièce de théâtre de Jack Thorne, Harry Potter et l\u0027Enfant Maudit est la huitième histoire de la saga Harry Potter et la première histoire de Harry Potter officiellement destinée à  la scène. La première mondiale de la pièce a eu lieu à  Londres dans un théâtre du West End le 30 juillet 2016. Être Harry Potter n\u0027a jamais été facile et ne l\u0027est pas davantage depuis qu\u0027il est un employé surmené du Ministère de la Magie, marié et pére de trois enfants. Tandis que Harry se débat avec un passé qui refuse de le laisser en paix, son plus jeune fils, Albus, doit lutter avec le poids d\u0027un héritage familial dont il n\u0027a jamais voulu. Le destin vient fusionner passé et présent. Père et fils se retrouvent face à une dure vérité : parfois, les ténebres surviennent des endroits les plus inattendus.\",\"promotion\":{\"id\":2854,\"tauxReduc\":15,\"dateDebut\":\"Dec 8, 2016 12:00:00 AM\",\"dateFin\":\"Dec 8, 3917 12:00:00 AM\"},\"lesAuteurs\":[{\"id\":2755,\"nom\":\"Patrice Quélard\",\"prenom\":\"\"}],\"lesAvis\":[],\"genre\":{\"id\":2652,\"nom\":\"Roman\"},\"editeur\":{\"id\":2703,\"nom\":\"Nutty Sheep \"}},{\"id\":2814,\"titre\":\"Les aventures de Jim Cusack - Un violon ne meurt jamais\",\"isbn\":\"isbn14\",\"dateDePublication\":\"Dec 27, 2013 12:00:00 AM\",\"nbPages\":35,\"prix\":7.99,\"langue\":\"Français\",\"langueOrigine\":\"Anglais\",\"nomCouverture\":\"https://ebook.nolim.fr/covers/original/9782814/9782814507654.jpg\",\"resume\":\"D\u0027après une nouvelle histoire originale de J.K. Rowling, John Tiffany et Jack Thorne, la nouvelle pièce de théâtre de Jack Thorne, Harry Potter et l\u0027Enfant Maudit est la huitième histoire de la saga Harry Potter et la première histoire de Harry Potter officiellement destinée à  la scène. La première mondiale de la pièce a eu lieu à  Londres dans un théâtre du West End le 30 juillet 2016. Être Harry Potter n\u0027a jamais été facile et ne l\u0027est pas davantage depuis qu\u0027il est un employé surmené du Ministère de la Magie, marié et pére de trois enfants. Tandis que Harry se débat avec un passé qui refuse de le laisser en paix, son plus jeune fils, Albus, doit lutter avec le poids d\u0027un héritage familial dont il n\u0027a jamais voulu. Le destin vient fusionner passé et présent. Père et fils se retrouvent face à une dure vérité : parfois, les ténebres surviennent des endroits les plus inattendus.\",\"promotion\":{\"id\":2855,\"tauxReduc\":5,\"dateDebut\":\"Dec 8, 2016 12:00:00 AM\",\"dateFin\":\"Dec 8, 3917 12:00:00 AM\"},\"lesAuteurs\":[{\"id\":2758,\"nom\":\"L.C Went\",\"prenom\":\"\"}],\"lesAvis\":[],\"genre\":{\"id\":2653,\"nom\":\"Bande dessinée\"},\"editeur\":{\"id\":2706,\"nom\":\"publie.net\"}},{\"id\":2816,\"titre\":\"Des Rails et Dérives\",\"isbn\":\"isbn16\",\"dateDePublication\":\"Dec 2, 2012 12:00:00 AM\",\"nbPages\":85,\"prix\":2.99,\"langue\":\"Français\",\"langueOrigine\":\"Français\",\"nomCouverture\":\"https://ebook.nolim.fr/covers/original/9791090/9791090013094.jpg\",\"resume\":\"D\u0027après une nouvelle histoire originale de J.K. Rowling, John Tiffany et Jack Thorne, la nouvelle pièce de théâtre de Jack Thorne, Harry Potter et l\u0027Enfant Maudit est la huitième histoire de la saga Harry Potter et la première histoire de Harry Potter officiellement destinée à  la scène. La première mondiale de la pièce a eu lieu à  Londres dans un théâtre du West End le 30 juillet 2016. Être Harry Potter n\u0027a jamais été facile et ne l\u0027est pas davantage depuis qu\u0027il est un employé surmené du Ministère de la Magie, marié et pére de trois enfants. Tandis que Harry se débat avec un passé qui refuse de le laisser en paix, son plus jeune fils, Albus, doit lutter avec le poids d\u0027un héritage familial dont il n\u0027a jamais voulu. Le destin vient fusionner passé et présent. Père et fils se retrouvent face à une dure vérité : parfois, les ténebres surviennent des endroits les plus inattendus.\",\"promotion\":{\"id\":2856,\"tauxReduc\":4,\"dateDebut\":\"Dec 8, 2016 12:00:00 AM\",\"dateFin\":\"Dec 8, 3917 12:00:00 AM\"},\"lesAuteurs\":[{\"id\":2760,\"nom\":\"Jean Pierre Banville\",\"prenom\":\"\"}],\"lesAvis\":[],\"genre\":{\"id\":2653,\"nom\":\"Bande dessinée\"},\"editeur\":{\"id\":2708,\"nom\":\"Ibex Books \"}},{\"id\":2818,\"titre\":\",\"isbn\":\"isbn18\",\"dateDePublication\":\"Jun 6, 2013 12:00:00 AM\",\"nbPages\":41,\"prix\":5.99,\"langue\":\"Français\",\"langueOrigine\":\"Français\",\"nomCouverture\":\"https://ebook.nolim.fr/covers/original/9782130/9782130612261.jpg\",\"resume\":\"D\u0027après une nouvelle histoire originale de J.K. Rowling, John Tiffany et Jack Thorne, la nouvelle pièce de théâtre de Jack Thorne, Harry Potter et l\u0027Enfant Maudit est la huitième histoire de la saga Harry Potter et la première histoire de Harry Potter officiellement destinée à  la scène. La première mondiale de la pièce a eu lieu à  Londres dans un théâtre du West End le 30 juillet 2016. Être Harry Potter n\u0027a jamais été facile et ne l\u0027est pas davantage depuis qu\u0027il est un employé surmené du Ministère de la Magie, marié et pére de trois enfants. Tandis que Harry se débat avec un passé qui refuse de le laisser en paix, son plus jeune fils, Albus, doit lutter avec le poids d\u0027un héritage familial dont il n\u0027a jamais voulu. Le destin vient fusionner passé et présent. Père et fils se retrouvent face à une dure vérité : parfois, les ténebres surviennent des endroits les plus inattendus.\",\"promotion\":{\"id\":2857,\"tauxReduc\":10,\"dateDebut\":\"Dec 8, 2016 12:00:00 AM\",\"dateFin\":\"Dec 8, 3917 12:00:00 AM\"},\"lesAuteurs\":[{\"id\":2762,\"nom\":\"Annie Baron-Carvais\",\"prenom\":\"\"}],\"lesAvis\":[],\"genre\":{\"id\":2654,\"nom\":\"Manga\"},\"editeur\":{\"id\":2710,\"nom\":\"Presses Universitaires de France \"}},{\"id\":2819,\"titre\":\"Itinéraire d\u0027un enfant âgé\",\"isbn\":\"isbn19\",\"dateDePublication\":\"Apr 15, 2013 12:00:00 AM\",\"nbPages\":25,\"prix\":1.99,\"langue\":\"Français\",\"langueOrigine\":\"Français\",\"nomCouverture\":\"https://ebook.nolim.fr/covers/original/9782354/9782354854096.jpg\",\"resume\":\"D\u0027après une nouvelle histoire originale de J.K. Rowling, John Tiffany et Jack Thorne, la nouvelle pièce de théâtre de Jack Thorne, Harry Potter et l\u0027Enfant Maudit est la huitième histoire de la saga Harry Potter et la première histoire de Harry Potter officiellement destinée à  la scène. La première mondiale de la pièce a eu lieu à  Londres dans un théâtre du West End le 30 juillet 2016. Être Harry Potter n\u0027a jamais été facile et ne l\u0027est pas davantage depuis qu\u0027il est un employé surmené du Ministère de la Magie, marié et pére de trois enfants. Tandis que Harry se débat avec un passé qui refuse de le laisser en paix, son plus jeune fils, Albus, doit lutter avec le poids d\u0027un héritage familial dont il n\u0027a jamais voulu. Le destin vient fusionner passé et présent. Père et fils se retrouvent face à une dure vérité : parfois, les ténebres surviennent des endroits les plus inattendus.\",\"promotion\":{\"id\":2858,\"tauxReduc\":5,\"dateDebut\":\"Dec 8, 2016 12:00:00 AM\",\"dateFin\":\"Dec 8, 3917 12:00:00 AM\"},\"lesAuteurs\":[{\"id\":2763,\"nom\":\"Stéphane Scotto Di Rinaldi\",\"prenom\":\"\"}],\"lesAvis\":[],\"genre\":{\"id\":2654,\"nom\":\"Manga\"},\"editeur\":{\"id\":2711,\"nom\":\"Editions Jets d\u0027Encre\"}},{\"id\":2826,\"titre\":\"Edith Piaf\",\"isbn\":\"isbn26\",\"dateDePublication\":\"Jun 4, 2014 12:00:00 AM\",\"nbPages\":900,\"prix\":12.99,\"langue\":\"Français\",\"langueOrigine\":\"Français\",\"nomCouverture\":\"https://ebook.nolim.fr/covers/original/9782072/9782072477126.jpg\",\"resume\":\"D\u0027après une nouvelle histoire originale de J.K. Rowling, John Tiffany et Jack Thorne, la nouvelle pièce de théâtre de Jack Thorne, Harry Potter et l\u0027Enfant Maudit est la huitième histoire de la saga Harry Potter et la première histoire de Harry Potter officiellement destinée à  la scène. La première mondiale de la pièce a eu lieu à  Londres dans un théâtre du West End le 30 juillet 2016. Être Harry Potter n\u0027a jamais été facile et ne l\u0027est pas davantage depuis qu\u0027il est un employé surmené du Ministère de la Magie, marié et pére de trois enfants. Tandis que Harry se débat avec un passé qui refuse de le laisser en paix, son plus jeune fils, Albus, doit lutter avec le poids d\u0027un héritage familial dont il n\u0027a jamais voulu. Le destin vient fusionner passé et présent. Père et fils se retrouvent face à une dure vérité : parfois, les ténebres surviennent des endroits les plus inattendus.\",\"promotion\":{\"id\":2859,\"tauxReduc\":50,\"dateDebut\":\"Dec 8, 2016 12:00:00 AM\",\"dateFin\":\"Dec 8, 3917 12:00:00 AM\"},\"lesAuteurs\":[{\"id\":2769,\"nom\":\"Albert Bensoussan\",\"prenom\":\"\"}],\"lesAvis\":[],\"genre\":{\"id\":2655,\"nom\":\"Biographie\"},\"editeur\":{\"id\":2713,\"nom\":\"Editions Gallimard \"}},{\"id\":2828,\"titre\":\"Walking Dead, Tome 2 : Cette vie derrière nous\",\"isbn\":\"isbn28\",\"dateDePublication\":\"Jun 6, 2007 12:00:00 AM\",\"nbPages\":145,\"prix\":11.98,\"langue\":\"Français\",\"langueOrigine\":\"Anglais\",\"nomCouverture\":\"https://images-na.ssl-images-amazon.com/images/I/71vDovFldzL.jpg\",\"resume\":\"D\u0027après une nouvelle histoire originale de J.K. Rowling, John Tiffany et Jack Thorne, la nouvelle pièce de théâtre de Jack Thorne, Harry Potter et l\u0027Enfant Maudit est la huitième histoire de la saga Harry Potter et la première histoire de Harry Potter officiellement destinée à  la scène. La première mondiale de la pièce a eu lieu à  Londres dans un théâtre du West End le 30 juillet 2016. Être Harry Potter n\u0027a jamais été facile et ne l\u0027est pas davantage depuis qu\u0027il est un employé surmené du Ministère de la Magie, marié et pére de trois enfants. Tandis que Harry se débat avec un passé qui refuse de le laisser en paix, son plus jeune fils, Albus, doit lutter avec le poids d\u0027un héritage familial dont il n\u0027a jamais voulu. Le destin vient fusionner passé et présent. Père et fils se retrouvent face à une dure vérité : parfois, les ténebres surviennent des endroits les plus inattendus.\",\"promotion\":{\"id\":2860,\"tauxReduc\":10,\"dateDebut\":\"Dec 8, 2016 12:00:00 AM\",\"dateFin\":\"Dec 8, 3917 12:00:00 AM\"},\"lesAuteurs\":[{\"id\":2770,\"nom\":\"Robert Kirkman\",\"prenom\":\"\"},{\"id\":2772,\"nom\":\"Charlie Adlard\",\"prenom\":\"\"}],\"lesAvis\":[],\"genre\":{\"id\":2656,\"nom\":\"Comics\"},\"editeur\":{\"id\":2714,\"nom\":\"Delcourt\"}},{\"id\":2829,\"titre\":\"Walking Dead, Tome 4 : Amour et mort\",\"isbn\":\"isbn29\",\"dateDePublication\":\"Feb 6, 2008 12:00:00 AM\",\"nbPages\":133,\"prix\":11.99,\"langue\":\"Français\",\"langueOrigine\":\"Anglais\",\"nomCouverture\":\"https://images-na.ssl-images-amazon.com/images/I/81Qf9n582mL.jpg\",\"resume\":\"D\u0027après une nouvelle histoire originale de J.K. Rowling, John Tiffany et Jack Thorne, la nouvelle pièce de théâtre de Jack Thorne, Harry Potter et l\u0027Enfant Maudit est la huitième histoire de la saga Harry Potter et la première histoire de Harry Potter officiellement destinée à  la scène. La première mondiale de la pièce a eu lieu à  Londres dans un théâtre du West End le 30 juillet 2016. Être Harry Potter n\u0027a jamais été facile et ne l\u0027est pas davantage depuis qu\u0027il est un employé surmené du Ministère de la Magie, marié et pére de trois enfants. Tandis que Harry se débat avec un passé qui refuse de le laisser en paix, son plus jeune fils, Albus, doit lutter avec le poids d\u0027un héritage familial dont il n\u0027a jamais voulu. Le destin vient fusionner passé et présent. Père et fils se retrouvent face à une dure vérité : parfois, les ténebres surviennent des endroits les plus inattendus.\",\"promotion\":{\"id\":2861,\"tauxReduc\":10,\"dateDebut\":\"Dec 8, 2016 12:00:00 AM\",\"dateFin\":\"Dec 8, 3917 12:00:00 AM\"},\"lesAuteurs\":[{\"id\":2770,\"nom\":\"Robert Kirkman\",\"prenom\":\"\"},{\"id\":2772,\"nom\":\"Charlie Adlard\",\"prenom\":\"\"}],\"lesAvis\":[],\"genre\":{\"id\":2656,\"nom\":\"Comics\"},\"editeur\":{\"id\":2714,\"nom\":\"Delcourt\"}}]");
	}

}