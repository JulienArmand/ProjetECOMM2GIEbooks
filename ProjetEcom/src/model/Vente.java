package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Vente {

	
	private long id;
	private int prix;
    private Livre livre;
    private Commande laCommande;
    
	public Vente() {
	}

	public Vente(int prix) {
		super();
		this.prix = prix;
	}

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO) 
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getPrix() {
		return prix;
	}

	public void setPrix(int prix) {
		this.prix = prix;
	}

	public Livre getLivre() {
		return livre;
	}
	
	@ManyToOne 
	@JoinColumn(name="Livre_id") 
	public void setLivre(Livre livre) {
		this.livre = livre;
	}

	public Commande getLaCommande() {
		return laCommande;
	}

	@ManyToOne 
	@JoinColumn(name="Commande_id") 
	public void setLaCommande(Commande laCommande) {
		this.laCommande = laCommande;
	}
 
	
	
}
