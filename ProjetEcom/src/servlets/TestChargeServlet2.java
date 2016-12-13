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

import beans.GestionAuteur;
import beans.GestionAvis;
import beans.GestionClient;
import beans.GestionCommande;
import beans.GestionEditeur;
import beans.GestionGenre;
import beans.GestionLivre;
import beans.GestionPromotion;
import beans.GestionVente;
import model.Auteur;
import model.Avis;
import model.Client;
import model.Commande;
import model.Editeur;
import model.Genre;
import model.Livre;
import model.Promotion;
import model.Vente;

public class TestChargeServlet2 extends HttpServlet {

	private static final long serialVersionUID = -3401197851263972685L;
	
		
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

}