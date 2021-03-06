package tools;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import org.apache.commons.lang3.StringEscapeUtils;

import model.Auteur;
import model.Livre;

/**
 * @author ochiers
 * Outil de recherche
 */
public class ElasticSearchTools {
	
	private static final String HTTP_PROXY_HOST = "http.proxyHost";
	private static final String HTTP_PROXY_PORT = "http.proxyPort";
	private static final String HTTPS_PROXY_HOST = "https.proxyHost";
	private static final String HTTPS_PROXY_PORT = "https.proxyPort";
	
	private static final String PROXY_HOST = "www-cache.ujf-grenoble.fr";
	private static final String PROXY_PORT = "3128";

	/**
	 * Recherche
	 * @param urlS
	 * @param methode
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static InputStream doRequest(String urlS, String methode, String data) throws IOException {

		System.setProperty(HTTP_PROXY_HOST, PROXY_HOST);
		System.setProperty(HTTP_PROXY_PORT, PROXY_PORT);
		System.setProperty(HTTPS_PROXY_HOST, PROXY_HOST);
		System.setProperty(HTTPS_PROXY_PORT, PROXY_PORT);

		URL url = new URL(urlS);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(methode);
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		connection.setRequestProperty("Content-Language", "en-US");
		connection.setRequestProperty("Content-Length", Integer.toString(data.getBytes().length));

		connection.setUseCaches(false);
		connection.setDoOutput(true);

		// Send request
		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
		wr.writeBytes(data);
		wr.close();

		return connection.getInputStream();

	}

	/**
	 * Index les données
	 * @param url
	 * @throws IOException
	 */
	public static void creerIndex(String url) throws IOException {

		System.setProperty(HTTP_PROXY_HOST, PROXY_HOST);
		System.setProperty(HTTP_PROXY_PORT, PROXY_PORT);
		System.setProperty(HTTPS_PROXY_HOST, PROXY_HOST);
		System.setProperty(HTTPS_PROXY_PORT, PROXY_PORT);

		String req = "{\"settings\":{\"analysis\":{\"filter\":{\"autocomplete_filter\":{\"type\":\"ngram\",\"min_gram\":1,\"max_gram\":20}},\"analyzer\":{\"autocomplete\":{\"type\":\"custom\",\"tokenizer\":\"standard\",\"filter\":[\"lowercase\",\"autocomplete_filter\"]}}}},\"mappings\":{\"type_rechercheTitreGenreAuteur\":{\"properties\":{\"titre\":{\"type\":\"text\",\"analyzer\":\"autocomplete\",\"search_analyzer\":\"simple\"},\"suggest_titre\":{\"type\": \"completion\",\"analyzer\": \"simple\", \"search_analyzer\": \"simple\"},\"suggest_auteurs\": {\"type\": \"completion\",\"analyzer\": \"simple\",\"search_analyzer\": \"simple\"}}}}}";
		InputStream is = ElasticSearchTools.doRequest(url, "PUT", req);
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		StringBuilder response = new StringBuilder(); // or StringBuffer if Java
														// version 5+
		String line;
		while ((line = rd.readLine()) != null) {
			response.append(line);
			response.append('\r');
		}
		rd.close();
	}

	/**
	 * Recherche
	 * @param requeteBarre
	 * @param d
	 * @param e
	 * @param genre
	 * @param avisMin
	 * @return
	 */
	public static String rechercheElasticSearch(String requeteBarre, Double d, Double e, String genre, int avisMin){

		String reqBarre = Tools.normalisationString(StringEscapeUtils.unescapeHtml4(requeteBarre));

		boolean prec = false;
		String blocReqBarre = "";
		String at = "@";
		if (!reqBarre.equals(at)) {
			blocReqBarre = "{ \"multi_match\":{ \"query\":\"" + reqBarre + "\",\"fuzziness\":\"AUTO\",\"operator\":\"and\", \"fields\": [ \"titre\", \"auteurs\" ]}}";
			prec = true;
		}
		String blocReqPrix = "";
		
		if (d != -1 || e != -1) {

			if (prec)
				blocReqPrix += ",";
			if (d != -1 && e != -1) {
				blocReqPrix += "{ \"range\" : { \"prix\" : {\"lte\" : " + e + "  ,\"gte\" : " + d + " }}}";

			} else if (d != -1 && e == -1) {
				blocReqPrix += "{ \"range\" : { \"prix\" : {\"gte\" : " + d + " }}}";

			} else { // prixMin == -1 || prixMax != -1
				blocReqPrix += "{ \"range\" : { \"prix\" : {\"lte\" : " + e + " }}}";
			}

			prec = true;
		}
		String blocReqGenre = "";
		if (!genre.equals(at)) {

			if (prec)
				blocReqGenre += ",";
			blocReqGenre += "{ \"term\" : { \"genre\" : \"" + genre.toLowerCase() + "\"} }";

			prec = true;
		}
		String blocReqAvis = "";
		if (avisMin != -1) {
			if (prec)
				blocReqAvis += ",";
			blocReqAvis += "{ \"range\" : { \"avis\" : {\"gte\" : " + avisMin + " }}}";
		}

		return "{\"from\" : 0, \"size\" : 10000,\"query\":{\"bool\": { \"must\": [" + blocReqBarre + "" + blocReqPrix + "" + blocReqGenre + "" + blocReqAvis + "]}}}";

	}

