/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.dao;

import com.beecode.toolvendor.model.Country;
import com.beecode.toolvendor.util.SessionUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author luisvespa
 */
public class CountryDAO {
    private static String TAG = UserDAO.class.getName();
    private static String fieldId = "id";
    private static String queryId = "from Country WHERE id= :id";
    private static String queryName = "from Country WHERE name= :name";
    private static String queryAll = "from Country";
    private static String queryDelete = "delete from Country where id = :id";
    
    public void add(Country entity) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        add(session, entity);
        tx.commit();
        session.close();
    }
    
    private void add(Session session, Country entity) {
        Country object = entity;
        session.save(object);
    }
    
    public void update(Country entity) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        update(session, entity);
        tx.commit();
        session.close();
    }
    
    private void update(Session session, Country entity) {
        Country object = entity;
        session.update(object);
    }
    
    public List getAll() {
        Session session = SessionUtil.getSession();
        List result = null;
        try{
           Criteria cr = session.createCriteria(Country.class);
           //cr.add(Restrictions.eq("company_id", companyId));
           result = cr.list();
           if ( result!=null )
                System.out.print("filas obtenidas: " + result.size()); 
        }catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        }finally {
           session.close(); 
        }
        return result;
    }
    
    public Country findById(int id) {
        Session session = SessionUtil.getSession();
        Country result = null;
        try{
           Criteria cr = session.createCriteria(Country.class);
           // Add restriction.
           cr.add(Restrictions.eq(fieldId, id));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (Country) cr.uniqueResult();
           if ( result!=null )
                System.out.print("Cuty: " + result.getName()); 
        }catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        }finally {
           session.close(); 
        }
        return result;
    }
    
    public Country findByName(String name) {
        Session session = SessionUtil.getSession();
        Country result = null;
        try{
           Criteria cr = session.createCriteria(Country.class);
           // Add restriction.
           cr.add(Restrictions.eq("name", name));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (Country) cr.uniqueResult();

           if ( result!=null )
                System.out.print("Country: " + result.getName());  
        }catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        }finally {
           session.close(); 
        }
            
        return result;
    }
    
    public int delete(int id) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(queryDelete);
        query.setInteger(fieldId, id);
        int rowCount = query.executeUpdate();
        System.out.println(TAG+". filas afectadas: " + rowCount);
        tx.commit();
        session.close();
        return rowCount;
    }
}
