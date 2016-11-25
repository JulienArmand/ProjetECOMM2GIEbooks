package servlets;

import java.io.IOException;
import javax.ejb.EJB;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.GestionClient;
import model.Client;

public class GetInfoClientServlet extends HttpServlet {

	private static final long serialVersionUID = -4348818455195397252L;
	@EJB()
	private GestionClient myBean;
	
	public GetInfoClientServlet() {
		// TODO Auto-generated constructor stub
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GsonBuilder gb = new GsonBuilder();
		Gson js = gb.excludeFieldsWithoutExposeAnnotation().create();
		Cookie[] cookies = request.getCookies();
		Cookie cookie = cookies[0];
		String pseudo = cookie.getValue();
		Client c = myBean.getClientFromPseudo(pseudo);
		String str = js.toJson(c);
		response.setContentType("application/json");
		response.getWriter().println(str);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}
