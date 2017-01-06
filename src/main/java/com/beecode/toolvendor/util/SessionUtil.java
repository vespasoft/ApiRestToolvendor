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

/**
 *
 * @author luisvespa
 */
public class SessionUtil {
    private static SessionUtil  instance= new SessionUtil();
    private SessionFactory sessionFactory;
    
    public static SessionUtil getInstance() {
        return instance;
    }
    
    public static Session getSession() {
        Session session = getInstance().sessionFactory.openSession();
        return session;
    }
    
    private SessionUtil() {
        /*Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        sessionFactory = configuration.buildSessionFactory();*/
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().
        applySettings(configuration.getProperties());
        sessionFactory= configuration.buildSessionFactory(builder.build());

    }
    
}
