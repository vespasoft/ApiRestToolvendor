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
public class SessionUtil1 {
    
    private static SessionUtil1 instance= new SessionUtil1();
    private SessionFactory sessionFactory;
    
    public static SessionUtil1 getInstance() {
        return instance;
    }
    
    public static Session getSession() {
        Session session = getInstance().sessionFactory.openSession();
        return session;
    }
    
    private SessionUtil1() {
        /*Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        sessionFactory = configuration.buildSessionFactory();*/
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().
        applySettings(configuration.getProperties());
        sessionFactory= configuration.buildSessionFactory(builder.build());

    }
    /*public static Session getSession() {
        Configuration cfg;
        ServiceRegistry serviceregistry;
        SessionFactory sessionFactory;
        
        try {
            cfg = new Configuration().configure();
            serviceregistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
            sessionFactory= cfg.buildSessionFactory(serviceregistry);
            return sessionFactory.getCurrentSession();
            
        } catch (Exception e) {
            return null;
        }
    }*/
    
}
