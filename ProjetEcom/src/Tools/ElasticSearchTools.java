package Tools;

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


public class ElasticSearchTools {
	
	public static InputStream doRequest(String urlS, String methode, String data) throws IOException {

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

	public static void creerIndex() throws IOException {

		String req = "{\"settings\":{\"analysis\":{\"filter\":{\"autocomplete_filter\":{\"type\":\"ngram\",\"min_gram\":1,\"max_gram\":20}},\"analyzer\":{\"autocomplete\":{\"type\":\"custom\",\"tokenizer\":\"standard\",\"filter\":[\"lowercase\",\"autocomplete_filter\"]}}}},\"mappings\":{\"type_rechercheTitreGenreAuteur\":{\"properties\":{\"titre\":{\"type\":\"text\",\"analyzer\":\"autocomplete\",\"search_analyzer\":\"simple\"},\"suggest_titre\":{\"type\": \"completion\",\"analyzer\": \"simple\", \"search_analyzer\": \"simple\"},\"suggest_auteurs\": {\"type\": \"completion\",\"analyzer\": \"simple\",\"search_analyzer\": \"simple\"}}}}}";
		InputStream is = ElasticSearchTools.doRequest("http://localhost:9200/livres", "PUT", req);
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
	
	
	public static String rechercheElasticSearch(String requeteBarre, int prixMin, int prixMax, String genre, int avisMin)
			throws Exception {


		String reqBarre = Tools.normalisationString(StringEscapeUtils.unescapeHtml4(requeteBarre));

		boolean prec = false;
		String blocReqBarre = "";
		if (!reqBarre.equals("@")) {
			blocReqBarre = "{ \"multi_match\":{ \"query\":\"" + reqBarre
					+ "\",\"fuzziness\":\"AUTO\",\"operator\":\"and\", \"fields\": [ \"titre\", \"auteurs\" ]}}";
			prec = true;
		}
		String blocReqPrix = "";
		if (prixMin != -1 || prixMax != -1) {

			if (prec)
				blocReqPrix += ",";
			if (prixMin != -1 && prixMax != -1) {
				blocReqPrix += "{ \"range\" : { \"prix\" : {\"lte\" : " + prixMax + "  ,\"gte\" : " + prixMin + " }}}";
				
			}
			else if (prixMin != -1 && prixMax == -1) {
				blocReqPrix += "{ \"range\" : { \"prix\" : {\"gte\" : " + prixMin + " }}}";

			} else { // prixMin == -1 || prixMax != -1
				blocReqPrix += "{ \"range\" : { \"prix\" : {\"lte\" : " + prixMax + " }}}";
			}

			prec = true;
		}
		String blocReqGenre = "";
		if (!genre.equals("@")) {

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
			prec = true;
		}

		String req = "{\"from\" : 0, \"size\" : 10000,\"query\":{\"bool\": { \"must\": [" + blocReqBarre + ""
				+ blocReqPrix + "" + blocReqGenre + "" + blocReqAvis + "]}}}";

		return req;
	}
	
	public static void enregistrerDansLIndexage(Livre l) throws IOException {
		String auteurs = "";
		
		
		Iterator<Auteur> it = l.getLesAuteurs().iterator();
		while(it.hasNext()){
			Auteur a = it.next();
			auteurs += a.getPrenom() + " " + a.getNom() + " ";
		}
		
		auteurs = Tools.normalisationString(auteurs);
		
		String tabTitre = "";
		String tabAuteurs = "";
		
		String [] tmpTitre = Tools.normalisationString(l.getTitre()).split(" ");
		for(int i = 0; i < tmpTitre.length; i++ ) {
			if(i == tmpTitre.length-1)
				tabTitre += "\"" + tmpTitre[i] + "\"";
			else tabTitre += "\"" + tmpTitre[i] + "\"" + ", ";
		}
		
		String [] tmpAuteurs = auteurs.split(" ");
		for(int i = 0; i < tmpAuteurs.length; i++ ) {
			if(!tmpAuteurs[i].equals("")){
				if(i == tmpAuteurs.length-1)
					tabAuteurs += "\"" + tmpAuteurs[i] + "\"";
				else tabAuteurs += "\"" + tmpAuteurs[i] + "\"" + ", ";
			}
		}
		
		String req = "\n{\"titre\":\"" + Tools.normalisationString(l.getTitre()) + "\", \"prix\":" + l.getPrixAvecPromo()
				+ ", \"genre\":\"" + Tools.normalisationString(l.getGenre().getNom()) + "\", \"avis\":"
				+ l.calculMoyenneAvis() + ", \"auteurs\":\""	+ auteurs + "\", \"suggest_titre\": { \"input\": ["+tabTitre+"] }, \"suggest_auteurs\": { \"input\": ["+tabAuteurs+"]}}";
		System.out.println(req);
		InputStream is = ElasticSearchTools
				.doRequest("http://localhost:9200/livres/type_rechercheTitreGenreAuteur/" + l.getId(), "PUT", req);
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

}
