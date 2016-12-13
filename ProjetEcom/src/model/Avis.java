package model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.google.gson.annotations.Expose;

/**
 * Bean entity representant un avis client
 * @author Clement
 *
 */
@Entity
public class Avis {

	/**
	 *  L'identifiant unique de l'avis
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "AVIS_ID")
	@Expose
	private long id;
	
	/**
	 * La note donnée par le client
	 */
	@Expose
	private int note;
	
	/**
	 * Le commentaire donné par le client (max 10000 chars)
	 */
	@Expose
	@Lob
	@Column(length = 10000)
	private String commentaire;
	
	/**
	 * La date de l'avis
	 */
	@Expose
	private Date dateDePublication;
	
	/**
	 * Le livre concerné par l'avis
	 */
	@ManyToOne 
	@JoinColumn(name="CLIENT_ID") 
	private Livre leLivre;
	
	/**
	 * Le client qui a donné son avis
	 */
	@ManyToOne 
	@Expose
	@JoinColumn(name="LIVRE_ID") 
	private Client leClient;

	/**
	 * Crée un avis
	 * @param note La note de l'avis
	 * @param commentaire Le commentaire de l'avis
	 * @param dateDePublication La date de l'avis
	 */
	public Avis(int note, String commentaire, Date dateDePublication) {
		super();
		this.note = note;
		this.commentaire = commentaire;
		this.dateDePublication = dateDePublication;
	}

	/**
	 * Retourne l'id de l'avis
	 * @return Un long etant l'id de l'avis
	 */
	public long getId() {
		return id;
	}

	/**
	 * Change l'id de l'avis (pas sur que cette fonction soit une bonne idée)
	 * @param id Le nouvel id de l'avis
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Retourne la note de l'avis
	 * @return La note de l'avis
	 */
	public int getNote() {
		return note;
	}

	/**
	 * Change la note de l'avis
	 * @param note La note à changer
	 */
	public void setNote(int note) {
		this.note = note;
	}

	/**
	 * Retourne le commentaire de l'avis
	 * @return Le commentaire de l'avis
	 */
	public String getCommentaire() {
		return commentaire;
	}

	/**
	 * Change le commentaire de l'avis
	 * @param commentaire Le commentaire à changer
	 */
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	/**
	 * Retourne la date de l'avis
	 * @return La date de l'avis
	 */
	public Date getDateDePublication() {
		return dateDePublication;
	}

	/**
	 * Change la date de l'avis
	 * @param dateDePublication La date à changer
	 */
	public void setDateDePublication(Date dateDePublication) {
		this.dateDePublication = dateDePublication;
	}
	/**
	 * Retourne le livre concerné par l'avis
	 * @return Le livre
	 */
	public Livre getLeLivre() {
		return leLivre;
	}
	
	/**
	 * Change le livre de l'avis
	 * @param leLivre Le livre à changer
	 */
	public void setLeLivre(Livre leLivre) {
		this.leLivre = leLivre;
	}

	/**
	 * Retourne le client de l'avis
	 * @return La client
	 */
	public Client getLeClient() {
		return leClient;
	}

	/**
	 * Change le client de l'avis
	 * @param leClient Le client à changer
	 */
	public void setLeClient(Client leClient) {
		this.leClient = leClient;
	}


}
