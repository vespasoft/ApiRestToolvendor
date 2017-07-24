/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.dao;

import com.beecode.toolvendor.model.Order;
import com.beecode.toolvendor.util.SessionUtil1;
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
public class OrderDAO {
    private static String TAG = OrderDAO.class.getName();
    private static String fieldId = "id";
    private static String queryDelete = "delete from Order where id = :id";
    
    public void add(Order entity) {
        Session session = SessionUtil1.getSession();
        Transaction tx = session.beginTransaction();
        add(session, entity);
        tx.commit();
        session.close();
    }
    
    private void add(Session session, Order entity) {
        Order object = entity;
        session.save(object);
    }
    
    public void update(Order entity) {
        Session session = SessionUtil1.getSession();
        Transaction tx = session.beginTransaction();
        update(session, entity);
        tx.commit();
        session.close();
    }
    
    private void update(Session session, Order entity) {
        Order object = entity;
        session.update(object);
    }
    
    /*
        Method Name: getAll
        Description: obtiene todos los registros de la tabla visit
        Parameters:  visitId (int)
        Return: List<OrderPicture>
    */
    public List getAllByCompany(Integer companyId) {
        Session session = SessionUtil1.getSession();
        List result = null;
        try {
           Criteria cr = session.createCriteria(Order.class);
           cr.add(Restrictions.eq("companyId", companyId));
           //crit.add(Restrictions.like("id", id+"%"));
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
    
    /*
    Method Name: getAllByUser
    Description: obtiene todos los registros de la tabla visit relacionados con un userId
    Parameters:  userId (int)
    Return: List<Order>
    */
    public List getAllByUser(int userId) {
        Session session = SessionUtil1.getSession();
        List result = null;
        try{
           Criteria cr = session.createCriteria(Order.class);
           // Add restriction.
           cr.add(Restrictions.eq("user_id", userId));
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
    Method Name: getAllByCustomer
    Description: obtiene todos los registros de la tabla visit relacionados con un customerId
    Parameters:  customerId (int)
    Return: List<Order>
    */
    public List getAllByCustomer(int customerId) {
        Session session = SessionUtil1.getSession();
        List result = null;
        try{
           Criteria cr = session.createCriteria(Order.class);
           // Add restriction.
           cr.add(Restrictions.eq("customer_id", customerId));
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
    Description: encuentra un registro en la tabla visit a partir de un id
    Parameters:  id (int)
    Return: Order
    */
    public Order findById(Integer id, Integer companyId) {
        Session session = SessionUtil1.getSession();
        Order result = null;
        try{
           Criteria cr = session.createCriteria(Order.class);
           // Add restriction.
           cr.add(Restrictions.eq("id", id));
           cr.add(Restrictions.eq("companyId", companyId));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (Order) cr.uniqueResult();
           if ( result!=null )
                System.out.print("Order: " + result.getId()); 
        }catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        }finally {
           session.close(); 
        }
        return result;
    }
    
    public int delete(int id) {
        Session session = SessionUtil1.getSession();
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
