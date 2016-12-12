package tools;

import javax.servlet.http.Cookie;

public class GestionCookies {
	
	public Cookie getCookieByName(Cookie[] listCookies, String name){
		int i = 0;
		while(i< listCookies.length && !listCookies[i].getName().equals(name)){
			i++;
		}
		return listCookies[i];
	}

}
