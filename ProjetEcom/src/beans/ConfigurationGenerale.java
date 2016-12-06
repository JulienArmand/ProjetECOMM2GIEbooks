package beans;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateful;

@Stateful
public class ConfigurationGenerale {

	private final String urlServ = "http://localhost";
	private final String  portServ = "8080";
	private final Map<String, String> params;
	
	public ConfigurationGenerale(){
		
		this.params = new HashMap<>();
		
		try{
			URL url = new URL(urlServ+":"+portServ+"/admin/confGenerale.txt");
			InputStream is = url.openStream();
			BufferedReader r = new BufferedReader(new InputStreamReader(is));
			
			String line = r.readLine();
			while (line != null) {
				
				if(!line.startsWith("#") && !line.equals("")){
					System.out.println("Configuration trouvée : " + line);
					String[] str = line.split("=");
					params.put(str[0], str[1]);
				}
				line = r.readLine();
			}
			
		}catch (Exception e){
			
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
