package com.mail;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {
	public static void main(String[] args) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.qq.com");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		try {
			PopupAuthenticator auth = new PopupAuthenticator();
			Session session = Session.getInstance(props, auth);
			session.setDebug(true);
			MimeMessage message = new MimeMessage(session);
			Address addressFrom = new InternetAddress(PopupAuthenticator.mailuser + "@qq.com", "ÀÃÃ±×Ó£¡");
			Address addressTo = new InternetAddress("732793220@qq.com", "LUO");
			message.setText("¹þ¹þ£¡ÀÃÃ±×Ó£¡");
			message.setSubject("Test sent Mail by javaMail");
			message.setFrom(addressFrom);
			message.addRecipient(Message.RecipientType.TO, addressTo);
			message.saveChanges();
			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.qq.com", PopupAuthenticator.mailuser, PopupAuthenticator.password);
			transport.send(message);
			transport.close();
			System.out.println("sent suc");
		} catch (Exception e) {
			System.out.println(e.toString());
			System.out.println("sent fail");
		}
	}
}

class PopupAuthenticator extends Authenticator {
	public static final String mailuser = "9140008";
	public static final String password = "vgjzjfucsieebceb";

	public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(mailuser, password);
	}
}