	/**
	 * Enregistre les données dans l'index
	 * @param url
	 * @param l
	 * @throws IOException
	 */
	public static void enregistrerDansLIndexage(String url, Livre l) throws IOException {
		StringBuilder auteursBuild = new StringBuilder();

		Iterator<Auteur> it = l.getLesAuteurs().iterator();
		while (it.hasNext()) {
			Auteur a = it.next();
			auteursBuild.append(a.getPrenom() + " " + a.getNom() + " ");
		}
		String auteurs = auteursBuild.toString();

		auteurs = Tools.normalisationString(auteurs);
		 
		StringBuilder tabTitreBuild = new StringBuilder();
		StringBuilder tabAuteursBuild = new StringBuilder();

		String[] tmpTitre = Tools.normalisationString(l.getTitre()).split(" ");
		for (int i = 0; i < tmpTitre.length; i++) {
			if (i == tmpTitre.length - 1)
				tabTitreBuild.append("\"" + tmpTitre[i] + "\"");
			else
				tabTitreBuild.append("\"" + tmpTitre[i] + "\"" + ", ");
		}

		String[] tmpAuteurs = auteurs.split(" ");
		String empty = "";
		for (int i = 0; i < tmpAuteurs.length; i++) {
			if (!tmpAuteurs[i].equals(empty)) {
				if (i == tmpAuteurs.length - 1)
					tabAuteursBuild.append("\"" + tmpAuteurs[i] + "\"");
				else
					tabAuteursBuild.append("\"" + tmpAuteurs[i] + "\"" + ", ");
			}
		}
		
		String tabTitre = tabTitreBuild.toString();
		String tabAuteurs = tabAuteursBuild.toString();

		String req = "\n{\"titre\":\"" + Tools.normalisationString(l.getTitre()) + "\", \"prix\":" + l.getPrixAvecPromo() + ", \"genre\":\"" + Tools.normalisationString(l.getGenre().getNom()) + "\", \"avis\":" + l.calculMoyenneAvis() + ", \"auteurs\":\"" + auteurs
				+ "\", \"suggest_titre\": { \"input\": [" + tabTitre + "] }, \"suggest_auteurs\": { \"input\": [" + tabAuteurs + "]}}";
		InputStream is = ElasticSearchTools.doRequest(url + "/livres/type_rechercheTitreGenreAuteur/" + l.getId(), "PUT", req);
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		StringBuilder response = new StringBuilder(); // or StringBuffer if Java
														// version 5+
		String line;
		while ((line = rd.readLine()) != null) {
			response.append(line);
			response.append('\r');
		}
		rd.close();
	}

	/**
	 * MAJ d'un avis
	 * @param l
	 * @throws IOException
	 */
	public static void updateAvis(Livre l) throws IOException {
		String req = "\n{\"doc\" : {\"avis\":" + l.calculMoyenneAvis() + "}}";

		System.setProperty(HTTP_PROXY_HOST, PROXY_HOST);
		System.setProperty(HTTP_PROXY_PORT, PROXY_PORT);
		System.setProperty(HTTPS_PROXY_HOST, PROXY_HOST);
		System.setProperty(HTTPS_PROXY_PORT, PROXY_PORT);

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
		rd.close();
	}

	/**
	 * Supprime l'index
	 * @param url
	 * @throws IOException
	 */
	public static void supprimerIndex(String url) throws IOException {

		System.setProperty(HTTP_PROXY_HOST, PROXY_HOST);
		System.setProperty(HTTP_PROXY_PORT, PROXY_PORT);
		System.setProperty(HTTPS_PROXY_HOST, PROXY_HOST);
		System.setProperty(HTTPS_PROXY_PORT, PROXY_PORT);

		InputStream is = ElasticSearchTools.doRequest(url, "DELETE", "");
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		StringBuilder response = new StringBuilder(); // or StringBuffer if
														// Java version 5+
		String line;
		while ((line = rd.readLine()) != null) {
			response.append(line);
			response.append('\r');
		}
		rd.close();

	}

}
