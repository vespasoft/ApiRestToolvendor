/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.dao;

import com.beecode.toolvendor.model.Groups;
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
public class GroupsDAO {
    private final static String TAG = UserDAO.class.getName();
    private final static String fieldId = "id";
    private final static String queryId = "from Groups WHERE id= :id";
    private final static String queryName = "from Groups WHERE name= :name";
    private final static String queryDelete = "delete from Groups where id = :id";
    private final static String queryAllByUser = "select distinct from Groups";
    
    public void add(Groups entity) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        add(session, entity);
        tx.commit();
        session.close();
    }
    
    private void add(Session session, Groups entity) {
        Groups object = entity;
        session.save(object);
    }
    
    public void update(Groups entity) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        update(session, entity);
        tx.commit();
        session.close();
    }
    
    private void update(Session session, Groups entity) {
        Groups object = entity;
        session.update(object);
    }
    
    public List getAllByCompany(Integer companyId) {
        Session session = SessionUtil.getSession();
        List result = null;
        try{
           Criteria cr = session.createCriteria(Groups.class);
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
           Criteria cr = session.createCriteria(Groups.class)
           .setFetchMode("user_groups", FetchMode.JOIN)
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
    
    public Groups findById(int id, int companyId) {
        Session session = SessionUtil.getSession();
        Groups result = null;
        try {
           Criteria cr = session.createCriteria(Groups.class);
           // Add restriction.
           cr.add(Restrictions.eq(fieldId, id));
           cr.add(Restrictions.eq("companyId", companyId));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (Groups) cr.uniqueResult();
           if ( result!=null )
                System.out.print("Groups: " + result.getDescription()); 
        } catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        } finally {
           session.close(); 
        }
        return result;
    }
    
    public Groups findByName(String name, int companyId) {
        Session session = SessionUtil.getSession();
        Groups result = null;
        try {
           Criteria cr = session.createCriteria(Groups.class);
           // Add restriction.
           cr.add(Restrictions.eq("name", name));
           cr.add(Restrictions.eq("companyId", companyId));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (Groups) cr.uniqueResult();
           if ( result!=null )
                System.out.print("Groups: " + result.getDescription()); 
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
