package model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Serie {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String nom;
	
	@OneToMany(mappedBy="laSerie",cascade = CascadeType.ALL, fetch = FetchType.EAGER) 
	private Collection<Livre> lesLivres;
	
	public Serie() {
	}

	public Serie(String nom) {
		super();
		this.nom = nom;
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

	public Collection<Livre> getLesLivres() {
		return lesLivres;
	}
	

	public void setLesLivres(Collection<Livre> lesLivres) {
		this.lesLivres = lesLivres;
	}

}
