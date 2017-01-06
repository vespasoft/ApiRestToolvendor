/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.email;

import com.beecode.toolvendor.interfaces.SendEmail;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;

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
public class SendEmailOffice365 implements SendEmail {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SendEmailOffice365.class);

    private String to = "vespaluis@gmail.com";
    private String subject = "TOOLVENDOR APP";
    private String messageContent = "Teste de Mensagem";
    private String messageBody = "Teste de Mensagem HTML";

    public SendEmailOffice365() {
    }

    @Override
    public void sendEmailText(String toEmail, String emailSubject, String emailContent) {
        this.to = toEmail;
        this.subject = emailSubject;
        this.messageContent = emailContent;

        final Session session = Session.getInstance(this.getEmailProperties(), new Authenticator() {

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
            message.setText(messageContent);
            message.setSentDate(new Date());
            Transport.send(message);
        } catch (final MessagingException ex) {
            LOGGER.error("Error al enviar mensagem: " + ex.getMessage(), ex);
        }
    }
    
    @Override
    public void sendEmailHTML(String toEmail, String emailSubject, String emailBody) {
        this.to = toEmail;
        this.subject = emailSubject;
        this.messageBody = emailBody;

        final Session session = Session.getInstance(this.getEmailProperties(), new Authenticator() {

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
            message.setContent(messageBody, "text/html");
            message.setSentDate(new Date());
            Transport.send(message);
        } catch (final MessagingException ex) {
            LOGGER.error("Error al enviar mensagem: " + ex.getMessage(), ex);
        }
    }

    private Properties getEmailProperties() {
        final Properties config = new Properties();
        config.put("mail.smtp.auth", "true");
        config.put("mail.transport.protocol", "smtp");
        config.put("mail.smtp.starttls.enable", "true");
        config.put("mail.smtp.host", SERVIDOR_SMTP);
        config.put("mail.smtp.port", "587");
        return config;
    }

    
}
