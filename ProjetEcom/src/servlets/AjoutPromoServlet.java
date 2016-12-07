package servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.GestionEditeur;
import beans.GestionGenre;
import beans.GestionLivre;

public class AjoutPromoServlet extends HttpServlet {

	private static final long	serialVersionUID	= 268367471001606128L;
	@EJB()
	GestionLivre myBean;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("GET");
		
		Long id = Long.parseLong(request.getParameter("id"));
		int taux = Integer.parseInt(request.getParameter("Taux"));
		String dateD = request.getParameter("dateD");
		String dateF = request.getParameter("dateF");
		myBean.ajouterPromo(id, taux, dateD, dateF);

		response.sendRedirect("admin.html");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("POST");
		Long id = Long.parseLong(request.getParameter("id"));
		int taux = Integer.parseInt(request.getParameter("Taux"));
		String dateD = request.getParameter("dateD");
		String dateF = request.getParameter("dateF");
		myBean.ajouterPromo(id, taux, dateD, dateF);
		
		response.sendRedirect("admin.html");
	}
}
