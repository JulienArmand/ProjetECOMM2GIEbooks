package servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.GestionClient;
import model.Client;
import tools.EnvoiMail;

/**
 * @author ochiers
 * Servlet d'envoi de mail de confirmation à l'inscription
 */
public class ConfirmationInscriptionServlet extends HttpServlet {

	private static final long serialVersionUID = -5577148761542306852L;
	@EJB()
	private GestionClient clientBean;
	
	/** 
	 * {@inheritDoc}
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String pseudo = request.getParameter("id");
		
		Client client = clientBean.getClientFromPseudo(pseudo);
		
		StringBuilder strBuild = new StringBuilder();
		strBuild.append("Confirmation inscription\n");
		strBuild.append("Bonjour, " + client.getPseudo() +"\n");
		strBuild.append("Vous vous êtes inscrit sur notre site et nous vous en remercions.\n");
				
		strBuild.append("\nNous vous remercions de votre confiance et bonne lecture.\nA très bientot sur notre site ! \n L'équipe FuturaBooks.");
		
		EnvoiMail.envoyerEmail(client.getEmail(), "Confirmation inscription", strBuild.toString());
	}

	
}
