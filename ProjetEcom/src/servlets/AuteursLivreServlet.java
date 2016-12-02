package servlets;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import beans.GestionLivre;
import model.Livre;

public class AuteursLivreServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7589085599833042269L;
	@EJB()  //ou @EJB si nom par défaut 
	private GestionLivre myBean; 
	
	public AuteursLivreServlet() {
		// TODO Auto-generated constructor stub
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GsonBuilder gb = new GsonBuilder();
		Gson js = gb.excludeFieldsWithoutExposeAnnotation().create();

		Livre l = myBean.getLivreAvecId(Integer.parseInt(request.getParameter("id")));
		String str = js.toJson(l.getLesAuteurs());
		
		response.setContentType("application/json");
		response.getWriter().println(str);
	}

}