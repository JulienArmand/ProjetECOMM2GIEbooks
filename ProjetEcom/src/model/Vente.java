package model;

public class Vente {

	
	private long id;
	private int prix;
	
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
 
	
	
}
