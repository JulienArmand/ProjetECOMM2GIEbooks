package servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.GestionAvis;
import beans.GestionClient;
import beans.GestionLivre;
import model.Client;
import model.Livre;

public class AjouterCommentaireServlet extends HttpServlet {

	private static final long	serialVersionUID	= 268367471001606128L;

	@EJB()
	private GestionAvis			beanAvis;
	@EJB()
	private GestionClient		beanClient;
	@EJB()
	private GestionLivre		beanLivre;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GsonBuilder gb = new GsonBuilder();
		Gson js = gb.excludeFieldsWithoutExposeAnnotation().create();

		/* Récupération des champs du formulaire. */
		int note = Integer.parseInt(request.getParameter("note"));
		String commentaire = request.getParameter("commentaire");
		int idLivre = Integer.parseInt(request.getParameter("idLivre"));
		int idClient = Integer.parseInt(request.getParameter("idClient"));

		String erreur = "dejaCommente";
		String str;
		if (checkDejaCommente(idClient, idLivre)) {
			str = js.toJson(erreur);
		} else {
			Livre l = ajoutCommentaire(idClient, idLivre, note, commentaire);
			str = js.toJson(l);
		}
		response.setContentType("application/json");
		response.getWriter().write(str);
	}

	private Livre ajoutCommentaire(int idclient, int idLivre, int note, String commentaire) {
		Client c = beanClient.getClient(idclient);
		Livre l = beanLivre.getLivreAvecId(idLivre);
		Logger logger = Logger.getAnonymousLogger();
		try {
			beanAvis.creerAvis(l, c, note, commentaire);
		} catch (Exception e) {
			logger.log(Level.FINE, "an exception was thrown", e);
		}
		return l;
	}

	private boolean checkDejaCommente(int idClient, int idLivre) {
		Logger logger = Logger.getAnonymousLogger();
		boolean existe = false;
		try {
			existe = beanAvis.existe(idClient, idLivre);
		} catch (Exception e) {
			logger.log(Level.FINE, "an exception was thrown", e);
		}
		return existe;
	}
}