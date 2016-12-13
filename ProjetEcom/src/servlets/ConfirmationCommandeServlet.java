package servlets;

import java.io.IOException;
import java.util.Iterator;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.GestionCommande;
import beans.GestionLivre;
import model.Client;
import model.Commande;
import model.Vente;
import tools.EnvoiMail;

public class ConfirmationCommandeServlet extends HttpServlet {

	private static final long	serialVersionUID	= 6907236103034815181L;
	
	@EJB() 
	private GestionLivre		livreBean;
	
	@EJB() 
	private GestionCommande		commandeBean;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Long idCommande = Long.parseLong(request.getParameter("idCommande"));
		
		Commande c = commandeBean.getCommande(idCommande);
		Client client = c.getLeClient();
		StringBuilder strBuild = new StringBuilder();
		strBuild.append("Confirmation de votre commande\n");
		strBuild.append("Bonjour, " + client.getPseudo() +"\n");
		strBuild.append("Vous avez effectué une commande sur notre site le " + c.getDateDeVente() + " et nous vous en remercions.\n");
		strBuild.append("Dont voici le détail : \n");
		
		Iterator<Vente> it = c.getLesVentes().iterator();
		while(it.hasNext()){
			Vente v = it.next();
			strBuild.append(v.getLivre().getTitre() + " au prix de " + v.getPrix() + "€.\n");
		}
		
		strBuild.append("\nNous vous remercions de votre confiance et bonne lecture.\nA très bientot sur notre site ! \n L'équipe FuturaBooks.");
		
		EnvoiMail.envoyer_email(client.getEmail(), "Confirmation commande", strBuild.toString());
	}

	
}
