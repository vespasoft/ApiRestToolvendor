/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.dao;

import com.beecode.toolvendor.model.Company;
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
public class CompanyDAO {
    private static String TAG = CompanyDAO.class.getName();
    private static String fieldId = "id";
    private static String queryDelete = "delete from Company where id = :id";
    
    public void add(Company entity) {
        Session session = SessionUtil1.getSession();
        Transaction tx = session.beginTransaction();
        add(session, entity);
        tx.commit();
        session.close();
    }
    
    private void add(Session session, Company entity) {
        Company object = entity;
        session.save(object);
    }
    
    public void update(Company entity) {
        Session session = SessionUtil1.getSession();
        Transaction tx = session.beginTransaction();
        update(session, entity);
        tx.commit();
        session.close();
    }
    
    private void update(Session session, Company entity) {
        Company object = entity;
        session.update(object);
    }
    
    public List getAll() {
        Session session = SessionUtil1.getSession();
        List result = null;
        try{
           Criteria cr = session.createCriteria(Company.class);
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
    
    public Company findById(int id) {
        Session session = SessionUtil1.getSession();
        Company result = null;
        try{
           Criteria cr = session.createCriteria(Company.class);
           // Add restriction.
           cr.add(Restrictions.eq("id", id));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (Company) cr.uniqueResult();
           if ( result!=null )
                System.out.print("Company Name sin Transaction: " + result.getCompany()); 
        }catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        }finally {
           session.close(); 
        }
        return result;
    }
    
    /* Method: findByEmail
       @param: String field, String value
       Este metodo permite hacer una busqueda por cualquier campo String de la tabla.
    */
    public Company findByEmail(String email) {
        Session session = SessionUtil1.getSession();
        Company result = null;
        try{
           Criteria cr = session.createCriteria(Company.class);
           // Add restriction.
           cr.add(Restrictions.eq("email", email));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (Company) cr.uniqueResult();

           if ( result!=null )
                System.out.print("Company Search: " + result.getEmail());  
        }catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        }finally {
           session.close(); 
        }
            
        return result;
    }
    
    /* Method: findByStringField
       @param: String field, String value
       Este metodo permite hacer una busqueda por cualquier campo String de la tabla.
    */
    public Company findByStringField(String field, String value) {
        Session session = SessionUtil1.getSession();
        Company result = null;
        try{
           Criteria cr = session.createCriteria(Company.class);
           // Add restriction.
           cr.add(Restrictions.eq(field, value));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (Company) cr.uniqueResult();

           if ( result!=null )
                System.out.print("Company Search: " + result.getEmail());  
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
