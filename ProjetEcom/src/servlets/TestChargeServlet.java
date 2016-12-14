package servlets;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.GestionLivre;
import model.Livre;

/**
 * Test de charge (retournant les livres en promotions)
 * @author ochiers
 *
 */
public class TestChargeServlet extends HttpServlet {

	private static final long serialVersionUID = -3401197851263972685L;
	
	@EJB()  
	private GestionLivre myBean; 
		
	/** 
	 * {@inheritDoc}
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Livre> l = myBean.getLesLivresEnPromotion();
		
	}

}