package model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.google.gson.annotations.Expose;


@Entity
public class Commande {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Expose
	private long id;
	@Expose
	private Date dateDeVente;
	@Expose
	private float prixTotal;
	
	
	@OneToMany(mappedBy="laCommande", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Collection<Vente> lesVentes;
	
	@ManyToOne 
	@JoinColumn(name="Client_id") 
	private Client leClient;
	
	@ManyToOne 
	@JoinColumn(name="MoyenPaiement_id") 
	private MoyenPaiement leMoyenDePaiement;
	
	
	public Commande() {
	}

	public Commande(Date dateDeVente, float prixTotal) {
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

	public float getPrixTotal() {
		return prixTotal;
	}

	public void setPrixTotal(float prixTotal) {
		this.prixTotal = prixTotal;
	}

	public Collection<Vente> getLesVentes() {
		return lesVentes;
	}
	

	public void setLesVentes(Collection<Vente> lesVentes) {
		this.lesVentes = lesVentes;
	}

	public Client getLeClient() {
		return leClient;
	}
	

	public void setLeClient(Client leClient) {
		this.leClient = leClient;
	}

	public MoyenPaiement getLeMoyenDePaiement() {
		return leMoyenDePaiement;
	}


	public void setLeMoyenDePaiement(MoyenPaiement moyenDePaiement) {
		this.leMoyenDePaiement = moyenDePaiement;
	}
	
	

}
