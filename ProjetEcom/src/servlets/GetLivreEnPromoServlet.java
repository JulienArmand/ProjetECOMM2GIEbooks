package servlets;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.GestionLivre;
import model.Livre;

/**
 * @author ochiers
 * Servlet retournant les livres en promotions
 */
public class GetLivreEnPromoServlet extends HttpServlet {

	private static final long serialVersionUID = -5204392351534805766L;
	
	@EJB()  
	private GestionLivre myBean; 
	
	/** 
	 * {@inheritDoc}
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GsonBuilder gb = new GsonBuilder();
		Gson js = gb.excludeFieldsWithoutExposeAnnotation().create();

		List<Livre> l = myBean.getLesLivresEnPromotion();
		String str = js.toJson(l);
		
		response.setContentType("application/json");
		response.getWriter().println(str);
	}

}
