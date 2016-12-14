package tools;

import javax.servlet.http.Cookie;

/**
 * @author ochiers
 * Gestion des cookies
 */
public class GestionCookies {
	
	/**
	 * Recupération d'un cookie par son nom
	 * @param listCookies
	 * @param name
	 * @return
	 */
	public Cookie getCookieByName(Cookie[] listCookies, String name){
		int i = 0;
		while(i< listCookies.length && !listCookies[i].getName().equals(name)){
			i++;
		}
		return listCookies[i];
	}

}
