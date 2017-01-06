/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.thread;

import com.beecode.toolvendor.model.Customer;
import com.beecode.toolvendor.model.Order;
import com.beecode.toolvendor.model.User;
import com.beecode.toolvendor.model.Visit;
import com.beecode.toolvendor.service.EmailServiceImpl;

/**
 *
 * @author luisvespa
 */
public class SendEmailOrderThread extends Thread {

    private Customer cstmr;
    private Order order;

    public Customer getCstmr() {
        return cstmr;
    }

    public void setCstmr(Customer cstmr) {
        this.cstmr = cstmr;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public SendEmailOrderThread(Customer cstmr, Order order) {
        this.cstmr = cstmr;
        this.order = order;
    }
    
    @Override
    public void run() {
        // se instancia la clase controladora de correos
        EmailServiceImpl emailserv = new EmailServiceImpl();
        // se valida que el object user no sea nulo
        if ( cstmr!=null )
            // se envia el correo de la orden con el pdf adjunto al cliente.
            emailserv.SendEmailOrder(cstmr, order);
    }
    
    
    
}
