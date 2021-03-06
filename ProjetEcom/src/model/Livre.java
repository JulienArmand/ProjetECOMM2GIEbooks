
package model;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.gson.annotations.Expose;

/**
 * @author ochiers
 * Bean représentant un livre en BD
 */
@Entity
public class Livre {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "LIVRE_ID")
	@Expose
	private long				id;

	@Expose
	private String				titre;
	@Expose
	private String				isbn;
	@Expose
	@Temporal(TemporalType.DATE)
	private Date				dateDePublication;
	@Expose
	private int					nbPages;
	@Expose
	private float				prix;
	@Expose
	private String				langue;
	@Expose
	private String				langueOrigine;
	@Expose
	private String				nomCouverture;
	@Expose
	private String				nomFichier;
	@Expose
	@Lob
	@Column(length = 10000)
	private String				resume;
	@Expose
	@OneToOne
	private Promotion			promotion;

	@Expose
	@ManyToMany(mappedBy = "lesLivres", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Collection<Auteur>	lesAuteurs;

	@Expose
	@OneToMany(mappedBy = "leLivre", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Collection<Avis>	lesAvis;

	@OneToMany(mappedBy = "livre", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Collection<Vente>	lesVentes;

	@ManyToOne
	@Expose
	@JoinColumn(name = "Genre_id")
	private Genre				genre;

	@ManyToOne
	@Expose
	@JoinColumn(name = "Editeur_id")
	private Editeur				editeur;

	/**
	 * Créer les listes d'un livre
	 */
	public Livre() {
		super();
		this.lesAuteurs = new LinkedList<>();
		this.lesAvis = new LinkedList<>();
		this.lesVentes = new LinkedList<>();
		this.nomFichier="pdf/Exemple.pdf";
	}

	/**
	 * Créer un livre et associe les valeurs aux champs d'un livre
	 * @param title
	 * @param isbn
	 * @param dateDePublication
	 * @param nbPages
	 * @param prix
	 * @param langue
	 * @param langueOrigine
	 */
	public Livre(String title, String isbn, Date dateDePublication, int nbPages, float prix, String langue, String langueOrigine) {
		this();
		this.titre = title;
		this.isbn = isbn;
		this.dateDePublication = dateDePublication;
		this.nbPages = nbPages;
		this.prix = prix;
		this.langue = langue;
		this.langueOrigine = langueOrigine;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Date getDateDePublication() {
		return dateDePublication;
	}

	public void setDateDePublication(Date dateDePublication) {
		this.dateDePublication = dateDePublication;
	}

	public int getNbPages() {
		return nbPages;
	}

	public void setNbPages(int nbPages) {
		this.nbPages = nbPages;
	}

	public float getPrix() {
		return prix;
	}
	
	public float getPrixAvecPromo() {
		Promotion p = this.getPromotion();
		if(p != null && p.getDateDebut().before(Date.from(Instant.now())) &&  p.getDateFin().after(Date.from(Instant.now())))
			return prix-(prix*p.getTauxReduc())/100;
		else
			return prix;
	}

	public void setPrix(float prix) {
		this.prix = prix;
	}

	public String getLangue() {
		return langue;
	}

	public void setLangue(String langue) {
		this.langue = langue;
	}

	public String getLangueOrigine() {
		return langueOrigine;
	}

	public void setLangueOrigine(String langueOrigine) {
		this.langueOrigine = langueOrigine;
	}

	public Collection<Auteur> getLesAuteurs() {
		return lesAuteurs;
	}

	public void setLesAuteurs(Collection<Auteur> lesAuteurs) {
		this.lesAuteurs = lesAuteurs;
	}

	public Collection<Avis> getLesAvis() {
		return lesAvis;
	}

	public void setLesAvis(Collection<Avis> lesAvis) {
		this.lesAvis = lesAvis;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public Editeur getEditeur() {
		return editeur;
	}

	public void setEditeur(Editeur editeur) {
		this.editeur = editeur;
	}

	public Collection<Vente> getLesVentes() {
		return lesVentes;
	}

	public void setLesVentes(Collection<Vente> lesVentes) {
		this.lesVentes = lesVentes;
	}

	public String getNomCouverture() {
		return nomCouverture;
	}

	public void setNomCouverture(String nomCouverture) {
		this.nomCouverture = nomCouverture;
	}

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	/**
	 * Calcul la moyenne des avis
	 * @return
	 */
	public int calculMoyenneAvis() {

		if (this.lesAvis.isEmpty())
			return 0;

		int x = 0;
		Iterator<Avis> it = this.lesAvis.iterator();
		while (it.hasNext())
			x += it.next().getNote();

		return x / this.lesAvis.size();

	}

	/**
	 * Ajoute un auteur au livre
	 * @param a
	 */
	public void addAuteur(Auteur a) {

		if (a != null) {
			if (this.lesAuteurs == null)
				this.lesAuteurs = new LinkedList<>();

			this.lesAuteurs.add(a);
		}

	}

}
