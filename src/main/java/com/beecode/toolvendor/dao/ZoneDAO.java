/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.dao;

import com.beecode.toolvendor.model.Zone;
import com.beecode.toolvendor.util.SessionUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author luisvespa
 */
public class ZoneDAO {
    private static String TAG = ZoneDAO.class.getName();
    private static String fieldId = "id";
    private static String table = "Zone";
    private static String queryId = "from "+table+" WHERE id= :id";
    private static String queryDescription = "from "+table+" WHERE description= :description";
    private static String queryAll = "from "+table;
    private static String queryDelete = "delete from "+table+" where id = :id";
    
    public void add(Zone entity) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        add(session, entity);
        tx.commit();
        session.close();
    }
    
    private void add(Session session, Zone entity) {
        Zone object = entity;
        session.save(object);
    }
    
    public void update(Zone entity) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        update(session, entity);
        tx.commit();
        session.close();
    }
    
    private void update(Session session, Zone entity) {
        Zone object = entity;
        session.update(object);
    }
    
    public List getAllByCompany(Integer companyId) {
        Session session = SessionUtil.getSession();
        List result = null;
        try{
           Criteria cr = session.createCriteria(Zone.class);
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
           Criteria cr = session.createCriteria(Zone.class)
           .setFetchMode("user_zones", FetchMode.JOIN)
           .setFetchMode("user", FetchMode.JOIN)     
           .add(Restrictions.eq("user_id", userId))
           .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
           result = cr.list();
           if ( result!=null ) {
               System.out.print(result.toString());
               System.out.print("filas obtenidas: " + result.size()); 
           } 
        }catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        }finally {
           session.close(); 
        }
        return result;
    }
    
    public Zone findById(int id, int companyId) {
        Session session = SessionUtil.getSession();
        Zone result = null;
        try {
           Criteria cr = session.createCriteria(Zone.class);
           // Add restriction.
           cr.add(Restrictions.eq(fieldId, id));
           cr.add(Restrictions.eq("companyId", companyId));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (Zone) cr.uniqueResult();
           if ( result!=null )
                System.out.print("Zone: " + result.getDescription()); 
        } catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        } finally {
           session.close(); 
        }
        return result;
    }
    
    public Zone findByName(String name, int companyId) {
        Session session = SessionUtil.getSession();
        Zone result = null;
        try {
           Criteria cr = session.createCriteria(Zone.class);
           // Add restriction.
           cr.add(Restrictions.eq("description", name));
           cr.add(Restrictions.eq("companyId", companyId));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (Zone) cr.uniqueResult();
           if ( result!=null )
                System.out.print("Zone: " + result.getDescription()); 
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
