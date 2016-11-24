package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.GestionLivre;
import beans.InitBean;
import model.Livre;

public class GetLivreRechercheServlet extends HttpServlet {

	@EJB()  //ou @EJB si nom par défaut 
	private GestionLivre myBean; 
	
	public GetLivreRechercheServlet() {
		// TODO Auto-generated constructor stub
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GsonBuilder gb = new GsonBuilder();
		Gson js = gb.excludeFieldsWithoutExposeAnnotation().create();

	//A supprimer TEST
		String texte ="walking et";

		List<String> genre= new ArrayList<>();
		String manga ="Manga";
		String comics="Comics";
		genre.add(manga);
		genre.add(comics);
	//fin supprimer
		
		List<Livre> l = myBean.getLivreRecherche(texte,genre/*request.getParameter("inputSearchQuerry")*/);
		String str = js.toJson(l);
		
		response.setContentType("application/json");
		response.getWriter().println(str);
	}
	

}
