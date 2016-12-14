package servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.GestionLivre;

/**
 * Servlet permettant d'ajouter une promotion à la BD
 * @author ochiers
 *
 */
public class AjoutPromoServlet extends HttpServlet {

	private static final long	serialVersionUID	= 268367471001606128L;
	@EJB()
	private GestionLivre myBean;
	
	/** 
	 * {@inheritDoc}
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Long id = Long.parseLong(request.getParameter("id"));
		int taux = Integer.parseInt(request.getParameter("Taux"));
		String dateD = request.getParameter("dateD");
		String dateF = request.getParameter("dateF");
		myBean.ajouterPromo(id, taux, dateD, dateF);

		response.sendRedirect("admin.html");
	}

	/** 
	 * {@inheritDoc}
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Long id = Long.parseLong(request.getParameter("id"));
		int taux = Integer.parseInt(request.getParameter("Taux"));
		String dateD = request.getParameter("dateD");
		String dateF = request.getParameter("dateF");
		myBean.ajouterPromo(id, taux, dateD, dateF);
		
		response.sendRedirect("admin.html");
	}
}
