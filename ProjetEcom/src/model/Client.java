package model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Client {

	private long id;
	private String pseudo;
	private String email;
	private String motDePasse;
	private String nom;
	private String prenom;
	private Collection<Avis> lesAvis;
	private Collection<Commande> lesCommandes;
	private Collection<MoyenPaiement> lesMoyenDePaiement;
	
	public Client(){}
	
	public Client(String pseudo, String email, String motDePasse, String nom, String prenom) {
		super();
		this.pseudo = pseudo;
		this.email = email;
		this.motDePasse = motDePasse;
		this.nom = nom;
		this.prenom = prenom;
	}
	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO) 
	@Column(name="CLIENT_ID")
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
	@OneToMany(mappedBy="leClient") 
	public Collection<Avis> getLesAvis() {
		return lesAvis;
	}
	
	public void setLesAvis(Collection<Avis> lesAvis) {
		this.lesAvis = lesAvis;
	}

	public Collection<Commande> getLesCommandes() {
		return lesCommandes;
	}
	@OneToMany(mappedBy="leClient")
	public void setLesCommandes(Collection<Commande> lesCommandes) {
		this.lesCommandes = lesCommandes;
	}

	public Collection<MoyenPaiement> getLesMoyenDePaiement() {
		return lesMoyenDePaiement;
	}
	
	@OneToMany(mappedBy="leClient")
	public void setLesMoyenDePaiement(Collection<MoyenPaiement> lesMoyenDePaiement) {
		this.lesMoyenDePaiement = lesMoyenDePaiement;
	}
	
}
