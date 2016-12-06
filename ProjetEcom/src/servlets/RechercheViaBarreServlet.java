package servlets;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Tools.Tools;
import beans.CoupleLivreVente;
import beans.GestionLivre;

public class RechercheViaBarreServlet extends HttpServlet {

	private static final long serialVersionUID = -3969291627640520522L;
	
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
			res = myBean.recherche(request.getParameter("req"), Integer.parseInt(request.getParameter("pmin")), Integer.parseInt(request.getParameter("pmax")), Tools.normalisationString(request.getParameter("genre")), Integer.parseInt(request.getParameter("avisMin")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String str = js.toJson(res);
		
		response.setContentType("application/json");
		response.getWriter().println(str);
	}

}
