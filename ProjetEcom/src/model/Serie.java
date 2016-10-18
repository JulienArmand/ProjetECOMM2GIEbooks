package model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Serie {

	private long id;
	private String nom;
	private Collection<Livre> lesLivres;
	
	public Serie() {
	}

	public Serie(String nom) {
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

	public Collection<Livre> getLesLivres() {
		return lesLivres;
	}
	
	@OneToMany(mappedBy="laSerie") 
	public void setLesLivres(Collection<Livre> lesLivres) {
		this.lesLivres = lesLivres;
	}

}
