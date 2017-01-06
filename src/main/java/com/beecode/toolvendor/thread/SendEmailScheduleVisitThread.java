/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.thread;

import com.beecode.toolvendor.model.User;
import com.beecode.toolvendor.model.Visit;
import com.beecode.toolvendor.service.EmailServiceImpl;

/**
 *
 * @author luisvespa
 */
public class SendEmailScheduleVisitThread extends Thread {

    private User user;
    private Visit visit;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    public SendEmailScheduleVisitThread(User user, Visit visit) {
        this.user = user;
        this.visit = visit;
    }
    
    @Override
    public void run() {
        // se instancia la clase controladora de correos
        EmailServiceImpl emailserv = new EmailServiceImpl();
        // se valida que el object user no sea nulo
        if ( user!=null )
            // se envia el correo de notificación de la visita programada al usuario
            emailserv.SendEmailProgramVisit(user, visit);
    }
    
    
    
}
