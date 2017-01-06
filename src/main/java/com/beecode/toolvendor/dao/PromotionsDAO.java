/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.dao;

import com.beecode.toolvendor.model.Promotions;
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
public class PromotionsDAO {
    private static String TAG = PromotionsDAO.class.getName();
    private static String fieldId = "id";
    private static String queryId = "from Promotions WHERE id= :id";
    private static String queryAll = "from Promotions p WHERE p.product.company_id= :company_id";
    private static String queryDelete = "delete from Promotions where id = :id";
    
    public void add(Promotions entity) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        add(session, entity);
        tx.commit();
        session.close();
    }
    
    private void add(Session session, Promotions entity) {
        Promotions object = entity;
        session.save(object);
    }
    
    public void update(Promotions entity) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        update(session, entity);
        tx.commit();
        session.close();
    }
    
    private void update(Session session, Promotions entity) {
        Promotions object = entity;
        session.update(object);
    }
    
    public List getAllByCompany(Integer companyId) {
        List result = null;
        Session session = SessionUtil.getSession();
        try{
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery(queryAll);
            query.setInteger("company_id", companyId);
            result = query.list();
            if (result!=null)
                System.out.println(TAG+". filas obtenidas: " + result.size());
            tx.commit();
        }catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        }finally {
           session.close(); 
        }
        return result;
    }
    
    public Promotions findById(int id) {
        Session session = SessionUtil.getSession();
        Promotions result = null;
        Transaction tx = null;
        try{
           tx = session.beginTransaction();
           Criteria cr = session.createCriteria(Promotions.class);
           // Add restriction.
           cr.add(Restrictions.eq(fieldId, id));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (Promotions) cr.uniqueResult();
           
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
