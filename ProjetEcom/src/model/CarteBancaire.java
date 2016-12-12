package model;

import java.util.Date;

import javax.persistence.Entity;

/**
 * Bean entity representant une carte bancaire
 * @author Clement
 *
 */
@Entity
public class CarteBancaire extends MoyenPaiement {

	/**
	 * Le numero de la carte
	 */
	private String numeroCarte;
	
	/**
	 * Date de peremption de la carte
	 */
	private Date dateDePeremption;

	/**
	 * Crée une carte bancaire
	 */
	public CarteBancaire() {
		super();
	}

	/**
	 * Crée une carte bancaire
	 * @param numeroCarte Le numéro de la carte
	 * @param dateDePeremption La date de peremption
	 */
	public CarteBancaire(String numeroCarte, Date dateDePeremption) {
		super();
		this.numeroCarte = numeroCarte;
		this.dateDePeremption = dateDePeremption;
	}

	/**
	 * Getter numero
	 * @return
	 */
	public String getNumeroCarte() {
		return numeroCarte;
	}

	/**
	 * Setter numero
	 * @param numeroCarte
	 */
	public void setNumeroCarte(String numeroCarte) {
		this.numeroCarte = numeroCarte;
	}

	/**
	 * Getter date
	 * @return
	 */
	public Date getDateDePeremption() {
		return dateDePeremption;
	}

	/**
	 * Setter date
	 * @param dateDePeremption
	 */
	public void setDateDePeremption(Date dateDePeremption) {
		this.dateDePeremption = dateDePeremption;
	}

}
