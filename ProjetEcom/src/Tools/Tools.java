package Tools;

import java.text.Normalizer;

public class Tools {

	public static String normalisationString(String s) {
		return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").replaceAll("\"", "")
				.replaceAll("'", "");

	}
}
