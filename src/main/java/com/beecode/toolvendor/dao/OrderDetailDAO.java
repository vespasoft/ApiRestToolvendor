/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.dao;

import com.beecode.toolvendor.model.OrderDetail;
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
public class OrderDetailDAO {
    SessionUtil sessionutil = new SessionUtil();
    private static String TAG = OrderDetailDAO.class.getName();
    private static String fieldId = "id";
    private static String queryDelete = "delete from OrderDetail where id = :id";
    
    public void add(OrderDetail entity) {
        Session session = sessionutil.getSession();
        Transaction tx = session.beginTransaction();
        add(session, entity);
        tx.commit();
        session.close();
    }
    
    private void add(Session session, OrderDetail entity) {
        OrderDetail object = entity;
        session.save(object);
    }
    
    public void update(OrderDetail entity) {
        Session session = sessionutil.getSession();
        Transaction tx = session.beginTransaction();
        update(session, entity);
        tx.commit();
        session.close();
    }
    
    private void update(Session session, OrderDetail entity) {
        OrderDetail object = entity;
        session.update(object);
    }
    
    /*
    Method Name: getAllByOrder
    Description: obtiene todos los registros relacionados con una order.
    Parameters:  orderId (int)
    Return: List<OrderDetail>
    */
    public List getAllByOrder(int orderId) {
        Session session = sessionutil.getSession();
        List result = null;
        try{
           Criteria cr = session.createCriteria(OrderDetail.class);
           // Add restriction.
           cr.add(Restrictions.eq("order_id", orderId));
           //crit.add(Restrictions.like("id", id+"%"));
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
    
    /*
    Method Name: findById
    Description: obtiene un objeto tipo OrderDetail a partir de un Id.
    Parameters:  id (int)
    Return: OrderDetail
    */
    public OrderDetail findById(Integer id) {
        Session session = sessionutil.getSession();
        OrderDetail result = null;
        try{
           Criteria cr = session.createCriteria(OrderDetail.class);
           // Add restriction.
           cr.add(Restrictions.eq("id", id));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (OrderDetail) cr.uniqueResult();
           if ( result!=null )
                System.out.print("OrderDetail: " + result.getId()); 
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
