package beans;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Avis;
import model.Client;
import model.Livre;

@Stateless
public class GestionAvis {

	@PersistenceContext(unitName = "Database-unit")
	private EntityManager em;

	public Avis creerAvis(Livre l, Client c, int note, String commentaire) throws Exception {

		Date datePublication = Date.from(Instant.now());

		Avis a = new Avis(note, commentaire, datePublication);

		a.setLeLivre(l);
		l.getLesAvis().add(a);

		a.setLeClient(c);
		c.getLesAvis().add(a);

		String req = "\n{\"doc\" : {\"avis\":" + l.calculMoyenneAvis() + "}}";

		System.out.println("MAJ " + req + " id : " + l.getId());

		URL url = new URL("http://localhost:9200/livres/type_rechercheTitreGenreAuteur/" + l.getId() + "/_update");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		connection.setRequestProperty("Content-Language", "en-US");
		connection.setRequestProperty("Content-Length", Integer.toString(req.getBytes().length));

		connection.setUseCaches(false);
		connection.setDoOutput(true);

		// Send request
		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
		wr.writeBytes(req);
		wr.close();

		InputStream is = connection.getInputStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		StringBuilder response = new StringBuilder(); // or StringBuffer if
														// Java version 5+
		String line;
		while ((line = rd.readLine()) != null) {
			response.append(line);
			response.append('\r');
		}
		System.out.println(line);
		rd.close();

		em.persist(a);
		return a;
	}

	public void supprimerTous() {
		Query q = em.createNativeQuery("DELETE FROM Avis");
		Query q2 = em.createNativeQuery("ALTER TABLE Avis {ALTER id RESTART WITH 0} ");
		q.executeUpdate();
		q2.executeUpdate();
	}
}
