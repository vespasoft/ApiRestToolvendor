/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.thread;

import com.beecode.toolvendor.model.Visit;
import com.beecode.toolvendor.service.EmailServiceImpl;

/**
 *
 * @author luisvespa
 */
public class SendEmailVisitThread extends Thread {

    private String to;
    private Visit visit;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    public SendEmailVisitThread(String to, Visit visit) {
        this.to = to;
        this.visit = visit;
    }
    
    @Override
    public void run() {
        // se instancia la clase controladora de correos
        EmailServiceImpl emailserv = new EmailServiceImpl();
        // se envia el correo de notificación de la visita programada al usuario
        emailserv.SendEmailVisit(to, visit);
    }
    
    
}
