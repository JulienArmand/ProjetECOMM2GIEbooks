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

import beans.InscriptionClientBean;
import model.Client;
import model.Livre;

public class InscriptionClientServlet extends HttpServlet {

	private static final long serialVersionUID = 268367471001606128L;
	
	@EJB()  //ou @EJB si nom par défaut 
	private InscriptionClientBean myBean; 
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getParameter("action").equals("supprimer"))
			myBean.suppressionClients();
		else {
			GsonBuilder gb = new GsonBuilder();
			Gson js = gb.excludeFieldsWithoutExposeAnnotation().create();
	
			List<Client> l = myBean.getLesClients();
			String str = js.toJson(l);
			
			response.setContentType("application/json");
			response.getWriter().println(str);
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("motDePasse").equals(request.getParameter("motDePasseConfirm"))){
			inscriptionClient(request.getParameter("pseudo"), request.getParameter("email"), request.getParameter("motDePasse"), request.getParameter("nom"), request.getParameter("prenom"));
			response.getWriter().println("Client créé");
		}
		System.out.println("Test 1");
		response.getWriter().println("Le pseudo est :" + request.getParameter("pseudo"));
		response.getWriter().println("Le nom est :" + request.getParameter("nom"));
		response.getWriter().println("Le prenom est :" + request.getParameter("prenom"));
		response.getWriter().println("Le mot de passe est :" + request.getParameter("motDePasse"));
		response.getWriter().println("Le mail est :" + request.getParameter("email"));
		
	}

	private void inscriptionClient(String pseudo, String email, String motDePasse, String nom, String prenom) {
//		myBean.suppressionClients();
		myBean.creerClient(pseudo, email, motDePasse, nom, prenom);
		System.out.println("test");
		
	}
}