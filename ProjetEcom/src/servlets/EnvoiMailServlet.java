package servlets;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EnvoiMailServlet extends HttpServlet {

	private static final long	serialVersionUID	= 6907236103034815181L;

	public static final String	address				= "futurabooksnoreply@gmail.com";

	private static final String	HTTP_PROXY_HOST		= "http.proxyHost";
	private static final String	HTTP_PROXY_PORT		= "http.proxyPort";
	private static final String	HTTPS_PROXY_HOST	= "https.proxyHost";
	private static final String	HTTPS_PROXY_PORT	= "https.proxyPort";

	private static final String	PROXY_HOST			= "www-cache.ujf-grenoble.fr";
	private static final String	PROXY_PORT			= "3128";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Envoie mail test");
		envoyer_email();
		System.out.println("Envoie mail test fini");
	}

	public void envoyer_email() {

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
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("sebastien.ochier@gmail.com"));

			message.setSubject("Testing Subject");
			message.setText("Test envoi," + "\n\n Ceci est un test !");
			System.out.println("Envoie");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
