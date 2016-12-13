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

/**
 * Bean entity representant une commande (une commande est un ensemble de ventes)
 * @author Clement
 *
 */
@Entity
public class Commande {
	
	/**
	 * L'id unique d'une commande 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	private long				id;
	
	/**
	 * La date de la commande 
	 */
	@Expose
	private Date				dateDeVente;
	/**
	 * Le prix total de la commande
	 */
	@Expose
	private float				prixTotal;

	/**
	 * Les ventes de la commande
	 */
	@Expose
	@OneToMany(mappedBy = "laCommande", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Collection<Vente>	lesVentes;

	/**
	 * Le client qui a passé commande
	 */
	@Expose
	@ManyToOne
	@JoinColumn(name = "Client_id")
	private Client				leClient;

	/**
	 * Le moyen de paiement utilisé pour regler la commande
	 */
	@ManyToOne
	@JoinColumn(name = "MoyenPaiement_id")
	private MoyenPaiement		leMoyenDePaiement;

	/**
	 * Crée une commande
	 * @param dateDeVente
	 */
	public Commande(Date dateDeVente) {
		super();
		this.dateDeVente = dateDeVente;
		this.prixTotal = 0;
	}

	/**
	 * Getter id
	 * @return
	 */
	public long getId() {
		return id;
	}

	/**
	 * Setter id
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Getter date
	 * @return
	 */
	public Date getDateDeVente() {
		return dateDeVente;
	}

	/**
	 * Setter date
	 * @param dateDeVente
	 */
	public void setDateDeVente(Date dateDeVente) {
		this.dateDeVente = dateDeVente;
	}

	/**
	 * Getter prix totql
	 * @return
	 */
	public float getPrixTotal() {
		return prixTotal;
	}

	/**
	 * Setter prix totql
	 * @param prixTotal
	 */
	public void setPrixTotal(float prixTotal) {
		this.prixTotal = prixTotal;
	}

	/**
	 * Getter les ventes
	 * @return
	 */
	public Collection<Vente> getLesVentes() {
		return lesVentes;
	}

	/**
	 * Setter les ventes
	 * @param lesVentes
	 */
	public void setLesVentes(Collection<Vente> lesVentes) {
		this.lesVentes = lesVentes;
	}

	/**
	 * getter le client
	 * @return
	 */
	public Client getLeClient() {
		return leClient;
	}

	/**
	 * Setter leCLient
	 * @param leClient
	 */
	public void setLeClient(Client leClient) {
		this.leClient = leClient;
	}

	/**
	 * Getter oyen de paiement
	 * @return
	 */
	public MoyenPaiement getLeMoyenDePaiement() {
		return leMoyenDePaiement;
	}

	/**
	 * Setter moyen de paiement
	 * @param moyenDePaiement
	 */
	public void setLeMoyenDePaiement(MoyenPaiement moyenDePaiement) {
		this.leMoyenDePaiement = moyenDePaiement;
	}

}
