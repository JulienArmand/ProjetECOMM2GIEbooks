package tools;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EnvoiMail {

	private static final String	address				= "futurabooksnoreply@gmail.com";

	private static final String	HTTP_PROXY_HOST		= "http.proxyHost";
	private static final String	HTTP_PROXY_PORT		= "http.proxyPort";
	private static final String	HTTPS_PROXY_HOST	= "https.proxyHost";
	private static final String	HTTPS_PROXY_PORT	= "https.proxyPort";

	private static final String	PROXY_HOST			= "www-cache.ujf-grenoble.fr";
	private static final String	PROXY_PORT			= "3128";

	public static void envoyer_email(String mail, String sujet, String msg) {

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
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));

			message.setSubject(sujet);
			message.setText(msg);

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
