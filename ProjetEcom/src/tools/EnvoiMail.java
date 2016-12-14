package tools;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author ochiers
 * Class gérant l'envoi d'un mail
 */
public class EnvoiMail {

	private static final String	ADDRESS				= "futurabooksnoreply@gmail.com";

	private static final String	HTTP_PROXY_HOST		= "http.proxyHost";
	private static final String	HTTP_PROXY_PORT		= "http.proxyPort";
	private static final String	HTTPS_PROXY_HOST	= "https.proxyHost";
	private static final String	HTTPS_PROXY_PORT	= "https.proxyPort";

	private static final String	PROXY_HOST			= "www-cache.ujf-grenoble.fr";
	private static final String	PROXY_PORT			= "3128";
	
	private static final String	MDP			= "aqwzsx123";

	/**
	 * Envoi d'un mail
	 * @param mail Destinataire
	 * @param sujet Sujet du mail
	 * @param msg Corps du mail
	 */
	public static void envoyerEmail(String mail, String sujet, String msg) {

		final String username = ADDRESS;
		final String password = MDP;

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
		Logger logger = Logger.getAnonymousLogger();
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(ADDRESS));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));

			message.setSubject(sujet);
			message.setText(msg);

			Transport.send(message);

		} catch (MessagingException e) {
			logger.log(Level.FINE, "an exception was thrown	", e);
		}
	}
}
