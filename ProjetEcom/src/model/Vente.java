package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.google.gson.annotations.Expose;

/**
 * @author ochiers
 * Beans entity représentant une vente
 */
@Entity
public class Vente {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	private long		id;
	@Expose
	private float		prix;

	@Expose
	@ManyToOne
	@JoinColumn(name = "Livre_id")
	private Livre		livre;

	@ManyToOne
	@JoinColumn(name = "Commande_id")
	private Commande	laCommande;

	/**
	 * Constructeur créant une vente
	 * @param prix Prix du livre au moment de la commande
	 */
	public Vente(float prix) {
		super();
		this.prix = prix;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public float getPrix() {
		return prix;
	}

	public void setPrix(float prix) {
		this.prix = prix;
	}

	public Livre getLivre() {
		return livre;
	}

	public void setLivre(Livre livre) {
		this.livre = livre;
	}

	public Commande getLaCommande() {
		return laCommande;
	}

	public void setLaCommande(Commande laCommande) {
		this.laCommande = laCommande;
	}

}
