/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.email;

import static com.beecode.toolvendor.interfaces.SendEmail.EMAIL_FROM;
import static com.beecode.toolvendor.interfaces.SendEmail.SMTP_AUTH_PWD;
import static com.beecode.toolvendor.interfaces.SendEmail.SMTP_AUTH_USER;
import static com.beecode.toolvendor.interfaces.SendEmail.SUBJECT_FROM_PERSONAL;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author luisvespa
 */
public class SendEmail implements com.beecode.toolvendor.interfaces.SendEmail  {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendEmailOffice365.class);

    private String to = "vespaluis@gmail.com";
    private String subject = "TOOLVENDOR APP";
    private String messageContent = "Teste de Mensagem";
    private String messageBody = "Teste de Mensagem HTML";
    
    public SendEmail() {
    }
    

    @Override
    public void SendMailSSL(String toEmail, String emailSubject, String emailBody, String content) {
        this.to = toEmail;
        this.subject = emailSubject;
        this.messageContent = emailBody;
        
        Properties props = new Properties();
        props.put("mail.smtp.host", SERVIDOR_SMTP);
        props.put("mail.smtp.socketFactory.port", SMTP_PORT_SSL);
        props.put("mail.smtp.socketFactory.class",
                        "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", SMTP_PORT_SSL);

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(SMTP_AUTH_USER, SMTP_AUTH_PWD);
                        }
                });

        try {

                final Message message = new MimeMessage(session);
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
                try {
                    message.setFrom(new InternetAddress(EMAIL_FROM, SUBJECT_FROM_PERSONAL));
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("Error al agregar InternetAddress: " + e.getMessage(), e);
                }
                message.setSubject(subject);
                if (content.equalsIgnoreCase("text")) message.setText(messageContent);
                else if (content.equalsIgnoreCase("text/html")) message.setContent(messageContent, "text/html");
                message.setSentDate(new Date());
                Transport.send(message);

                System.out.println("SendMailSSL is done.");

        } catch (MessagingException ex) {
                LOGGER.error("Error al enviar mensagem: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void SendMailTSL(String toEmail, String emailSubject, String emailBody, String content) {
        this.to = toEmail;
        this.subject = emailSubject;
        this.messageContent = emailBody;
        
        final Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SERVIDOR_SMTP);
        props.put("mail.smtp.port", SMTP_PORT);

        final Session session = Session.getInstance(props, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_AUTH_USER, SMTP_AUTH_PWD);
            }

        });

        try {
            final Message message = new MimeMessage(session);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            try {
                message.setFrom(new InternetAddress(EMAIL_FROM, SUBJECT_FROM_PERSONAL));
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("Error al agregar InternetAddress: " + e.getMessage(), e);
            }
            message.setSubject(subject);
            if (content.equalsIgnoreCase("text")) message.setText(messageContent);
            else if (content.equalsIgnoreCase("text/html")) message.setContent(messageContent, "text/html");
            message.setSentDate(new Date());
            Transport.send(message);
            
            System.out.println("SendMailTSL is done.");
        } catch (final MessagingException ex) {
            LOGGER.error("Error al enviar mensagem: " + ex.getMessage(), ex);
        }
    }
    
}
