package model;

import java.util.Collection;
import java.util.LinkedList;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.google.gson.annotations.Expose;

/**
 * @author ochiers
 * Bean représentant un genre
 */
@Entity
public class Genre {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO) 
	@Expose
	private long id;
	@Expose
	private String nom;
	@OneToMany(mappedBy="genre",cascade = CascadeType.ALL, fetch = FetchType.EAGER) 
	private Collection<Livre> lesLivres;

	/**
	 * Constructeur d'un genre
	 * @param nom
	 */
	public Genre(String nom) {
		this.lesLivres = new LinkedList<>();
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
	
	/**
	 * Ajout un livre au genre
	 * @param l
	 */
	public void addLivre(Livre l) {
		if (l != null) {
			if (this.lesLivres == null)
				this.lesLivres = new LinkedList<>();

			this.lesLivres.add(l);
		}
	}
}
