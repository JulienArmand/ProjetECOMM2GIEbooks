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

public class InscriptionClientServlet extends HttpServlet {

	private static final long serialVersionUID = 268367471001606128L;
	
	@EJB() 
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
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String erreurs="";
        /* Récupération des champs du formulaire. */
        String email = request.getParameter( CHAMP_EMAIL );
        String motDePasse = request.getParameter( CHAMP_PASS );
        String confirmation = request.getParameter( CHAMP_CONF );
        String nom = request.getParameter( CHAMP_NOM );
        String prenom = request.getParameter( CHAMP_PRENOM );
        String identifiant = request.getParameter( CHAMP_IDENTIFIANT );
        
        /* Validation du champ email. */
        try {
            validationEmail( email );
        } catch ( Exception e ) {
            erreurs=e.getMessage();
            System.out.println("ERREUR "+e);
        }

        /* Validation des champs mot de passe et confirmation. */
        try {
            validationMotsDePasse( motDePasse, confirmation );
        } catch ( Exception e ) {
        	erreurs=e.getMessage();
        	System.out.println("ERREUR "+e);
        }

        /* Validation du champ nom. */
        try {
            validationIdentifiant( identifiant );
        } catch ( Exception e ) {
            erreurs=e.getMessage();
            System.out.println("ERREUR "+e);
        }

        /* Initialisation du résultat global de la validation. */
        if ( erreurs.isEmpty() ) {
            inscriptionClient(identifiant, email, motDePasse, nom, prenom);
        }

        String json = new Gson().toJson(erreurs);
        response.setContentType("application/json");
        response.getWriter().write(json);

	}

	private void inscriptionClient(String pseudo, String email, String motDePasse, String nom, String prenom) {
		myBean.creerClient(pseudo, email, motDePasse, nom, prenom);
	}
	
	/**
	 * Valide l'adresse mail saisie.
	 */
	private void validationEmail( String email ) throws Exception {
	    if (!( email != null && email.trim().length() != 0 )) {
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
	    } else {
	        throw new Exception("Merci de saisir et confirmer votre mot de passe.");
	    }
	}

	/**
	 * Valide le nom d'utilisateur saisi.
	 */
	private void validationIdentifiant( String identifiant ) throws Exception {

	    if (identifiant.trim().length() > 16 ) {
	        throw new Exception( "Le nom d'utilisateur doit contenir entre 3 et 16 caractères." );
	    }
	    if(myBean.pseudoDejaPris(identifiant)){
	    	throw new Exception( "identifiant déja utilisé." );
	    }
	}
	
}