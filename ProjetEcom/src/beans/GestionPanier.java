package beans;

import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Livre;

@Stateless
public class GestionPanier {

	@PersistenceContext(unitName = "Database-unit")
	private EntityManager	em;

	private List<Livre>		lesLivres;
	private float			prixTotal;

	public void ajoutPanier(Livre l) {
		if (lesLivres == null) {
			lesLivres = new LinkedList<Livre>();
			prixTotal = 0;
		}

		lesLivres.add(l);
		prixTotal += l.getPrix();
	}
	
	public void supprimerLivre(Livre l) {
		lesLivres.remove(l);
		prixTotal -= l.getPrix();
	}

	public void supprimerPanier() {
		lesLivres.clear();
		prixTotal = 0;
	}

	public List<Livre> getLesLivres() {
		return lesLivres;
	}

	public float getPrix() {
		return prixTotal;
	}

}
