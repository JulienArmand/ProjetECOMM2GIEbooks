package beans;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateful;

@Stateful
public class ConfigurationGenerale {

	private static final String urlServ = "http://localhost";
	private static final String  portServ = "8080";
	private final Map<String, String> params;
	
	public ConfigurationGenerale(){
		
		this.params = new HashMap<>();
		Logger logger = Logger.getAnonymousLogger();
		try{
			URL url = new URL(urlServ+":"+portServ+"/admin/confGenerale.txt");
			InputStream is = url.openStream();
			BufferedReader r = new BufferedReader(new InputStreamReader(is));
			String empty = "";
			String equal = "=";
			String line = r.readLine();
			while (line != null) {
				
				if(!line.startsWith("#") && !line.equals(empty)){
					String[] str = line.split(equal);
					params.put(str[0], str[1]);
				}
				line = r.readLine();
			}
			
		}catch (Exception e){
			logger.log(Level.FINE, "an exception was thrown	", e);
		}
		
	}
	
	/**
	 * Donne la valeur du parametre demandé
	 * @param parameter Le parametre demandé
	 * @return Une String contenant la valeur du parametre demandé
	 */
	public String get(String parameter){
		return this.params.get(parameter);
	}
	
}
