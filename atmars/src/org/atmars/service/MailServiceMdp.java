package org.atmars.service;

import java.util.Properties;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.mail.Authenticator;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailServiceMdp implements MessageListener {

	private String name = "atmars.com";
	private String pwd = "rjxyei2012";
	private String from = "atmars.com@gmail.com";
	@Override
	public void onMessage(Message message) {
		
		try {
			MapMessage mapMessage = (MapMessage)message;
			String to = mapMessage.getString("To");
			String subject = mapMessage.getString("Subject");
			String content = mapMessage.getString("Content");
			
			Properties props = System.getProperties();
			props.setProperty("mail.transport.protocol", "smtp");
			props.setProperty("mail.smtp.host", "smtp.gmail.com");
			props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.setProperty("mail.smtp.socketFactory.fallback", "false");
			props.setProperty("mail.smtp.port", "465");
			props.setProperty("mail.smtp.socketFactory.port", "465");
			props.setProperty("mail.smtp.auth", "true");
			
			Authenticator auth = new MailAuthenticator(name, pwd);
			Session session = Session.getDefaultInstance(props, auth);
			
			javax.mail.Message jMessage = new MimeMessage(session);
			jMessage.setFrom(new InternetAddress(from));
			jMessage.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
			jMessage.setSubject(subject);
			jMessage.setContent(content, "text/html;CHARSET=utf8");
			Transport.send(jMessage);
		} catch(Exception e){}
		
	}

}
