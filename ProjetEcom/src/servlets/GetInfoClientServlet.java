package servlets;

import java.io.IOException;
import javax.ejb.EJB;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.GestionClient;
import model.Client;

/**
 * @author ochiers
 * Servlet retournant les infos d'un client
 */
public class GetInfoClientServlet extends HttpServlet {

	private static final long serialVersionUID = -4348818455195397252L;

	@EJB()
	private GestionClient myBean;
	
	/** 
	 * {@inheritDoc}
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GsonBuilder gb = new GsonBuilder();
		Gson js = gb.excludeFieldsWithoutExposeAnnotation().create();
		String pseudo = request.getParameter("pseudo");
		Client c = myBean.getClientFromPseudo(pseudo);
		String str = js.toJson(c);
		response.setContentType("application/json");
		response.getWriter().println(str);
	}
	
}
