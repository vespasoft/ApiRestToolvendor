/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.interfaces;

import com.beecode.toolvendor.model.Customer;
import com.beecode.toolvendor.model.Order;
import com.beecode.toolvendor.model.User;
import com.beecode.toolvendor.model.Visit;

/**
 *
 * @author luisvespa
 */
public interface EmailService {
    
    public void SendEmailWellcome(User user);
    
    public void SendEmailForgot(User user);
    
    public void SendEmailProgramVisit(User user, Visit visit);
    
    public void SendEmailVisit(String to, Visit visit);
    
    public void SendEmailOrder(Customer cstmr, Order order);
    
}
