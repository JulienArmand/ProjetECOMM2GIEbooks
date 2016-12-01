package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
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
	
	public static final String VUE          = "/partials/registerView.html";
	public static final String CHAMP_IDENTIFIANT = "identifiant";
	public static final String CHAMP_EMAIL  = "email";
    public static final String CHAMP_PASS   = "motdepasse";
    public static final String CHAMP_CONF   = "motdepasseconfirm";
    public static final String CHAMP_NOM    = "nom";
    public static final String CHAMP_PRENOM    = "prenom";
    public static final String ATT_ERREURS  = "erreurs";
    public static final String ATT_RESULTAT = "resultat";
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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

		
//		String resultat="okay";
//		System.out.println("TEST2");
//
//		String json = new Gson().toJson(resultat);
//        response.setContentType("application/json");
//        response.getWriter().write(json);


	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("TEST GET");
		String resultat;
//        Map<String, String> erreurs = new HashMap<String, String>();
		String erreurs="";
        /* Récupération des champs du formulaire. */
        String email = request.getParameter( CHAMP_EMAIL );
        String motDePasse = request.getParameter( CHAMP_PASS );
        String confirmation = request.getParameter( CHAMP_CONF );
        String nom = request.getParameter( CHAMP_NOM );
        String prenom = request.getParameter( CHAMP_PRENOM );
        String identifiant = request.getParameter( CHAMP_IDENTIFIANT );
        System.out.println("info: "+identifiant);
        System.out.println("info: "+nom);
        System.out.println("info: "+prenom);
        System.out.println("info: "+motDePasse);
        System.out.println("info: "+confirmation);
        System.out.println("info: "+email);
        
        /* Validation du champ email. */
        try {
            validationEmail( email );
        } catch ( Exception e ) {
//            erreurs.put( CHAMP_EMAIL, e.getMessage() );
            erreurs=e.getMessage();
            System.out.println("ERREUR "+e);
        }

        /* Validation des champs mot de passe et confirmation. */
        try {
            validationMotsDePasse( motDePasse, confirmation );
        } catch ( Exception e ) {
//            erreurs.put( CHAMP_PASS, e.getMessage() );
        	erreurs=e.getMessage();
        	System.out.println("ERREUR "+e);
        }

        /* Validation du champ nom. */
        try {
            validationIdentifiant( identifiant );
        } catch ( Exception e ) {
//            erreurs.put( CHAMP_IDENTIFIANT, e.getMessage() );
            erreurs=e.getMessage();
            System.out.println("ERREUR "+e);
        }

        /* Initialisation du résultat global de la validation. */
        if ( erreurs.isEmpty() ) {
            resultat = "Succès de l'inscription.";
            inscriptionClient(identifiant, email, motDePasse, nom, prenom);
        } else {
            resultat = "Échec de l'inscription.";
        }
        
        /* Stockage du résultat et des messages d'erreur dans l'objet request */
//        request.setAttribute( ATT_ERREURS, erreurs );
//        request.setAttribute( ATT_RESULTAT, resultat );

        /* Transmission de la paire d'objets request/response à notre JSP */
//        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
		
//		if(motDePasse.equals(confirmation)){
//			inscriptionClient(identifiant, email, motDePasse, nom, prenom);
//			inscriptionClient(request.getParameter("pseudo"), request.getParameter("email"), request.getParameter("motDePasse"), request.getParameter("nom"), request.getParameter("prenom"));
//			response.getWriter().println("Client créé");
//		}
//		response.getWriter().println("Le pseudo est :" + request.getParameter("pseudo"));
//		response.getWriter().println("Le nom est :" + request.getParameter("nom"));
//		response.getWriter().println("Le prenom est :" + request.getParameter("prenom"));
//		response.getWriter().println("Le mot de passe est :" + request.getParameter("motDePasse"));
//		response.getWriter().println("Le mail est :" + request.getParameter("email"));
//		RequestDispatcher dispatcher = request.getRequestDispatcher("html/inscriptionClient.html");
//		dispatcher.forward(request,response);
        System.out.println("RESULTAT "+ resultat);
        
        String json = new Gson().toJson(erreurs);
        response.setContentType("application/json");
        response.getWriter().write(json);
	//	response.sendRedirect(request.getContextPath()+"/#/inscription");
		
	}

	private void inscriptionClient(String pseudo, String email, String motDePasse, String nom, String prenom) {
//		myBean.suppressionClients();
		myBean.creerClient(pseudo, email, motDePasse, nom, prenom);
		System.out.println("test");
		
	}
	//IP changer .46
	
	/**
	 * Valide l'adresse mail saisie.
	 */
	private void validationEmail( String email ) throws Exception {
	    if ( email != null && email.trim().length() != 0 ) {
	        
//	    	if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
//	            throw new Exception( "Merci de saisir une adresse mail valide." );
//	        }
	    } else {
	        throw new Exception( "Merci de saisir une adresse mail." );
	    }
	    if(myBean.emailDejaPris(email)){
	    	throw new Exception( "Adresse mail déja utilisée." );
	    }
	}

	/**
	 * Valide les mots de passe saisis.
	 */
	private void validationMotsDePasse( String motDePasse, String confirmation ) throws Exception{
	    if (motDePasse != null && motDePasse.trim().length() != 0 && confirmation != null && confirmation.trim().length() != 0) {
	        if (!motDePasse.equals(confirmation)) {
	            throw new Exception("Les mots de passe entrés sont différents, merci de les saisir à nouveau.");
	        } 
//	        else if (motDePasse.trim().length() < 3) {
//	            throw new Exception("Les mots de passe doivent contenir au moins 3 caractères.");
//	        }
	    } else {
	        throw new Exception("Merci de saisir et confirmer votre mot de passe.");
	    }
	}

	/**
	 * Valide le nom d'utilisateur saisi.
	 */
	private void validationIdentifiant( String identifiant ) throws Exception {
	    if ( identifiant != null && identifiant.trim().length() < 3 ) {
//	        throw new Exception( "Le nom d'utilisateur doit contenir au moins 3 caractères." );
	    }
	    if(myBean.pseudoDejaPris(identifiant)){
	    	throw new Exception( "identifiant déja utilisé." );
	    }
	}
	
}