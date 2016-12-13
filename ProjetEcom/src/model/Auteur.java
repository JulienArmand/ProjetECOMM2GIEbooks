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

import com.google.gson.annotations.Expose;

/**
 * Bean entity représentant un auteur
 * @author Clement
 *
 */
@Entity
public class Auteur {
	
	/**
	 * L'identifiant unique de l'auteur
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "AUTEUR_ID")
	@Expose
	private long id;
	
	/**
	 * Le nom de l'auteu
	 */
	@Expose
	private String nom;
	
	/**
	 * Le prenom de l'auteur
	 */
	@Expose
	private String prenom;
	
	/**
	 * Les livres qu'a écrit l'auteur
	 */
	@ManyToMany
	@JoinTable(name = "LIVRE_AUTEUR_LIEN", joinColumns = @JoinColumn(name = "CI_AUTEUR_ID", referencedColumnName = "AUTEUR_ID"), 
				inverseJoinColumns = @JoinColumn(name = "CI_LIVRE_ID", referencedColumnName = "LIVRE_ID"))
	private Collection<Livre> lesLivres;

	/**
	 * Crée un auteur
	 */
	public Auteur() {
		this.lesLivres = new LinkedList<>();
	}

	/**
	 * Crée un auteur
	 * @param nom Le nom de l'auteur
	 * @param prenom Le prenom de l'auteur
	 */
	public Auteur(String nom, String prenom) {
		this();
		this.nom = nom;
		this.prenom = prenom;
	}


	/**
	 * Retourne l'id de l'auteur
	 * @return Un long etant l'id de l'auteur
	 */
	public long getId() {
		return id;
	}

	/**
	 * Change l'id de l'auteur (pas sur que cette fonction soit une bonne idée)
	 * @param id Le nouvel id de l'auteur
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Retourne le nom de l'auteur
	 * @return Le nom de l'auteur
	 */
	public String getNom() {
		return nom;
	}


	/**
	 * Change le nom de l'auteur
	 * @param nom Le nom à changer
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Retourne le prenom de l'auteur
	 * @return Le prenom de l'auteur
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * Change le prenom de l'auteur
	 * @param nom Le prenom à changer
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * Retourne la liste des livres de l'auteur
	 * @return La liste des livres de l'auteur
	 */
	public Collection<Livre> getLesLivres() {
		return lesLivres;
	}
	
	/**
	 * Ajoute unlivre a la listes des lires de l'auteur
	 * @param l Le livre à ajouter
	 */
	public void addLivre(Livre l) {

		if (l != null) {

			if (this.lesLivres == null)

				this.lesLivres = new LinkedList<>();

			this.lesLivres.add(l);
		}

	}
}
