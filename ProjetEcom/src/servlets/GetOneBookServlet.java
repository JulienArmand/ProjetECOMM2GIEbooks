package servlets;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.InitBean;
import model.Auteur;
import model.Livre;

public class GetOneBookServlet extends HttpServlet {

	@EJB()  //ou @EJB si nom par défaut 
	private InitBean myBean; 
	
	public GetOneBookServlet() {
		// TODO Auto-generated constructor stub
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Livre l = myBean.getFirstLivre();
		response.getWriter().println("<html><body>");
		response.getWriter().println("<p>"+l.getIsbn()+"</p>");
		response.getWriter().println("<p>"+l.getLangue()+"</p>");
		response.getWriter().println("<p>"+l.getLangueOrigine()+"</p>");
		response.getWriter().println("<p>"+l.getNbPages()+"</p>");
		response.getWriter().println("<p>"+l.getTitle()+"</p>");
		response.getWriter().println("<p>"+l.getDateDePublication()+"</p>");
		response.getWriter().println("<p>"+l.getEditeur().getNom()+"</p>");
		if(l.getLesAuteurs() != null)
			response.getWriter().println("<p>"+l.getLesAuteurs().size()+" auteurs</p>");
		else
			response.getWriter().println("<p>pas d'auteurs</p>");
		
		List<Auteur> l2 = myBean.getLesAuteurs();
		for(int i=0; i< l2.size();i++){
			response.getWriter().println("<p>"+l2.get(i).getNom()+"</p>");
			
		}
		response.getWriter().println("</body></html>");
	}

}
