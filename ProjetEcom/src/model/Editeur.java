package model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Editeur {

	private long id;
	private String nom;
    private Collection<Livre> lesLivres; 
	
	
	public Editeur() {
	}

	public Editeur(String nom) {
		super();
		this.nom = nom;
	}

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO) 
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

	@OneToMany(mappedBy="editeur") 
	public Collection<Livre> getLesLivres() {
		return lesLivres;
	}

	public void setLesLivres(Collection<Livre> lesLivres) {
		this.lesLivres = lesLivres;
	}

}
