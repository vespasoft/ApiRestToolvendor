/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.thread;

import com.beecode.toolvendor.model.User;
import com.beecode.toolvendor.service.EmailServiceImpl;

/**
 *
 * @author luisvespa
 */
public class SendEmailForgotThread extends Thread {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SendEmailForgotThread(User user) {
        this.user = user;
    }
    
    @Override
    public void run() {
        // se instancia la clase controladora de correos
        EmailServiceImpl emailserv = new EmailServiceImpl();
        // se valida que el object user no sea nulo
        if ( user!=null )
            // se envia el correo de recuperación de contraseña al usuario
            emailserv.SendEmailForgot(user);
    }
    
    
    
}
