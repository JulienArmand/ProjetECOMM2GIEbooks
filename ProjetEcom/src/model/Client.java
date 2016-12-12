package model;

import java.util.Collection;
import java.util.LinkedList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.google.gson.annotations.Expose;

/**
 * Bean entity representant un client
 * @author Clement
 *
 */
/**
 * @author Clement
 *
 */
@Entity
public class Client {

	/**
	 * L'id unique du client
	 */
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO) 
	@Expose
	@Column(name="CLIENT_ID")
	private long id;
	/**
	 * Le pseudo du client
	 */
	@Expose
	private String pseudo;
	/**
	 * Le mail du client
	 */
	@Expose
	private String email;
	/**
	 * Le mot de passe du client
	 */
	@Expose
	private String motDePasse;
	/**
	 * Le nom du client
	 */
	@Expose
	private String nom;
	/**
	 * Le prenom du client
	 */
	@Expose
	private String prenom;
	/**
	 * Indique si le client s'est desinscrit
	 */
	@Expose
	private boolean desinscrit;
	
	/**
	 * Les avis donnés par le client
	 */
	@OneToMany(mappedBy="leClient",cascade = CascadeType.ALL, fetch = FetchType.EAGER) 
	private Collection<Avis> lesAvis;

	/**
	 * Les commandes effectuées par le client
	 */
	@OneToMany(mappedBy="leClient",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Collection<Commande> lesCommandes;

	/**
	 * Les moyens de paiement du client
	 */
	@OneToMany(mappedBy="leClient",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Collection<MoyenPaiement> lesMoyenDePaiement;
		
	/**
	 * Crée u client
	 * @param pseudo Le pseudo du client
	 * @param email Le mail du client
	 * @param motDePasse Le mot de passe du client
	 * @param nom Le nom du client
	 * @param prenom Le prenom du client
	 */
	public Client(String pseudo, String email, String motDePasse, String nom, String prenom) {
		super();
		this.pseudo = pseudo;
		this.email = email;
		this.motDePasse = motDePasse;
		this.nom = nom;
		this.prenom = prenom;
		this.desinscrit = false;
		this.lesAvis = new LinkedList<>();
	}

	/**
	 * Getter id
	 * @return
	 */
	public long getId() {
		return id;
	}

	/**
	 * Setter id
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Getter pseudo
	 * @return
	 */
	public String getPseudo() {
		return pseudo;
	}

	/**
	 * Setter pseudo
	 * @param pseudo
	 */
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	/**
	 * Getter email
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter email
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter mot de passe
	 * @return
	 */
	public String getMotDePasse() {
		return motDePasse;
	}

	/**
	 * Setter mot de passe
	 * @param motDePasse
	 */
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	/**
	 * Get nom
	 * @return
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Setter nom
	 * @param nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Getter prenom
	 * @return
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * Setter prenom
	 * @param prenom
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	/**
	 * Getter avis
	 * @return
	 */
	public Collection<Avis> getLesAvis() {
		return lesAvis;
	}
	
	/**
	 * Getter desinscrit
	 * @return
	 */
	public boolean getDesinscrit() {
		return this.desinscrit;
	}

	/**
	 * Setter desinscrit
	 * @param b
	 */
	public void setDesinscrit(boolean b) {
		this.desinscrit = b;
	}
	
	/**
	 * Setter les avis
	 * @param lesAvis
	 */
	public void setLesAvis(Collection<Avis> lesAvis) {
		this.lesAvis = lesAvis;
	}

	/**
	 * Getter les commandes
	 * @return
	 */
	public Collection<Commande> getLesCommandes() {
		return lesCommandes;
	}
	/**
	 * Setter les commandes
	 * @param lesCommandes
	 */
	public void setLesCommandes(Collection<Commande> lesCommandes) {
		this.lesCommandes = lesCommandes;
	}

	/**
	 * Getter les moyens de paiement
	 * @return
	 */
	public Collection<MoyenPaiement> getLesMoyenDePaiement() {
		return lesMoyenDePaiement;
	}
	
	/**
	 * Setter les moyens de paiement
	 * @param lesMoyenDePaiement
	 */
	public void setLesMoyenDePaiement(Collection<MoyenPaiement> lesMoyenDePaiement) {
		this.lesMoyenDePaiement = lesMoyenDePaiement;
	}
	
}
