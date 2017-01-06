/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.dao;

import com.beecode.toolvendor.model.Visit;
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
public class VisitDAO {
    private static String TAG = VisitDAO.class.getName();
    private static String fieldId = "id";
    private static String queryDelete = "delete from Visit where id = :id";
    
    public void add(Visit entity) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        add(session, entity);
        tx.commit();
        session.close();
    }
    
    private void add(Session session, Visit entity) {
        Visit object = entity;
        session.save(object);
    }
    
    public void update(Visit entity) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        update(session, entity);
        tx.commit();
        session.close();
    }
    
    private void update(Session session, Visit entity) {
        Visit object = entity;
        session.update(object);
    }
    
    /*
        Method Name: getAll
        Description: obtiene todos los registros de la tabla visit
        Parameters:  visitId (int)
        Return: List<VisitPicture>
    */
    public List getAllByCompany(Integer companyId) {
        Session session = SessionUtil.getSession();
        List result = null;
        try {
           Criteria cr = session.createCriteria(Visit.class);
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
    Return: List<Visit>
    */
    public List getAllByUser(int userId) {
        Session session = SessionUtil.getSession();
        List result = null;
        try{
           Criteria cr = session.createCriteria(Visit.class);
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
    Method Name: getAllByCustomer
    Description: obtiene todos los registros de la tabla visit relacionados con un customerId
    Parameters:  customerId (int)
    Return: List<Visit>
    */
    public List getAllByCustomer(int customerId) {
        Session session = SessionUtil.getSession();
        List result = null;
        try{
           Criteria cr = session.createCriteria(Visit.class);
           // Add restriction.
           cr.add(Restrictions.eq("customerId", customerId));
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
    Return: Visit
    */
    public Visit findById(Integer id, Integer companyId) {
        Session session = SessionUtil.getSession();
        Visit result = null;
        try{
           Criteria cr = session.createCriteria(Visit.class);
           // Add restriction.
           cr.add(Restrictions.eq("id", id));
           cr.add(Restrictions.eq("companyId", companyId));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (Visit) cr.uniqueResult();
           if ( result!=null )
                System.out.print("Visit: " + result.getId()); 
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
