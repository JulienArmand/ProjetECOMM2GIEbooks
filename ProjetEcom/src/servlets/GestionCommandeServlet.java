package servlets;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Tools.GestionCookies;
import beans.GestionClient;
import beans.GestionCommande;
import beans.GestionLivre;
import beans.GestionPaiement;
import beans.GestionVente;

import model.Client;
import model.Commande;
import model.Vente;

public class GestionCommandeServlet extends HttpServlet {

	private static final long	serialVersionUID	= 268367471001606128L;

	@EJB() 
	private GestionCommande		commandeBean;

	@EJB() 
	private GestionClient		clientBean;

	@EJB() 
	private GestionPaiement		paiementBean;

	@EJB() 
	private GestionVente		venteBean;

	@EJB() 
	private GestionLivre		LivreBean;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GsonBuilder gb = new GsonBuilder();
		Gson js = gb.excludeFieldsWithoutExposeAnnotation().create();
		String str = null;

		if (request.getParameter("action").equals("verifAchete")) {
			Long id = Long.parseLong(request.getParameter("id"));
			Client client = clientBean.getClientByCookie(request);
			List<Commande> l = commandeBean.getCommandeClient(client);
			Iterator<Commande> it= l.iterator();
			boolean b = false;
			while(it.hasNext()) {
				Commande c = it.next();
				Collection<Vente> ventes = c.getLesVentes();
				Iterator<Vente> vIt = ventes.iterator();
				while (vIt.hasNext()){
					Vente v = vIt.next();
					if(id.equals(v.getLivre().getId())){
						b = true;
						break;
					}
				}
			}
			str = js.toJson(b);
		} else if (request.getParameter("action").equals("post")) {

			// Creer commande
			Cookie[] cookies = request.getCookies();
			GestionCookies g = new GestionCookies();
			String idClient = g.getCookieByName(cookies, "idClient").getValue();
			Client client = clientBean.getClient(Long.parseLong(idClient));

			Commande c = commandeBean.creerCommande(new Date(), client, paiementBean.getMoyenPaiement(client, request.getParameter("type")));

			// Creer ventes
			Collection<Vente> lesVentes = new LinkedList<Vente>();
			String[] livres = request.getParameter("livres").split(",");
			for (int i = 0; i < livres.length; i++) {
				Vente v = venteBean.creerVente(LivreBean.getLivreAvecId(Long.parseLong(livres[i])), c);
				lesVentes.add(v);
			}
			// Ajouter ventes
			commandeBean.setVentesCommande(c.getId(), lesVentes);

			Commande cFinal = commandeBean.getCommande(c.getId());
			str = js.toJson(cFinal);

		} else if (request.getParameter("action").equals("commande")) {
			Commande c = commandeBean.getCommande(Long.parseLong(request.getParameter("idCommande")));
			str = js.toJson(c);
		} else if (request.getParameter("action").equals("commandeClient")) {
			Client client = clientBean.getClientByCookie(request);
			List<Commande> l = commandeBean.getCommandeClient(client);
			str = js.toJson(l);
		}
		response.setContentType("application/json");
		response.getWriter().println(str);

	}

}
