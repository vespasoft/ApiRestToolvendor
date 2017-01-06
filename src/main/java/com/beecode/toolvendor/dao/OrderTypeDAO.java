/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.dao;

import com.beecode.toolvendor.model.OrderType;
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
public class OrderTypeDAO {
    private final String TAG = OrderTypeDAO.class.getName();
    private final String fieldId = "id";
    private final String fieldName = "name";
    private final String queryDelete = "delete from OrderType where id = :id";
    
    public void add(OrderType entity) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        add(session, entity);
        tx.commit();
        session.close();
    }
    
    private void add(Session session, OrderType entity) {
        OrderType object = entity;
        session.save(object);
    }
    
    public void update(OrderType entity) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        update(session, entity);
        tx.commit();
        session.close();
    }
    
    private void update(Session session, OrderType entity) {
        OrderType object = entity;
        session.update(object);
    }
    
    public List getAll() {
        Session session = SessionUtil.getSession();
        List result = null;
        try{
           Criteria cr = session.createCriteria(OrderType.class);
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
    
    public OrderType findById(Integer id) {
        Session session = SessionUtil.getSession();
        OrderType result = null;
        try{
           Criteria cr = session.createCriteria(OrderType.class);
           // Add restriction.
           cr.add(Restrictions.eq(fieldId, id));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (OrderType) cr.uniqueResult();
           if ( result!=null )
                System.out.print("OrderType: " + result.getName()); 
        }catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        }finally {
           session.close(); 
        }
        return result;
    }
    
    public OrderType findByName(String name) {
        Session session = SessionUtil.getSession();
        OrderType result = null;
        try{
           Criteria cr = session.createCriteria(OrderType.class);
           // Add restriction.
           cr.add(Restrictions.eq(fieldName, name));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (OrderType) cr.uniqueResult();

           if ( result!=null )
                System.out.print("OrderType: " + result.getName());  
        } catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        } finally {
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
