/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.interfaces;

/**
 *
 * @author luisvespa
 */
public interface SendEmail {
    public static final String TAG = "SendMail";
    public static final String SERVIDOR_SMTP = "smtp.1and1.com"; //or simply "localhost"
    public static final String SMTP_PORT = "587"; //25 or 587"
    public static final String SMTP_PORT_SSL = "993"; //25 or 587"
    public static final String SMTP_AUTH_USER = "admin@beecode.co";
    public static final String SMTP_AUTH_PWD  = "Portuguesa8-";
    public static final String EMAIL_FROM = "admin@beecode.co";
    public static final String SUBJECT_FROM_PERSONAL = "TOOLVENDOR APP";
    
    public void SendMailSSL(String toEmail, String emailSubject, String emailBody, String content);
    
    public void SendMailTSL(String toEmail, String emailSubject, String emailBody, String content);
    
}
