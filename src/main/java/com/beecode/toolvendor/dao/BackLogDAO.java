/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.dao;

import com.beecode.toolvendor.model.BackLog;
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
public class BackLogDAO {
    private static String TAG = BackLogDAO.class.getName();
    private static String fieldId = "id";
    private static String queryId = "from BackLog WHERE id= :id";
    private static String queryDelete = "delete from BackLog where id = :id";
    
    public void add(BackLog entity) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        add(session, entity);
        tx.commit();
        session.close();
    }
    
    private void add(Session session, BackLog entity) {
        BackLog object = entity;
        session.save(object);
    }
    
    public void update(BackLog entity) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        update(session, entity);
        tx.commit();
        session.close();
    }
    
    private void update(Session session, BackLog entity) {
        BackLog object = entity;
        session.update(object);
    }
    
    public List getAllByCompany(Integer companyId) {
        Session session = SessionUtil.getSession();
        List result = null;
        try{
           Criteria cr = session.createCriteria(BackLog.class);
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
    
    public List getAllByUser(Integer userId) {
        Session session = SessionUtil.getSession();
        List result = null;
        try{
           Criteria cr = session.createCriteria(BackLog.class);
           cr.add(Restrictions.eq("userId", userId));
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
    
    public BackLog findById(int id) {
        Session session = SessionUtil.getSession();
        BackLog result = null;
        Transaction tx = null;
        try{
           tx = session.beginTransaction();
           Criteria cr = session.createCriteria(BackLog.class);
           // Add restriction.
           cr.add(Restrictions.eq("id", id));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (BackLog) cr.uniqueResult();
           
           tx.commit();
        }catch (HibernateException e) {
           if (tx!=null) tx.rollback();
           System.out.println("Error DAO: "+ e.getMessage());
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
