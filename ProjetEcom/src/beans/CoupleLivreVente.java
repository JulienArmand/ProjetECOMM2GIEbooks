package beans;

import com.google.gson.annotations.Expose;

import model.Livre;

public class CoupleLivreVente{
	
	@Expose
	Livre l;
	@Expose
	int v;
	public CoupleLivreVente(Livre l, Integer i){
		this.l = l;
		this.v = i;
	}
	
}

