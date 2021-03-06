/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author luisvespa
 */
public class SessionUtil {
    
    public static Session getSession() {
        Configuration cfg;
        ServiceRegistry serviceregistry;
        SessionFactory sessionFactory;
        
        try {
            cfg = new Configuration().configure("hibernate.cfg.xml");
            serviceregistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
            sessionFactory= cfg.buildSessionFactory(serviceregistry);
            return sessionFactory.getCurrentSession();
            
        } catch (Exception e) {
            return null;
        }
    }
    
}
