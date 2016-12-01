package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.ConnexionClientBean;

public class ConnexionClientServlet extends HttpServlet {
	

	private static final long serialVersionUID = 5024415012647003429L;
	
	@EJB()
	private ConnexionClientBean myBean;
	
	public ConnexionClientServlet() {
		// TODO Auto-generated constructor stub
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(checkPseudoClientExiste(request.getParameter("pseudo"))){
			if(checkMotDePasseCorrect(request.getParameter("pseudo"), request.getParameter("motDePasse"))){
				//Pseudo existe et mot de passe correct -> valider la connexion
				Cookie login = new Cookie("login", request.getParameter("pseudo"));
				response.addCookie(login);
				
				
			}
			else{
				//Mot de passe non correct
				Cookie erreur = new Cookie("erreur", "true");
				response.addCookie(erreur);

			}
		}
		else{
			//Pseudo n'existe pas
			Cookie erreur = new Cookie("erreur", "true");
			response.addCookie(erreur);
		}
		//Redirect to main page
		RequestDispatcher dispatcher = request.getRequestDispatcher("");
		dispatcher.forward(request,response);
	}
	
	
	private boolean checkPseudoClientExiste(String pseudo){
		boolean result = myBean.checkPseudoExiste(pseudo);
		return result;
	}
	
	private boolean checkMotDePasseCorrect(String pseudo, String motDePasse){
		return myBean.checkMotDePasseCorrect(pseudo, motDePasse);
	}
	
}