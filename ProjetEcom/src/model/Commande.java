package model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class Commande {

	private long id;
	private Date dateDeVente;
	private int prixTotal;
	private Collection<Vente> lesVentes;
	private Client leClient;
	private MoyenPaiement leMoyenDePaiement;
	
	
	public Commande() {
	}

	public Commande(Date dateDeVente, int prixTotal) {
		super();
		this.dateDeVente = dateDeVente;
		this.prixTotal = prixTotal;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
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

	public Collection<Vente> getLesVentes() {
		return lesVentes;
	}
	
	@OneToMany(mappedBy="laCommande")
	public void setLesVentes(Collection<Vente> lesVentes) {
		this.lesVentes = lesVentes;
	}

	public Client getLeClient() {
		return leClient;
	}
	
	@ManyToOne 
	@JoinColumn(name="Client_id") 
	public void setLeClient(Client leClient) {
		this.leClient = leClient;
	}

	public MoyenPaiement getLeMoyenDePaiement() {
		return leMoyenDePaiement;
	}

	@ManyToOne 
	@JoinColumn(name="MoyenPaiement_id") 
	public void setLeMoyenDePaiement(MoyenPaiement moyenDePaiement) {
		this.leMoyenDePaiement = moyenDePaiement;
	}
	
	

}
