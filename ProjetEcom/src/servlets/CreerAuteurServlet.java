package servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.GestionAuteur;

public class CreerAuteurServlet extends HttpServlet {

	private static final long	serialVersionUID	= 268367471001606128L;
	@EJB()
	private GestionAuteur myBean;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String creer = "creer";
		String modif = "modif";
		String empty = "";
		String action = request.getParameter("action");
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		if (action.equals(creer) && !nom.equals(empty) && !prenom.equals(empty)) {
			myBean.creerAuteur(prenom, nom);
		}
		else if(action.equals(modif)){
			myBean.modifierAuteur(Long.parseLong(request.getParameter("id")), prenom, nom);
		}

		response.sendRedirect("admin.html");
	}

}
