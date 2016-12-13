package restBeans;

import java.util.Properties;
import java.util.Iterator;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Client;
import model.Commande;
import model.Vente;

@Stateless
@Named
@Path("/EnvoiMailBeans")
public class EnvoiMailBeans {

	public static final String	address				= "futurabooksnoreply@gmail.com";

	private static final String	HTTP_PROXY_HOST		= "http.proxyHost";
	private static final String	HTTP_PROXY_PORT		= "http.proxyPort";
	private static final String	HTTPS_PROXY_HOST	= "https.proxyHost";
	private static final String	HTTPS_PROXY_PORT	= "https.proxyPort";

	private static final String	PROXY_HOST			= "www-cache.ujf-grenoble.fr";
	private static final String	PROXY_PORT			= "3128";

	@GET
	@Path("/confirmation")
	@Produces(MediaType.APPLICATION_XML)
	public void envoyerMailConfirmationCommande(@PathParam("cmd") Commande cmd) {

		Client c = cmd.getLeClient();

		StringBuilder strBuild = new StringBuilder();
		strBuild.append("Confirmation de votre commande\n");
		strBuild.append("Bonjour, " + c.getPseudo() + "\n");
		strBuild.append("Vous avez effectué une commande sur notre site le " + cmd.getDateDeVente() + " et nous vous en remercions.\n");
		strBuild.append("Dont voici le détail : \n");
		Iterator<Vente> it = (cmd.getLesVentes()).iterator();
		while (it.hasNext()) {
			Vente v = (Vente) it.next();
			strBuild.append(v.getLivre().getTitre() + " au prix de " + v.getPrix() + "\n");
		}
		strBuild.append("\nNous vous remercions de votre confiance et bonne lecture.\nA très bientot sur notre site ! \n L'équipe FuturaBooks.");

		envoyer_email(c.getEmail(), "Confirmation commande", strBuild.toString());

	}

	public void envoyer_email(String email, String sujet, String msg) {

		final String username = address;
		final String password = "aqwzsx123";

		System.setProperty(HTTP_PROXY_HOST, PROXY_HOST);
		System.setProperty(HTTP_PROXY_PORT, PROXY_PORT);
		System.setProperty(HTTPS_PROXY_HOST, PROXY_HOST);
		System.setProperty(HTTPS_PROXY_PORT, PROXY_PORT);

		Properties props = System.getProperties();
		props.setProperty("mail.smtp.host", "localhost");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(address));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));

			message.setSubject(sujet);
			message.setText(msg);

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
