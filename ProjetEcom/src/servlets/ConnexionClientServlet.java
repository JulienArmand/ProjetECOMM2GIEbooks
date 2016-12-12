package servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.ConnexionClient;
import tools.ChiffrageCookies;

public class ConnexionClientServlet extends HttpServlet {
	

	private static final long serialVersionUID = 5024415012647003429L;
	
	@EJB()
	private ConnexionClient myBean;
		
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pseudo = request.getParameter("pseudo");
		if(checkPseudoClientExiste(pseudo)){
			if(checkMotDePasseCorrect(pseudo, request.getParameter("motDePasse"))){
				//Pseudo existe et mot de passe correct -> valider la connexion
				Cookie[] cookies = request.getCookies();
				//Supression des anciens cookies d'erreurs
				if(cookies != null){
					for(int j = 0; j < cookies.length; j++){
						Cookie deadCookie = new Cookie(cookies[0].getName(), null);
						deadCookie.setMaxAge(0);
						response.addCookie(deadCookie);
					}	
				}

				Cookie login = new Cookie("login", ChiffrageCookies.chiffreString(pseudo));
				Cookie idClient = new Cookie("idClient", ChiffrageCookies.chiffreString(String.valueOf(myBean.getIdClient(request.getParameter("pseudo")))));
				response.addCookie(login);
				response.addCookie(idClient);
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
	}
	
	
	private boolean checkPseudoClientExiste(String pseudo){
		return myBean.checkPseudoExiste(pseudo);
	}
	
	private boolean checkMotDePasseCorrect(String pseudo, String motDePasse){
		return myBean.checkMotDePasseCorrect(pseudo, motDePasse);
	}
	
}