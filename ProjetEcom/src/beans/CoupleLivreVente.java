package beans;

import com.google.gson.annotations.Expose;

import model.Livre;

/**
 * Classe associant un livre et le nombre de fois qu'il a été vendu.
 * Cette classe sert au front-end pour pouvoir trié une liste de livre en fonction des ventes
 * @author Clement
 *
 */
public class CoupleLivreVente{
	
	/**
	 * Le livre
	 */
	@Expose
	Livre l;
	/**
	 * Le nombre de ventes du livre
	 */
	@Expose
	int v;
	/**
	 * Le constructeur
	 * @param l Le livre
	 * @param i Le nomvre de ventes
	 */
	public CoupleLivreVente(Livre l, Integer i){
		this.l = l;
		this.v = i;
	}
	
}

