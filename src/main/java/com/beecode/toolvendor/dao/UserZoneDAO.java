/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.dao;

import com.beecode.toolvendor.model.UserZone;
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
public class UserZoneDAO {
    SessionUtil sessionutil = new SessionUtil();
    private static String TAG = UserDAO.class.getName();
    private static String fieldId = "id";
    private static String queryId = "from UserZone WHERE Id= :Id";
    private static String queryDuplex = "from UserZone WHERE userId= :userId AND zoneId= :zoneId";
    private static String queryAll = "from UserZone WHERE userId= :userId";
    private static String queryDelete = "delete from UserZone where id = :id";
    
    public void add(UserZone entity) {
        Session session = sessionutil.getSession();
        Transaction tx = session.beginTransaction();
        add(session, entity);
        tx.commit();
        session.close();
    }
    
    private void add(Session session, UserZone entity) {
        UserZone object = entity;
        session.save(object);
    }
    
    public void update(UserZone entity) {
        Session session = sessionutil.getSession();
        Transaction tx = session.beginTransaction();
        update(session, entity);
        tx.commit();
        session.close();
    }
    
    private void update(Session session, UserZone entity) {
        UserZone object = entity;
        session.update(object);
    }
    
    public List getAllByUser(Integer userId) {
        Session session = sessionutil.getSession();
        List result = null;
        try{
           Criteria cr = session.createCriteria(UserZone.class)
           .add(Restrictions.eq("user_id", userId));
           result = cr.list();
           if ( result!=null ) {
               System.out.print(result.toString());
               System.out.print("filas obtenidas: " + result.size()); 
           } 
        }catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        }finally {
           session.close(); 
        }
        return result;
    }
    
    public UserZone findById(int id) {
        Session session = sessionutil.getSession();
        UserZone result = null;
        try {
           Criteria cr = session.createCriteria(UserZone.class);
           // Add restriction.
           cr.add(Restrictions.eq(fieldId, id));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (UserZone) cr.uniqueResult();
           if ( result!=null )
                System.out.print("UserZone: " + result.getId()); 
        } catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        } finally {
           session.close(); 
        }
        return result;
    }
    
    public UserZone findByUserZone(Integer userId, Integer zoneId) {
        Session session = sessionutil.getSession();
        UserZone result = null;
        try {
           Criteria cr = session.createCriteria(UserZone.class);
           // Add restriction.
           cr.add(Restrictions.eq("user_id", userId));
           cr.add(Restrictions.eq("zone_id", zoneId));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (UserZone) cr.uniqueResult();
           if ( result!=null )
                System.out.print("UserZone: " + result.getId()); 
        } catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        } finally {
           session.close(); 
        }
        return result;
    }
    
    public int delete(int id) {
        Session session = sessionutil.getSession();
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
