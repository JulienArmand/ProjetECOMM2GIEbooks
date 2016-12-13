package model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.gson.annotations.Expose;

@Entity
public class Promotion {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO) 
	@Expose
	private long id;
	@Expose
	private int tauxReduc;
	@Temporal(TemporalType.DATE)
	@Expose
	private Date dateDebut;
	@Temporal(TemporalType.DATE)
	@Expose
	private Date dateFin;
	@OneToOne
	private Livre livre;
	
	public Promotion(int tauxReduc, Date dateDebut, Date dateFin) {
		this.tauxReduc = tauxReduc;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getTauxReduc() {
		return tauxReduc;
	}

	public void setTauxReduc(int tauxReduc) {
		this.tauxReduc = tauxReduc;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public Livre getLivre() {
		return livre;
	}

	public void setLivre(Livre livre) {
		this.livre = livre;
	}
		
}
