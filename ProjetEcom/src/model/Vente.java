package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Vente {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO) 
	private long id;
	private int prix;
	
	
	@ManyToOne 
	@JoinColumn(name="Livre_id")
    private Livre livre;
	
	@ManyToOne 
	@JoinColumn(name="Commande_id") 
    private Commande laCommande;
    
	public Vente() {
	}

	public Vente(int prix) {
		super();
		this.prix = prix;
	}


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
