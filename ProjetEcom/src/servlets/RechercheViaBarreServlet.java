package servlets;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.CoupleLivreVente;
import beans.GestionLivre;
import tools.Tools;

/**
 * @author ochiers
 * Servlet de gestion de la recherche
 */
public class RechercheViaBarreServlet extends HttpServlet {

	private static final long serialVersionUID = -3969291627640520522L;
	
	@EJB() 
	private GestionLivre myBean; 
	
	/** 
	 * {@inheritDoc}
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GsonBuilder gb = new GsonBuilder();
		Gson js = gb.excludeFieldsWithoutExposeAnnotation().create();

		Logger logger = Logger.getAnonymousLogger();
		List<CoupleLivreVente> res  = new LinkedList<>();
		try {
			res = myBean.recherche(request.getParameter("req"), Double.parseDouble(request.getParameter("pmin").replaceAll(",", ".")), Double.parseDouble(request.getParameter("pmax").replaceAll(",", ".")), Tools.normalisationString(request.getParameter("genre")), Integer.parseInt(request.getParameter("avisMin")));
		} catch (Exception e) {
			logger.log(Level.FINE, "an exception was thrown", e);
		}
		String str = js.toJson(res);
		
		response.setContentType("application/json");
		response.getWriter().println(str);
	}

}
