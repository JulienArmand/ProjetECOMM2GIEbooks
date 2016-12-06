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

public class GetTousServlet extends HttpServlet {

	private static final long serialVersionUID = -3401197051263972685L;
	
	@EJB()  
	private GestionAuteur beanAuteur; 
	@EJB()  
	private GestionAvis beanAvis;
	@EJB()  
	private GestionClient beanClient;
	@EJB()  
	private GestionCommande beanCommande;
	@EJB()  
	private GestionEditeur beanEditeur;
	@EJB()  
	private GestionGenre beanGenre;
	@EJB()  
	private GestionLivre beanLivre;
	@EJB()  
	private GestionPromotion beanPromotion; 
	@EJB()  
	private GestionVente beanVente;
		
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GsonBuilder gb = new GsonBuilder();
		Gson js = gb.excludeFieldsWithoutExposeAnnotation().create();

		String str = null;
		
		if (request.getParameter("action").equals("auteurs")) {
			List<Auteur> l = beanAuteur.getLesAuteurs();
			str = js.toJson(l);			
		} else if (request.getParameter("action").equals("avis")) {
			List<Avis> l = beanAvis.getLesAvis();
			str = js.toJson(l);			
		} else if (request.getParameter("action").equals("clients")) {
			List<Client> l = beanClient.getLesClients();
			str = js.toJson(l);			
		} else if (request.getParameter("action").equals("commandes")) {
			List<Commande> l = beanCommande.getLesCommandes();
			str = js.toJson(l);			
		} else if (request.getParameter("action").equals("editeurs")) {
			List<Editeur> l = beanEditeur.getLesEditeurs();
			str = js.toJson(l);			
		} else if (request.getParameter("action").equals("genre")) {
			List<Genre> l = beanGenre.getLesGenres();
			str = js.toJson(l);			
		} else if (request.getParameter("action").equals("livres")) {
			List<Livre> l = beanLivre.getLesLivres();
			str = js.toJson(l);			
		} else if (request.getParameter("action").equals("promotions")) {
			List<Promotion> l = beanPromotion.getLesPromotions();
			str = js.toJson(l);			
		} else if (request.getParameter("action").equals("ventes")) {
			List<Vente> l = beanVente.getLesVentes();
			str = js.toJson(l);			
		}
		
		response.setContentType("application/json");
		response.getWriter().println(str);
	}

}