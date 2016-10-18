package model;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class CarteBancaire extends MoyenPaiement {

	private String numeroCarte;
	private Date dateDePeremption;

	public CarteBancaire() {
		super();
	}

	public CarteBancaire(String numeroCarte, Date dateDePeremption) {
		super();
		this.numeroCarte = numeroCarte;
		this.dateDePeremption = dateDePeremption;
	}

	public String getNumeroCarte() {
		return numeroCarte;
	}

	public void setNumeroCarte(String numeroCarte) {
		this.numeroCarte = numeroCarte;
	}

	public Date getDateDePeremption() {
		return dateDePeremption;
	}

	public void setDateDePeremption(Date dateDePeremption) {
		this.dateDePeremption = dateDePeremption;
	}

}
