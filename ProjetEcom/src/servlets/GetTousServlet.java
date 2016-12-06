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
import beans.GestionEditeur;
import beans.GestionGenre;
import beans.GestionPromotion;
import model.Auteur;
import model.Editeur;
import model.Genre;
import model.Promotion;

public class GetTousServlet extends HttpServlet {

	@EJB()   
	private GestionGenre beanGenre;
	@EJB()  
	private GestionAuteur beanAuteur; 
	@EJB()  
	private GestionEditeur beanEditeur; 
	@EJB()  
	private GestionPromotion beanPromotion; 
		
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GsonBuilder gb = new GsonBuilder();
		Gson js = gb.excludeFieldsWithoutExposeAnnotation().create();

		String str = null;
		if (request.getParameter("action").equals("genres")) {
			List<Genre> l = beanGenre.getTousLesGenres();
			str = js.toJson(l);
			
		} else if (request.getParameter("action").equals("auteurs")) {
			List<Auteur> l = beanAuteur.getLesAuteurs();
			str = js.toJson(l);
			
		} else if (request.getParameter("action").equals("editeurs")) {
			List<Editeur> l = beanEditeur.getLesEditeurs();
			str = js.toJson(l);
			
		} else if (request.getParameter("action").equals("promotions")) {
			List<Promotion> l = beanPromotion.getLesPromotions();
			str = js.toJson(l);
			
		}
		
		response.setContentType("application/json");
		response.getWriter().println(str);
	}

}