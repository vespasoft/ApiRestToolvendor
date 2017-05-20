/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.dao;

import com.beecode.toolvendor.model.Customer;
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
public class CustomerDAO {
    
    private static String TAG = CustomerDAO.class.getName();
    private static String fieldId = "id";
    private static String queryId = "from Customer WHERE id= :id";
    private static String queryName = "from Customer WHERE name= :name";
    private static String queryEmail = "from Customer WHERE email= :email";
    private static String queryAll = "from Customer";
    private static String queryAllByUser = "from Customer WHERE user_id= :user_id";
    private static String queryDelete = "delete from Customer where id = :id";
    
    public boolean add(Customer entity) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        boolean result = false;
        try {
            add(session, entity);
            tx.commit();
            result = true;
        } catch ( Exception e ) {
            System.out.println("Error in DAO customer add: " + e.getMessage());
            result = false;
        }finally {
           session.close(); 
        }
        return result;
    }
    
    private void add(Session session, Customer entity) {
        Customer object = entity;
        session.save(object);
    }
    
    public boolean update(Customer entity) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        boolean result = false;
        try {
            update(session, entity);
            tx.commit();
            result = true;
        } catch ( Exception e ) {
            System.out.println("Error in DAO customer add: " + e.getMessage());
            result = false;
        }finally {
           session.close(); 
        }
        return result;
    }
    
    private void update(Session session, Customer entity) {
        Customer object = entity;
        session.update(object);
    }
    
    /*
        Method Name: getAllByUser
        Description: obtiene todos los registros de la tabla visit relacionados con un userId
        Parameters:  userId (int)
        Return: List<Visit>
    */
    public List getAllByUser(Integer userId) {
        Session session = SessionUtil.getSession();
        List result = null;
        try{
           Criteria cr = session.createCriteria(Customer.class);
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
    
    /*
        Method Name: getAllByCompany
        Description: obtiene todos los registros de la tabla customer relacionados con un companyId
        Parameters:  companyId (int)
        Return: List<Customer>
    */
    public List getAllByCompany(Integer companyId) {
        Session session = SessionUtil.getSession();
        List result = null;
        try{
           Criteria cr = session.createCriteria(Customer.class);
           // Add restriction.
           cr.add(Restrictions.eq("companyId", companyId));
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
    
    public Customer findById(int id, int companyId) {
        Session session = SessionUtil.getSession();
        Customer result = null;
        try{
           Criteria cr = session.createCriteria(Customer.class);
           // Add restriction.
           cr.add(Restrictions.eq("id", id));
           cr.add(Restrictions.eq("companyId", companyId));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (Customer) cr.uniqueResult();
           if ( result!=null )
                System.out.print("Customer findById: " + result.getCompanyName()); 
        }catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        }finally {
           session.close(); 
        }
        return result;
    }
    
    public Customer findByCompanyName(String companyname, int companyId) {
        Session session = SessionUtil.getSession();
        Customer result = null;
        try{
           Criteria cr = session.createCriteria(Customer.class);
           // Add restriction.
           cr.add(Restrictions.eq("companyName", companyname));
           cr.add(Restrictions.eq("companyId", companyId));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (Customer) cr.uniqueResult();
           if ( result!=null )
                System.out.print("Customer findByName: " + result.getCompanyName()); 
        }catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        }finally {
           session.close(); 
        }
        return result;
    }
    
    public Customer findByEmail(String email, int companyId) {
        Session session = SessionUtil.getSession();
        Customer result = null;
        try{
           Criteria cr = session.createCriteria(Customer.class);
           // Add restriction.
           cr.add(Restrictions.eq("contactEmail", email));
           cr.add(Restrictions.eq("companyId", companyId));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (Customer) cr.uniqueResult();
           if ( result!=null )
                System.out.print("Customer findByEmail: " + result.getCompanyName()); 
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
        try {
            Query query = session.createQuery(queryDelete);
            query.setInteger(fieldId, id);
            int rowCount = query.executeUpdate();
            System.out.println(TAG+". filas afectadas: " + rowCount);
            tx.commit();
            session.close();
            return rowCount;
        } catch ( Exception e ) {
            System.out.println("Error in DAO customer add: " + e.getMessage());
            return 0;
        }
        
    }
}
