/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.dao;

import com.beecode.toolvendor.model.VisitType;
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
public class VisitTypeDAO {
    private final String TAG = VisitTypeDAO.class.getName();
    private final String fieldId = "id";
    private final String fieldName = "name";
    private final String queryDelete = "delete from VisitType where id = :id";
    
    public void add(VisitType entity) {
        Session session = SessionUtil1.getSession();
        Transaction tx = session.beginTransaction();
        add(session, entity);
        tx.commit();
        session.close();
    }
    
    private void add(Session session, VisitType entity) {
        VisitType object = entity;
        session.save(object);
    }
    
    public void update(VisitType entity) {
        Session session = SessionUtil1.getSession();
        Transaction tx = session.beginTransaction();
        update(session, entity);
        tx.commit();
        session.close();
    }
    
    private void update(Session session, VisitType entity) {
        VisitType object = entity;
        session.update(object);
    }
    
    public List getAllByCompany(Integer companyId) {
        Session session = SessionUtil1.getSession();
        List result = null;
        try{
           Criteria cr = session.createCriteria(VisitType.class);
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
    
    public VisitType findById(Integer id, int companyId) {
        Session session = SessionUtil1.getSession();
        VisitType result = null;
        try{
           Criteria cr = session.createCriteria(VisitType.class);
           // Add restriction.
           cr.add(Restrictions.eq(fieldId, id));
           cr.add(Restrictions.eq("companyId", companyId));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (VisitType) cr.uniqueResult();
           if ( result!=null )
                System.out.print("VisitType: " + result.getName()); 
        }catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        }finally {
           session.close(); 
        }
        return result;
    }
    
    public VisitType findByName(String name, int companyId) {
        Session session = SessionUtil1.getSession();
        VisitType result = null;
        try{
           Criteria cr = session.createCriteria(VisitType.class);
           // Add restriction.
           cr.add(Restrictions.eq(fieldName, name));
           cr.add(Restrictions.eq("companyId", companyId));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (VisitType) cr.uniqueResult();

           if ( result!=null )
                System.out.print("VisitType: " + result.getName());  
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
