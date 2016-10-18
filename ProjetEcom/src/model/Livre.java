package model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Livre {

	private long id;
	private String title;
	private String isbn;
	private Date dateDePublication;
	private int nbPages;
	private int prix;
	private String langue;
	private String langueOrigine;
	private Collection<Auteur> lesAuteurs;
	private Collection<Avis> lesAvis;
	private Collection<Vente> lesVentes;
	private Genre genre;
	private Editeur editeur;
	private Serie laSerie;
	
	public Livre() {
	}

	public Livre(String title, String isbn, Date dateDePublication, int nbPages, int prix, String langue,
			String langueOrigine) {
		super();
		this.title = title;
		this.isbn = isbn;
		this.dateDePublication = dateDePublication;
		this.nbPages = nbPages;
		this.prix = prix;
		this.langue = langue;
		this.langueOrigine = langueOrigine;
	}

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="LIVRE_ID")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public int getPrix() {
		return prix;
	}

	public void setPrix(int prix) {
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
	
	@ManyToMany(mappedBy="lesLivres")
	public void setLesAuteurs(Collection<Auteur> lesAuteurs) {
		this.lesAuteurs = lesAuteurs;
	}

	@OneToMany(mappedBy="leLivre") 
	public Collection<Avis> getLesAvis() {
		return lesAvis;
	}
	
	public void setLesAvis(Collection<Avis> lesAvis) {
		this.lesAvis = lesAvis;
	}

	public Genre getGenre() {
		return genre;
	}
	
	@ManyToOne 
	@JoinColumn(name="Genre_id") 
	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public Editeur getEditeur() {
		return editeur;
	}
	
	@ManyToOne 
	@JoinColumn(name="Editeur_id") 
	public void setEditeur(Editeur editeur) {
		this.editeur = editeur;
	}

	public Serie getLaSerie() {
		return laSerie;
	}

	@ManyToOne 
	@JoinColumn(name="Serie_id") 
	public void setLaSerie(Serie laSerie) {
		this.laSerie = laSerie;
	}

	public Collection<Vente> getLesVentes() {
		return lesVentes;
	}

	@OneToMany(mappedBy="livre") 
	public void setLesVentes(Collection<Vente> lesVentes) {
		this.lesVentes = lesVentes;
	}
	
}
