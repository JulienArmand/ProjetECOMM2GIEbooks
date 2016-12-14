package model;

import javax.persistence.Entity;

/**
 * @author ochiers
 * Bean représentant un compte paypal
 */
@Entity
public class Paypal extends MoyenPaiement {

	private String adressePaypal;


	/**
	 * Constructeur vide
	 */
	public Paypal() {
		super();
	}
	
	/**
	 * Constructeur d'un compte paypal
	 * @param adressePaypal
	 */
	public Paypal(String adressePaypal) {
		super();
		this.adressePaypal = adressePaypal;
	}

	public String getAdressePaypal() {
		return adressePaypal;
	}

	public void setAdressePaypal(String adressePaypal) {
		this.adressePaypal = adressePaypal;
	}

}
