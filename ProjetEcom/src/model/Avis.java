package model;

import java.util.Date;

public class Avis {

	private long id;
	private int note;
	private String commentaire;
	private Date dateDePublication;
	
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

	
	
}
