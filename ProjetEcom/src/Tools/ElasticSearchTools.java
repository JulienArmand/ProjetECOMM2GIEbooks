package Tools;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang3.StringEscapeUtils;

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

		String req = "{\"settings\":{\"analysis\":{\"filter\":{\"autocomplete_filter\":{\"type\":\"ngram\",\"min_gram\":1,\"max_gram\":20}},\"analyzer\":{\"autocomplete\":{\"type\":\"custom\",\"tokenizer\":\"standard\",\"filter\":[\"lowercase\",\"autocomplete_filter\"]}}}},\"mappings\":{\"type_rechercheTitreGenreAuteur\":{\"properties\":{\"titre\":{\"type\":\"text\",\"analyzer\":\"autocomplete\",\"search_analyzer\":\"simple\"}}}}}";

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
			blocReqBarre = "{ \"match\":{ \"titre\":{ \"query\":\"" + reqBarre
					+ "\",\"fuzziness\":\"AUTO\",\"operator\":\"and\"}}}";
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

}
