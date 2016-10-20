package model;

import java.util.Collection;
import java.util.LinkedList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;;

@Entity
public class Auteur {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "AUTEUR_ID")
	private long id;
	
	private String nom;
	private String prenom;
	
	@ManyToMany
	@JoinTable(name = "LIVRE_AUTEUR_LIEN", joinColumns = @JoinColumn(name = "CI_AUTEUR_ID", referencedColumnName = "AUTEUR_ID"), 
				inverseJoinColumns = @JoinColumn(name = "CI_LIVRE_ID", referencedColumnName = "LIVRE_ID"))
	private Collection<Livre> lesLivres;

	public Auteur() {
		super();
		this.lesLivres = new LinkedList<>();
	}

	public Auteur(String nom, String prenom) {
		this();
		this.nom = nom;
		this.prenom = prenom;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	
	public Collection<Livre> getLesLivres() {
		return lesLivres;
	}
	
	
	public void setLesLivres(Collection<Livre> lesLivres) {
		this.lesLivres = lesLivres;
	}

}
