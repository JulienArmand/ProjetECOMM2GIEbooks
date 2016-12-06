package servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.GestionAuteur;
import beans.GestionEditeur;
import beans.GestionGenre;
import beans.GestionLivre;
import model.Auteur;
import model.Editeur;
import model.Genre;
import model.Livre;
import model.Promotion;

public class AjouterLivreServlet extends HttpServlet {
	
	private static final long serialVersionUID = -2746891563648869068L;
	
	@EJB()
	private GestionLivre beanLivre;
	@EJB()
	private GestionAuteur beanAuteur;
	@EJB()
	private GestionEditeur beanEditeur;
	@EJB()
	private GestionGenre beanGenre;

	private List<Auteur> listA;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GsonBuilder gb = new GsonBuilder();
		Gson js = gb.excludeFieldsWithoutExposeAnnotation().create();
		
		/* Récupération des champs du formulaire. */
		
		String titre = request.getParameter("titre");
		System.out.println(request.getParameter("editeur"));
		Editeur editeur = beanEditeur.getEditeur(Long.parseLong(request.getParameter("editeur")));
		Genre genre = beanGenre.getGenre(Long.parseLong(request.getParameter("genre")));
		String isbn = request.getParameter("isbn");
		int nbpage = Integer.parseInt(request.getParameter("nbPage"));
		float prix = Float.parseFloat(request.getParameter("prix"));
		String langue = request.getParameter("lang");
		String langueOriginale = request.getParameter("langOrig");
		String couverture = request.getParameter("couverture");
		String resume = request.getParameter("resume");
		
		Date datePub = null;
		String pattern = "dd/MM/yyyy";
		SimpleDateFormat format = new SimpleDateFormat(pattern);

		try {
			datePub = (Date) format.parse(request.getParameter("datePub"));
		} catch (ParseException e) {
			System.err.println("Mauvais format de date : " + request.getParameter("datePub"));
			datePub = (Date) Date.from(Instant.now());
		}
		
        
        /* Initialisation du résultat global de la validation. */
        Livre l = null;
		try {
			l = beanLivre.creerLivre(titre, null, editeur, genre, isbn, nbpage, prix, langue, langueOriginale, couverture, null, resume, datePub);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String str = js.toJson(l);
        
        response.setContentType("application/json");
        response.getWriter().write(str);
	}

}