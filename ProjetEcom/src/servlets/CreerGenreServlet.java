package servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.GestionGenre;

public class CreerGenreServlet extends HttpServlet {

	private static final long	serialVersionUID	= 268367471001606128L;
	@EJB()
	GestionGenre myBean;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("GET");

		response.sendRedirect("admin.html");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("POST");
		
		if (request.getParameter("action").equals("creer") && !request.getParameter("Nom").equals("")) {
			myBean.creerGenre(request.getParameter("Nom"));
		}
		else if(request.getParameter("action").equals("modif") && !request.getParameter("Nom").equals("") && !request.getParameter("id").equals("")){
			myBean.modifierGenre(Long.parseLong(request.getParameter("id")), request.getParameter("Nom") );
		}
		response.sendRedirect("admin.html");
	}
}
