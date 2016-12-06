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

import beans.GestionGenre;
import model.Genre;

public class GetTousLesGenres extends HttpServlet {

	private static final long serialVersionUID = -7552426816377790156L;
	
	@EJB()  //ou @EJB si nom par défaut 
	private GestionGenre myBean; 
	
	public GetTousLesGenres() {
		// TODO Auto-generated constructor stub
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GsonBuilder gb = new GsonBuilder();
		Gson js = gb.excludeFieldsWithoutExposeAnnotation().create();

		List<Genre> l = myBean.getLesGenres();
		String str = js.toJson(l);
		
		response.setContentType("application/json");
		response.getWriter().println(str);
	}

}