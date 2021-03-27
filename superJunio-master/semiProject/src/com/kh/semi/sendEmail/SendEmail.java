package com.kh.semi.sendEmail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
 public static void main(String[] args) {

  final String user   = "pjh87973158@gmail.com";
  final String password  = "pjh1218714";

  String to = null;
  String mTitle = null;
  String mText = null;
  
  // Get the session object
  Properties props = new Properties();
  props.put("mail.smtp.host", "smtp.gmail.com");
  props.put("mail.smtp.port", 465);
  props.put("mail.smtp.auth", "true");
  props.put("mail.smtp.ssl.enable", "true");
  props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

  Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
   protected PasswordAuthentication getPasswordAuthentication() {
    return new PasswordAuthentication(user, password);
   }
  });

  // Compose the message
  try {
   MimeMessage message = new MimeMessage(session);
   message.setFrom(new InternetAddress(user));
   message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

   // Subject
   message.setSubject(mTitle);
   
   // Text
   message.setText(mText);

   // send the message
   Transport.send(message);
   System.out.println("message sent successfully...");

  } catch (AddressException e) {
   e.printStackTrace();
  } catch(MessagingException e) {
	  e.printStackTrace();
  }
 	
 }
}