package model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Promotion {

	private long id;
	private int tauxReduc;
	private Date dateDebut;
	private Date dateFin;
	
	public Promotion() {
		// TODO Auto-generated constructor stub
	}

	public Promotion(int tauxReduc, Date dateDebut, Date dateFin) {
		super();
		this.tauxReduc = tauxReduc;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
	}

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO) 
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
	
	

}
