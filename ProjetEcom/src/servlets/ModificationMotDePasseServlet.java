package servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Tools.GestionCookies;

import javax.servlet.http.Cookie;
import beans.GestionClient;
import model.Client;

public class ModificationMotDePasseServlet extends HttpServlet {
	
	private static final long serialVersionUID = -6094862300127773927L;
	@EJB()
	private GestionClient myBean;

	public ModificationMotDePasseServlet(){
		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String motDePasseActuel = request.getParameter("ancienMotDePasse");
		Cookie[] cookies = request.getCookies();
		GestionCookies g = new GestionCookies();
		String cookiePseudo = g.getCookieByName(cookies, "login").getValue();
		Client c = myBean.getClientFromPseudo(cookiePseudo);
		if(c.getMotDePasse().equals(motDePasseActuel)){
			String nouveauMotDePasse = request.getParameter("nouveauMotDePasse");
			String confirmationNouveauMotDePasse = request.getParameter("confirmationNouveauMotDePasse");
			if(nouveauMotDePasse.equals(confirmationNouveauMotDePasse)){
				//Modification du mot de passe et redirection vers la page d'acceuil avec message de confirmation.
				myBean.updateClientMotDePasse(c, nouveauMotDePasse);
				RequestDispatcher dispatcher = request.getRequestDispatcher("");
				dispatcher.forward(request,response);
				
			}
			else{
				//Les deux nouveaux mots de passe ne sont pas identiques. Redirection vers la page de modification et message d'erreur.
				RequestDispatcher dispatcher = request.getRequestDispatcher("");
				dispatcher.forward(request,response);
			}
		}
		else{
			//Mot de passe actuel incorrect. Redirection vers la page de modification et message d'erreur.
			RequestDispatcher dispatcher = request.getRequestDispatcher("");
			dispatcher.forward(request,response);
		}
		
	}

}
