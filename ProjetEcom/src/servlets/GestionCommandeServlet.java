package servlets;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
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
import beans.GestionLivre;
import beans.GestionPaiement;
import beans.GestionVente;
import model.Client;
import model.Commande;
import model.Vente;

public class GestionCommandeServlet extends HttpServlet {

	private static final long	serialVersionUID	= 268367471001606128L;

	@EJB() // ou @EJB si nom par défaut
	private GestionCommande		commandeBean;

	@EJB() // ou @EJB si nom par défaut
	private GestionClient		clientBean;

	@EJB() // ou @EJB si nom par défaut
	private GestionPaiement		paiementBean;

	@EJB() // ou @EJB si nom par défaut
	private GestionVente		venteBean;

	@EJB() // ou @EJB si nom par défaut
	private GestionLivre		LivreBean;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GsonBuilder gb = new GsonBuilder();
		Gson js = gb.excludeFieldsWithoutExposeAnnotation().create();

		String str = null;

		if (request.getParameter("action").equals("commande")) {
			Commande c = commandeBean.getCommande(Long.parseLong(request.getParameter("idCommande")));
			str = js.toJson(c);
		} else if (request.getParameter("action").equals("commandeClient")) {

			List<Commande> l = commandeBean.getCommandeClient(clientBean.getClient(Long.parseLong(request.getParameter("idClient"))));
			str = js.toJson(l);
		}
		response.setContentType("application/json");
		response.getWriter().println(str);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Creer commande

		Client client = clientBean.getClient(Long.parseLong(request.getParameter("idClient")));

		Commande c = commandeBean.creerCommande(new Date(), Float.parseFloat(request.getParameter("prixTotal")), client, paiementBean.getMoyenPaiement(client, request.getParameter("type")));

		// Creer ventes
		Collection<Vente> lesVentes = new LinkedList<Vente>();
		for (int i = 1; i <= Integer.parseInt(request.getParameter("nbArticle")); i++) {
			lesVentes.add(venteBean.creerVente(LivreBean.getLivreAvecId(Long.parseLong(request.getParameter("idLivre" + i)))));
		}
		// Ajouter ventes
		commandeBean.setVentesCommande(c.getId(), lesVentes);

	}
}