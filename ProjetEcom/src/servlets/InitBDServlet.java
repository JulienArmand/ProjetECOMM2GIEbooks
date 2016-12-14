package servlets;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.InitBean;
 
/**
 * Servlet permettant l'initialisation de la BD avec un jeu de données restreint
 * @author ochiers
 *
 */
public class InitBDServlet extends HttpServlet {

	private static final long serialVersionUID = -1205140827058517536L;
	
	@EJB()  
	private InitBean myBean; 
	
 
	/** 
	 * {@inheritDoc}
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		initBD();
		response.getWriter().println("BD initialisée");

	}
	
	/** 
	 * {@inheritDoc}
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		initBD();
		response.getWriter().println("BD initialisée");
	}

	private void initBD() {

		Logger logger = Logger.getAnonymousLogger();
		
		try {
			myBean.initBDFromCSV();
		} catch (IOException | URISyntaxException e) {
			logger.log(Level.FINE, "an exception was thrown", e);
		}		
	}
}
