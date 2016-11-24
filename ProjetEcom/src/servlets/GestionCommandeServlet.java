package servlets;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.GestionClient;
import beans.GestionCommande;
import beans.InscriptionClientBean;
import model.Client;
import model.Commande;
import model.Livre;
import model.Vente;

public class GestionCommandeServlet extends HttpServlet {

	private static final long	serialVersionUID	= 268367471001606128L;

	@EJB() // ou @EJB si nom par défaut
	private GestionCommande		myBean;

	@EJB() // ou @EJB si nom par défaut
	private GestionClient		clientBean;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GsonBuilder gb = new GsonBuilder();
		Gson js = gb.excludeFieldsWithoutExposeAnnotation().create();

		String str = null;

		if (request.getParameter("action").equals("commande")) {
			Commande c = myBean.getCommande(Long.parseLong(request.getParameter("idCommande")));
			str = js.toJson(c);
		} else if (request.getParameter("action").equals("commandeClient")) {

			List<Commande> l = myBean.getCommandeClient(clientBean.getClient(Long.parseLong(request.getParameter("idClient"))));
			str = js.toJson(l);
		}
		response.setContentType("application/json");
		response.getWriter().println(str);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Creer commande
		Commande c = myBean.creerCommande(new Date(), Float.parseFloat(request.getParameter("prixTotal")), 
											clientBean.getClient(Long.parseLong(request.getParameter("idClient"))), );
		// Creer ventes
		Collection<Vente> lesVentes = null;
		// Ajouter ventes
		myBean.setVentesCommande(c.getId(), lesVentes);

	}
}