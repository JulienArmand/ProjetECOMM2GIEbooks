package model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public abstract class MoyenPaiement {

	private long id;
	private boolean actif;
	private Collection<Commande> lesCommandes;
	private Client leClient;
	
	public MoyenPaiement() {
		this.actif = true;
	}

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO) 
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isActif() {
		return actif;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}

	public Collection<Commande> getLesCommandes() {
		return lesCommandes;
	}

	@OneToMany(mappedBy="leMoyenDePaiement")
	public void setLesCommandes(Collection<Commande> lesCommandes) {
		this.lesCommandes = lesCommandes;
	}

	public Client getLeClient() {
		return leClient;
	}
	
	@ManyToOne 
	@JoinColumn(name="Client_id") 
	public void setLeClient(Client leClient) {
		this.leClient = leClient;
	}
	
}
