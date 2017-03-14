/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.thread;

import com.beecode.toolvendor.model.Customer;
import com.beecode.toolvendor.model.Visit;
import com.beecode.toolvendor.service.EmailServiceImpl;

/**
 *
 * @author luisvespa
 */
public class SendEmailVisitThread extends Thread {

    private Customer cstmr;
    private Visit visit;

    public Customer getCstmr() {
        return cstmr;
    }

    public void setCstmr(Customer cstmr) {
        this.cstmr = cstmr;
    }

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    public SendEmailVisitThread(Customer cstmr, Visit visit) {
        this.cstmr = cstmr;
        this.visit = visit;
    }
    
    @Override
    public void run() {
        // se instancia la clase controladora de correos
        EmailServiceImpl emailserv = new EmailServiceImpl();
        // se valida que el object user no sea nulo
        if ( cstmr!=null )
            // se envia el correo de notificación de la visita programada al usuario
            emailserv.SendEmailVisit(cstmr, visit);
    }
    
    
    
}
