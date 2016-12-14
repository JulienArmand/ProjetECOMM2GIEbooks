package model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * @author ochiers
 * Bean représentant un moyen de paiement en BD
 */
@Entity
public abstract class MoyenPaiement {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long					id;
	private boolean					actif;

	@OneToMany(mappedBy = "leMoyenDePaiement", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Collection<Commande>	lesCommandes;

	@ManyToOne
	@JoinColumn(name = "Client_id")
	private Client					leClient;

	/**
	 * Constructeur rendant actif un moyen de paiement
	 */
	public MoyenPaiement() {
		this.actif = true;
	}

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

	public void setLesCommandes(Collection<Commande> lesCommandes) {
		this.lesCommandes = lesCommandes;
	}

	public Client getLeClient() {
		return leClient;
	}

	public void setLeClient(Client leClient) {
		this.leClient = leClient;
	}

}
