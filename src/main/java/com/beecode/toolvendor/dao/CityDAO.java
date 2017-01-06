/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.dao;

import com.beecode.toolvendor.model.City;
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
public class CityDAO {
    private static String TAG = UserDAO.class.getName();
    private static String fieldId = "id";
    private static String queryId = "from City WHERE id= :id";
    private static String queryName = "from City WHERE city= :city";
    private static String queryAll = "from City";
    private static String queryDelete = "delete from City where id = :id";
    
    public void add(City entity) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        add(session, entity);
        tx.commit();
        session.close();
    }
    
    private void add(Session session, City entity) {
        City object = entity;
        session.save(object);
    }
    
    public void update(City entity) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        update(session, entity);
        tx.commit();
        session.close();
    }
    
    private void update(Session session, City entity) {
        City object = entity;
        session.update(object);
    }
    
    public List getAllByCountry(Country country) {
        Session session = SessionUtil.getSession();
        List result = null;
        try {
           Criteria cr = session.createCriteria(City.class);
           cr.add(Restrictions.eq("country", country));
           result = cr.list();
           if ( result!=null )
                System.out.print("filas obtenidas: " + result.size()); 
        } catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        } finally {
           session.close(); 
        }
        return result;
    }
    
    public City findById(int id) {
        Session session = SessionUtil.getSession();
        City result = null;
        try{
           Criteria cr = session.createCriteria(City.class);
           // Add restriction.
           cr.add(Restrictions.eq(fieldId, id));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (City) cr.uniqueResult();
           if ( result!=null )
                System.out.print("Cuty: " + result.getCity()); 
        }catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        }finally {
           session.close(); 
        }
        return result;
    }
    
    public City findByName(String name) {
        Session session = SessionUtil.getSession();
        City result = null;
        try{
           Criteria cr = session.createCriteria(City.class);
           // Add restriction.
           cr.add(Restrictions.eq("city", name));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (City) cr.uniqueResult();

           if ( result!=null )
                System.out.print("City: " + result.getCity());  
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
