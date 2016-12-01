package Tools;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
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
	 */
	public static void sauvegarderImage(String urlImage,  int destWidth, int destHeight, String destPath) throws IOException {
		
		BufferedImage bImage;
		throw new IOException();
		
		/*urlImage = "http://www.arbres.org/images/arbre-petition.jpg";
		System.err.println("etape -1 ok " + urlImage);
		URL url = new URL(urlImage);
		
		System.err.println("etape 0 ok");
		ReadableByteChannel rbc = Channels.newChannel(url.openStream());
		System.err.println("etape 1 ok");
		File f = File.createTempFile("temp", ".png");
		System.err.println("etape 2 ok");
		FileOutputStream fos = new FileOutputStream(f);

		System.err.println("etape 3 ok");
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

		System.err.println("YOLOOO ok");
		bImage = ImageIO.read(f);    
		
		System.err.println("Lecture ok");
		//créer l'image de destination
        GraphicsConfiguration configuration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        BufferedImage bImageNew = configuration.createCompatibleImage(destWidth, destHeight);
        Graphics2D graphics = bImageNew.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        //dessiner l'image de destination
        graphics.drawImage(bImage, 0, 0, destWidth, destHeight, 0, 0, bImage.getWidth(), bImage.getHeight(), null);
        graphics.dispose();
 
		System.err.println("Redimensionnement ok");
        File outputfile = new File(destPath);
        ImageIO.write(bImageNew, "png", outputfile);
        f.delete();
		System.err.println("Ecriture ok");*/
    }
	
}
