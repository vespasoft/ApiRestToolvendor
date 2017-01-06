/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.dao;

import com.beecode.toolvendor.model.UserType;
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
public class UserTypeDAO {
    SessionUtil sessionutil = new SessionUtil();
    private static String TAG = UserDAO.class.getName();
    private static String fieldId = "id";
    private static String queryAll = "from UserType";
    private static String queryDelete = "delete from UserType where id = :id";
    
    public void add(UserType entity) {
        Session session = sessionutil.getSession();
        Transaction tx = session.beginTransaction();
        add(session, entity);
        tx.commit();
        session.close();
    }
    
    private void add(Session session, UserType entity) {
        UserType object = entity;
        session.save(object);
    }
    
    public void update(UserType entity) {
        Session session = sessionutil.getSession();
        Transaction tx = session.beginTransaction();
        update(session, entity);
        tx.commit();
        session.close();
    }
    
    private void update(Session session, UserType entity) {
        UserType object = entity;
        session.update(object);
    }
    
    public List getAllByCompany(Integer companyId) {
        Session session = sessionutil.getSession();
        List result = null;
        try{
           Criteria cr = session.createCriteria(UserType.class);
           cr.add(Restrictions.eq("companyId", companyId));
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
    
    /*public UserType findById(int id) {
        Session session = sessionutil.getSession();
        UserType result = null;
        try {
           Criteria cr = session.createCriteria(UserType.class);
           // Add restriction.
           cr.add(Restrictions.eq(fieldId, id));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (UserType) cr.uniqueResult();
           if ( result!=null )
                System.out.print("User Type: " + result.getDescription()); 
        } catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        } finally {
           session.close(); 
        }
        return result;
    }*/
    
    public UserType findById(int id, int companyId) {
        Session session = sessionutil.getSession();
        UserType result = null;
        try {
           Criteria cr = session.createCriteria(UserType.class);
           // Add restriction.
           cr.add(Restrictions.eq(fieldId, id));
           cr.add(Restrictions.eq("companyId", companyId));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (UserType) cr.uniqueResult();
           if ( result!=null )
                System.out.print("User Type: " + result.getDescription()); 
        } catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        } finally {
           session.close(); 
        }
        return result;
    }
    
    public UserType findByDescription(String descrip, int companyId) {
        Session session = sessionutil.getSession();
        UserType result = null;
        try{
           Criteria cr = session.createCriteria(UserType.class);
           // Add restriction.
           cr.add(Restrictions.eq("description", descrip));
           cr.add(Restrictions.eq("companyId", companyId));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (UserType) cr.uniqueResult();

           if ( result!=null )
                System.out.print("User Type: " + result.getDescription());  
        }catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        }finally {
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
