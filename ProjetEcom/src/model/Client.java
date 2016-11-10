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

@Entity
public class Client {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO) 
	@Expose
	@Column(name="CLIENT_ID")
	private long id;
	@Expose
	private String pseudo;
	@Expose
	private String email;
	@Expose
	private String motDePasse;
	@Expose
	private String nom;
	@Expose
	private String prenom;
	

	@OneToMany(mappedBy="leClient",cascade = CascadeType.ALL, fetch = FetchType.EAGER) 
	private Collection<Avis> lesAvis;

	@OneToMany(mappedBy="leClient",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Collection<Commande> lesCommandes;

	@OneToMany(mappedBy="leClient",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Collection<MoyenPaiement> lesMoyenDePaiement;
	
	public Client(){}
	
	public Client(String pseudo, String email, String motDePasse, String nom, String prenom) {
		super();
		this.pseudo = pseudo;
		this.email = email;
		this.motDePasse = motDePasse;
		this.nom = nom;
		this.prenom = prenom;
		this.lesAvis = new LinkedList<Avis>();
	}
	

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getPseudo() {
		return pseudo;
	}


	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getMotDePasse() {
		return motDePasse;
	}


	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public Collection<Avis> getLesAvis() {
		return lesAvis;
	}
	
	public void setLesAvis(Collection<Avis> lesAvis) {
		this.lesAvis = lesAvis;
	}

	public Collection<Commande> getLesCommandes() {
		return lesCommandes;
	}
	public void setLesCommandes(Collection<Commande> lesCommandes) {
		this.lesCommandes = lesCommandes;
	}

	public Collection<MoyenPaiement> getLesMoyenDePaiement() {
		return lesMoyenDePaiement;
	}
	
	public void setLesMoyenDePaiement(Collection<MoyenPaiement> lesMoyenDePaiement) {
		this.lesMoyenDePaiement = lesMoyenDePaiement;
	}
	
}
