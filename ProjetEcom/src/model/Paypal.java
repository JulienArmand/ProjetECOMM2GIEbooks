package model;

public class Paypal extends MoyenPaiement {

	private String adressePaypal;

	public Paypal() {
		super();
	}

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
