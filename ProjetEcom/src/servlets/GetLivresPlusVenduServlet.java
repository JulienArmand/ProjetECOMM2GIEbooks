package servlets;

import java.io.IOException;
import java.util.Set;

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
 * Servlet retournant les livres les plus vendus
 */
public class GetLivresPlusVenduServlet extends HttpServlet {

	private static final long serialVersionUID = -7264551378039338453L;
	
	@EJB()
	private GestionLivre myBean; 
		
	/** 
	 * {@inheritDoc}
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GsonBuilder gb = new GsonBuilder();
		Gson js = gb.excludeFieldsWithoutExposeAnnotation().create();

		Set<Livre> l = myBean.getDixLivresLesPlusVendu();
		String str = js.toJson(l);
		
		response.setContentType("application/json");
		response.getWriter().println(str);
	}

}
