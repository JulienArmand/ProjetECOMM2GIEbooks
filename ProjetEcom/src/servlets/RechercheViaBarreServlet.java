package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.CoupleLivreVente;
import beans.GestionLivre;
import beans.InitBean;
import model.Livre;

public class RechercheViaBarreServlet extends HttpServlet {

	@EJB()  //ou @EJB si nom par défaut 
	private GestionLivre myBean; 
	
	public RechercheViaBarreServlet() {
		// TODO Auto-generated constructor stub
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("RECHERCHEVIABARRE");
		GsonBuilder gb = new GsonBuilder();
		Gson js = gb.excludeFieldsWithoutExposeAnnotation().create();

		
		List<CoupleLivreVente> res  = new LinkedList<>();
		try {
			//res = myBean.recherche(request.getParameter("req"), 0, 500, "Fantastique", 0);
			System.out.println("avis : " + request.getParameter("avisMin"));
			res = myBean.recherche(request.getParameter("req"), Integer.parseInt(request.getParameter("pmin")), Integer.parseInt(request.getParameter("pmax")), request.getParameter("genre").replaceAll("\\+", " "), Integer.parseInt(request.getParameter("avisMin")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String str = js.toJson(res);
		
		response.setContentType("application/json");
		response.getWriter().println(str);
	}

}
