package model;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Livre {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="LIVRE_ID")
	private long id;
	
	private String title;
	private String isbn;
	private Date dateDePublication;
	private int nbPages;
	private int prix;
	private String langue;
	private String langueOrigine;
	
	@ManyToMany(mappedBy="lesLivres",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Collection<Auteur> lesAuteurs;

	@OneToMany(mappedBy="leLivre",cascade = CascadeType.ALL, fetch = FetchType.EAGER) 
	private Collection<Avis> lesAvis;

	@OneToMany(mappedBy="livre",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Collection<Vente> lesVentes;
	
	@ManyToOne 
	@JoinColumn(name="Genre_id") 
	private Genre genre;
	
	@ManyToOne 
	@JoinColumn(name="Editeur_id")
	private Editeur editeur;
	
	@ManyToOne 
	@JoinColumn(name="Serie_id") 
	private Serie laSerie;
	
	public Livre() {
		super();
		this.lesAuteurs = new LinkedList<Auteur>();
		this.lesAvis = new LinkedList<Avis>();
		this.lesVentes = new LinkedList<Vente>();
	}

	public Livre(String title, String isbn, Date dateDePublication, int nbPages, int prix, String langue,
			String langueOrigine) {
		this();
		this.title = title;
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

	public Serie getLaSerie() {
		return laSerie;
	}


	public void setLaSerie(Serie laSerie) {
		this.laSerie = laSerie;
	}

	public Collection<Vente> getLesVentes() {
		return lesVentes;
	}
 
	public void setLesVentes(Collection<Vente> lesVentes) {
		this.lesVentes = lesVentes;
	}
	
}
