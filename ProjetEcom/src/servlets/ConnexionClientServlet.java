package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.ConnexionClientBean;

public class ConnexionClientServlet extends HttpServlet {
	

	private static final long serialVersionUID = 5024415012647003429L;
	
	@EJB()
	private ConnexionClientBean myBean;
	
	public ConnexionClientServlet() {
		// TODO Auto-generated constructor stub
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(checkPseudoClientExiste(request.getParameter("pseudo"))){
			if(checkMotDePasseCorrect(request.getParameter("pseudo"), request.getParameter("motDePasse"))){
				//Pseudo existe et mot de passe correct -> valider la connexion
				Cookie login = new Cookie("login", request.getParameter("pseudo"));
				response.addCookie(login);
				PrintWriter out = response.getWriter();  
				response.setContentType("text/html");  
				out.println("<script type=\"text/javascript\">");  
				out.println("alert('Bienvenue "+request.getParameter("pseudo")+"');");  
				out.println("</script>");
				//Redirect to main page
				//response.sendRedirect("");
			}
			else{
				//Pseudo existe mais mot de passe incorrect
				response.getWriter().println("Le mot de passe que vous avez saisi est incorrect.");
			}
		}
		else{
			//Pseudo n'existe pas
			response.getWriter().println("Le pseudo que vous avez saisi n'existe pas..");
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(checkPseudoClientExiste(request.getParameter("pseudo"))){
			if(checkMotDePasseCorrect(request.getParameter("pseudo"), request.getParameter("motDePasse"))){
				//Pseudo existe et mot de passe correct -> valider la connexion
				Cookie login = new Cookie("login", request.getParameter("pseudo"));
				response.addCookie(login);
				PrintWriter out = response.getWriter();  
				response.setContentType("text/html");  
				out.println("<script type=\"text/javascript\">");  
				out.println("alert('Bienvenue2 "+request.getParameter("pseudo")+"');");  
				out.println("</script>");
				//Redirect to main page
				//response.sendRedirect("");
				RequestDispatcher dispatcher = request.getRequestDispatcher("index.html");
				dispatcher.forward(request,response);
			}
			else{
				//Pseudo existe mais mot de passe incorrect
				response.getWriter().println("Le mot de passe que vous avez saisi est incorrect.");
			}
		}
		else{
			//Pseudo n'existe pas
			response.getWriter().println("Le pseudo que vous avez saisi n'existe pas..");
		}
	}
	
	
	private boolean checkPseudoClientExiste(String pseudo){
		boolean result = myBean.checkPseudoExiste(pseudo);
		return result;
	}
	
	private boolean checkMotDePasseCorrect(String pseudo, String motDePasse){
		return myBean.checkMotDePasseCorrect(pseudo, motDePasse);
	}
	
}