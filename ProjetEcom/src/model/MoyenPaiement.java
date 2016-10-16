package model;

public abstract class MoyenPaiement {

	private long id;
	private boolean actif;
	
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
	
}
