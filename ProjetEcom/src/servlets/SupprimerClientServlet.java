package servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.GestionClient;

/**
 * @author ochiers
 * Servlet permettant de desincrire un client
 */
public class SupprimerClientServlet extends HttpServlet {

	private static final long serialVersionUID = 268367471001606128L;
	
	@EJB()
	private GestionClient beanClient; 
		
	/** 
	 * {@inheritDoc}
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		beanClient.desinscriptionClient(beanClient.getClient(Long.parseLong(request.getParameter("idClient"))));

	}	
}