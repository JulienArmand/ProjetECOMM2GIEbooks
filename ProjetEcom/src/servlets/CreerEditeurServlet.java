package servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.GestionEditeur;

public class CreerEditeurServlet extends HttpServlet {

	private static final long	serialVersionUID	= 268367471001606128L;
	@EJB()
	private GestionEditeur myBean;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getParameter("action").equals("creer") && !request.getParameter("nom").equals("")) {
			myBean.creerEditeur(request.getParameter("Nom"));
		}
		else if(request.getParameter("action").equals("modif") && !request.getParameter("nom").equals("") && !request.getParameter("id").equals("")){
			myBean.modifierEditeur(Long.parseLong(request.getParameter("id")), request.getParameter("nom") );
		}

		response.sendRedirect("admin.html");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getParameter("action").equals("creer") && !request.getParameter("Nom").equals("")) {
			myBean.creerEditeur(request.getParameter("Nom"));
		}
		else if(request.getParameter("action").equals("modif") && !request.getParameter("Nom").equals("") && !request.getParameter("id").equals("")){
			myBean.modifierEditeur(Long.parseLong(request.getParameter("id")), request.getParameter("Nom") );
		}
		response.sendRedirect("admin.html");
	}
}
