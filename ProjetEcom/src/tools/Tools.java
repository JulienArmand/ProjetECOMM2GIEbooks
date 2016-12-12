package tools;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.Normalizer;

import javax.imageio.ImageIO;

public class Tools {

	public static String normalisationString(String s) {
		return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").replaceAll("\"", "")
				.replaceAll("'", "");

	}
	
	/**
	 * Télécharge l'image de urlImage, la redimensionne suivant destWidth et destHeight, et la sauvegarde sous destPath.
	 * @param urlImage L'url de l'image a télécharger
	 * @param destWidth La taille en largeur de l'image finale
	 * @param destHeight La taille en hauteur de l'image finale		
	 * @param destPath	Le chemin sous lequel sera sauvegardé l'image
	 * @throws IOException
	 * @throws URISyntaxException 
	 */
	public static void sauvegarderImage(String urlImage,  int destWidth, int destHeight, String destPath) throws IOException, URISyntaxException {
		
		System.setProperty("http.proxyHost", "www-cache.ujf-grenoble.fr");
		System.setProperty("http.proxyPort", "3128");
		System.setProperty("https.proxyHost", "www-cache.ujf-grenoble.fr");
		System.setProperty("https.proxyPort", "3128");
		
		BufferedImage bImage;		
		URL url = new URL(urlImage);
		bImage = ImageIO.read(url);    
		
		//créer l'image de destination
        GraphicsConfiguration configuration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        BufferedImage bImageNew = configuration.createCompatibleImage(destWidth, destHeight);
        Graphics2D graphics = bImageNew.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        //dessiner l'image de destination
        graphics.drawImage(bImage, 0, 0, destWidth, destHeight, 0, 0, bImage.getWidth(), bImage.getHeight(), null);
        graphics.dispose();
 
        
		File outputfile = new File(destPath);
        if(!outputfile.exists())
        	outputfile.createNewFile();
        ImageIO.write(bImageNew, "png", outputfile);
    }
	
}
