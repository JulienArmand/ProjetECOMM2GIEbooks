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

public class ModificationProfileServlet extends HttpServlet {

	private static final long serialVersionUID = 32260626432449654L;
	
	@EJB()
	private GestionClient myBean;

	public ModificationProfileServlet(){
		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pseudo = request.getParameter("pseudo");
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String email = request.getParameter("email");
		Cookie[] cookies = request.getCookies();
		GestionCookies g = new GestionCookies();
		String cookiePseudo = g.getCookieByName(cookies, "login").getValue();
		System.out.println(cookiePseudo);
		Client c = myBean.getClientFromPseudo(cookiePseudo);
		if(!pseudo.equals(c.getPseudo())){
			System.out.println("Pseudo modifié.");
			myBean.updateClientPseudo(c, pseudo);
			int nbCookies = cookies.length;
			for(int i = 0; i < nbCookies; i++){
				cookies[0].setMaxAge(0);
			}
			//Réinitialisation des cookies login et idClient
			Cookie login = new Cookie("login", request.getParameter("pseudo"));
			response.addCookie(login);
			Cookie idClient = new Cookie("idClient", String.valueOf(c.getId()));
			response.addCookie(idClient);
		}
		if(!nom.equals(c.getNom())){
			System.out.println("Nom modifié.");
			myBean.updateClientNom(c, nom);
		}
		if(!prenom.equals(c.getPrenom())){
			System.out.println("Prénom modifié.");
			myBean.updateClientPrenom(c, prenom);
		}
		if(!email.equals(c.getEmail())){
			System.out.println("Email modifié.");
			myBean.updateClientEmail(c, email);
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("");
		dispatcher.forward(request,response);
	}
	
}
