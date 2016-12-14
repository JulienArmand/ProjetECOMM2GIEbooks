package servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.GestionEditeur;

/**
 * @author ochiers
 * Servlet de création d'un editeur
 */
public class CreerEditeurServlet extends HttpServlet {

	private static final long	serialVersionUID	= 268367471001606128L;
	@EJB()
	private GestionEditeur myBean;
	
	/** 
	 * {@inheritDoc}
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String creer = "creer";
		String modif = "modif";
		String empty = "";
		String action = request.getParameter("action");
		String nom = request.getParameter("nom");
		
		if (action.equals(creer) && !nom.equals(empty)) {
			myBean.creerEditeur(request.getParameter("Nom"));
		}
		else if(action.equals(modif) && !nom.equals(empty) && !request.getParameter("id").equals(empty)){
			myBean.modifierEditeur(Long.parseLong(request.getParameter("id")), nom );
		}

		response.sendRedirect("admin.html");
	}

}
