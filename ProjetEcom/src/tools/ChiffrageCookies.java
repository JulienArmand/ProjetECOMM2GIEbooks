package tools;

/**
 * @author ochiers
 * Gestion du chiffrage
 */
public class ChiffrageCookies {

	/**
	 * Chiffre la valeur d'un cookie
	 * @param cookieValue
	 * @return
	 */
	public static String chiffreString(String cookieValue) {
		char[] in = cookieValue.toCharArray();
		int begin = 0;
		int end = in.length - 1;
		char temp;
		while (end > begin) {
			temp = in[begin];
			in[begin] = in[end];
			in[end] = temp;
			end--;
			begin++;
		}
		return new String(in);
	}

	/**
	 * Déchiffre la valeur d'un cookie
	 * @param value
	 * @return
	 */
	public static String dechiffreString(String value) {
		char[] in = value.toCharArray();
		int begin = 0;
		int end = in.length - 1;
		char temp;
		while (end > begin) {
			temp = in[begin];
			in[begin] = in[end];
			in[end] = temp;
			end--;
			begin++;
		}
		return new String(in);
	}
}
