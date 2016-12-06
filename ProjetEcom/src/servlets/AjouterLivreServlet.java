package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.GestionAvis;
import beans.GestionClient;
import beans.GestionCommande;
import beans.GestionLivre;
import beans.InscriptionClientBean;
import model.Client;
import model.Livre;

public class AjouterLivreServlet extends HttpServlet {
	
	private static final long serialVersionUID = -2746891563648869068L;
	
	@EJB()
	private GestionLivre beanLivre;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GsonBuilder gb = new GsonBuilder();
		Gson js = gb.excludeFieldsWithoutExposeAnnotation().create();
		
		/* Récupération des champs du formulaire. */
		String titre = request.getParameter("titre");
		String auteurs = request.getParameter("auteurs");
		String editeurs = request.getParameter("editeurs");
		String genre = request.getParameter("genre");
		String isbn = request.getParameter("isbn");
		int nbpage = Integer.parseInt(request.getParameter("nbpage"));
		float prix = Float.parseFloat(request.getParameter("prix"));
		String langue = request.getParameter("langue");
		String langueOriginale = request.getParameter("langueOriginale");
		String couverture = request.getParameter("couverture");
		
        
        /* Initialisation du résultat global de la validation. */
//        Livre l =Livre beanLivre.creerLivre(titre, a, e, g, isbn, nbpage, prix, langue, langueOriginale, couverture, promo, resume, datePub)
//        String str = js.toJson(l);
        
//        response.setContentType("application/json");
//        response.getWriter().write(str);
	}

}