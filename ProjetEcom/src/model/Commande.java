package model;

import java.util.Date;

public class Commande {

	private long id;
	private Date dateDeVente;
	private int prixTotal;
	
	public Commande() {
	}

	public Commande(Date dateDeVente, int prixTotal) {
		super();
		this.dateDeVente = dateDeVente;
		this.prixTotal = prixTotal;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDateDeVente() {
		return dateDeVente;
	}

	public void setDateDeVente(Date dateDeVente) {
		this.dateDeVente = dateDeVente;
	}

	public int getPrixTotal() {
		return prixTotal;
	}

	public void setPrixTotal(int prixTotal) {
		this.prixTotal = prixTotal;
	}
	
	

}
