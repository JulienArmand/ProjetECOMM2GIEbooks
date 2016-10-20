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

import beans.InitBean;
import model.Livre;

public class GetOneBookServlet extends HttpServlet {

	@EJB()  //ou @EJB si nom par défaut 
	private InitBean myBean; 
	
	public GetOneBookServlet() {
		// TODO Auto-generated constructor stub
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GsonBuilder gb = new GsonBuilder();
		Gson js = gb.excludeFieldsWithoutExposeAnnotation().create();

		List<Livre> l = myBean.getLesLivres();
		String str = js.toJson(l);
		
		response.setContentType("application/json");
		response.getWriter().println(str);
	}

}
