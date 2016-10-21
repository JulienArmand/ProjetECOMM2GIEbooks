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

import beans.GestionPanier;
import beans.InscriptionClientBean;
import model.Client;
import model.Livre;

public class GestionPanierServlet extends HttpServlet {

	private static final long serialVersionUID = 268367471001606128L;
	
	@EJB()  //ou @EJB si nom par défaut 
	private GestionPanier myBean; 
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		GsonBuilder gb = new GsonBuilder();
		Gson js = gb.excludeFieldsWithoutExposeAnnotation().create();

		List<Livre> l = myBean.getLesLivres();
		String str = js.toJson(l);
		
		float prix = myBean.getPrix();
		str += js.toJson(prix);
		response.setContentType("application/json");
		response.getWriter().println(str);		

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long id = Long.parseLong(request.getParameter("idLivre"));
	}

}
