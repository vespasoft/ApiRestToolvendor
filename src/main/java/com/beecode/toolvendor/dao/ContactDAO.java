/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.dao;

import com.beecode.toolvendor.model.Contact;
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
public class ContactDAO {
    
    private static String TAG = UserDAO.class.getName();
    private static String fieldId = "id";
    private static String queryId = "from Contact WHERE id= :id";
    private static String queryName = "from Contact WHERE name= :name";
    private static String queryAll = "from Contact WHERE userId= :userId";
    private static String queryDelete = "delete from Contact where id = :id";
    
    public void add(Contact entity) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        add(session, entity);
        tx.commit();
        session.close();
    }
    
    private void add(Session session, Contact entity) {
        Contact object = entity;
        session.save(object);
    }
    
    public void update(Contact entity) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        update(session, entity);
        tx.commit();
        session.close();
    }
    
    private void update(Session session, Contact entity) {
        Contact object = entity;
        session.update(object);
    }
    
    /*
        Method Name: getAll
        Description: obtiene todos los registros de la tabla Contact relacionados con un userId
        Parameters:  userId (int)
        Return: List<Call>
    */
    public List getAllByUser(Integer userId) {
        Session session = SessionUtil.getSession();
        List result = null;
        try{
           Criteria cr = session.createCriteria(Contact.class);
           // Add restriction.
           cr.add(Restrictions.eq("userId", userId));
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
    
    public Contact findById(int id) {
        Session session = SessionUtil.getSession();
        Contact result = null;
        try{
           Criteria cr = session.createCriteria(Contact.class);
           // Add restriction.
           cr.add(Restrictions.eq(fieldId, id));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (Contact) cr.uniqueResult();
           if ( result!=null )
                System.out.print("Contact findById: " + result.getPhone()); 
        }catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        }finally {
           session.close(); 
        }
        return result;
    }
    
    public Contact findByPhone(Integer userId, String phone) {
        Session session = SessionUtil.getSession();
        Contact result = null;
        try{
           Criteria cr = session.createCriteria(Contact.class);
           // Add restriction.
           cr.add(Restrictions.eq("userId", userId));
           cr.add(Restrictions.eq("phone", phone));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (Contact) cr.uniqueResult();
           if ( result!=null )
                System.out.print("Contact findById: " + result.getPhone()); 
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
