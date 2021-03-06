package servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.http.Cookie;
import beans.GestionClient;
import model.Client;
import tools.ChiffrageCookies;
import tools.GestionCookies;

/**
 * @author ochiers
 * Servlet de gestion de la modification de profil
 */
public class ModificationProfileServlet extends HttpServlet {

	private static final long serialVersionUID = 32260626432449654L;
	
	@EJB()
	private GestionClient myBean;
	
	/** 
	 * {@inheritDoc}
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pseudo = request.getParameter("pseudo");
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String email = request.getParameter("email");
		String erreur = "InitError";
		GsonBuilder gb = new GsonBuilder();
		Gson js = gb.excludeFieldsWithoutExposeAnnotation().create();
		
		//Check si l'un des champs est vide
		if(pseudo.trim().length() == 0 || nom.trim().length() == 0|| prenom.trim().length() == 0 || email.trim().length() == 0){
			erreur = "champVide";
		}
		else{
			boolean pseudoExiste = false;
			Cookie[] cookies = request.getCookies();
			GestionCookies g = new GestionCookies();
			String cookiePseudo = ChiffrageCookies.dechiffreString(g.getCookieByName(cookies, "login").getValue());
			Client c = myBean.getClientFromPseudo(cookiePseudo);

			//Mise à jour du pseudo
			if(!pseudo.equals(c.getPseudo())){
				Client clientPseudo = myBean.getClientFromPseudo(pseudo);
				if(clientPseudo==null){
					myBean.updateClientPseudo(c, pseudo);
					Cookie login = new Cookie("login", ChiffrageCookies.chiffreString(request.getParameter("pseudo")));
					response.addCookie(login);
					Cookie idClient = new Cookie("idClient", String.valueOf(c.getId()));
					response.addCookie(idClient);
				}else{
					erreur = "pseudoExiste";
					pseudoExiste = true;
				}
			}
			
			//Mise à jour du nom
			if(!nom.equals(c.getNom())){
				myBean.updateClientNom(c, nom);
			}
			
			//Mise à jour du prénom
			if(!prenom.equals(c.getPrenom())){
				myBean.updateClientPrenom(c, prenom);
			}
			
			//Mise à jour de l'adresse mail
			if(!email.equals(c.getEmail())){
				Client clientEmail = myBean.getClientFromEmail(email);
				if(clientEmail==null){
					myBean.updateClientEmail(c, email);
				}else{
					if(!pseudoExiste){
						erreur = "emailExiste";
					}
					else{
						erreur = "doubleExiste";
					}		
				}
			}
		}
		String str = js.toJson(erreur);
		response.setContentType("application/json");
		response.getWriter().write(str);
	}
		
}
