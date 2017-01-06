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
    public static final String SERVIDOR_SMTP = "outlook.office365.com"; //or simply "localhost"
    public static final int SMTP_PORT = 587; //25 or 587"
    public static final String SMTP_AUTH_USER = "toolvendor@beecode.co";
    public static final String SMTP_AUTH_PWD  = "123456";
    public static final String EMAIL_FROM = "toolvendor@beecode.co";
    public static final String SUBJECT_FROM_PERSONAL = "TOOLVENDOR APP";
    
    public void sendEmailText(String toEmail, String emailSubject, String emailBody);
    
    public void sendEmailHTML(String toEmail, String emailSubject, String emailBody);
    
}
