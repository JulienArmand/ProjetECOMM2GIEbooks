package model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Avis {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "AVIS_ID")
	private long id;
	
	private int note;
	private String commentaire;
	private Date dateDePublication;
	
	@ManyToOne 
	@JoinColumn(name="CLIENT_ID") 
	private Livre leLivre;
	
	@ManyToOne 
	@JoinColumn(name="LIVRE_ID") 
	private Client leClient;

	public Avis() {
	}

	public Avis(int note, String commentaire, Date dateDePublication) {
		super();
		this.note = note;
		this.commentaire = commentaire;
		this.dateDePublication = dateDePublication;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getNote() {
		return note;
	}

	public void setNote(int note) {
		this.note = note;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public Date getDateDePublication() {
		return dateDePublication;
	}

	public void setDateDePublication(Date dateDePublication) {
		this.dateDePublication = dateDePublication;
	}

	public Livre getLeLivre() {
		return leLivre;
	}
	
	
	public void setLeLivre(Livre leLivre) {
		this.leLivre = leLivre;
	}

	public Client getLeClient() {
		return leClient;
	}


	public void setLeClient(Client leClient) {
		this.leClient = leClient;
	}


}
