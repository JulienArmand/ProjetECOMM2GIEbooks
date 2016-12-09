package servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.GestionClient;
import model.Client;

public class ModificationMotDePasseServlet extends HttpServlet {
	
	private static final long serialVersionUID = -6094862300127773927L;
	@EJB()
	private GestionClient myBean;

	public ModificationMotDePasseServlet(){
		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String motDePasseActuel = request.getParameter("ancienMotDePasse");
		String nouveauMotDePasse = request.getParameter("nouveauMotDePasse");
		String confirmationNouveauMotDePasse = request.getParameter("confirmationNouveauMotDePasse");
		String pseudo = request.getParameter("pseudo");
		String erreur = "InitError";
		GsonBuilder gb = new GsonBuilder();
		Gson js = gb.excludeFieldsWithoutExposeAnnotation().create();
		
		if(motDePasseActuel.trim().length() == 0 || nouveauMotDePasse.trim().length() == 0 || confirmationNouveauMotDePasse.trim().length() == 0){
			erreur = "champVide";
		}
		else{
			Client c = myBean.getClientFromPseudo(pseudo);
			if(c.getMotDePasse().equals(motDePasseActuel)){
				if(nouveauMotDePasse.equals(confirmationNouveauMotDePasse)){
					//Modification du mot de passe et redirection vers la page d'acceuil avec message de confirmation.
					myBean.updateClientMotDePasse(c, nouveauMotDePasse);
				}
				else{
					//Les deux nouveaux mots de passe ne sont pas identiques. Redirection vers la page de modification et message d'erreur.
					erreur = "motsDePasseDifferents";
				}
			}
			else{
				//Mot de passe actuel incorrect. Redirection vers la page de modification et message d'erreur.
				erreur = "mauvaisMotDePasse";
			}
		}
		String str = js.toJson(erreur);
		response.setContentType("application/json");
		response.getWriter().write(str);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
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
		*/
		
	}

}
