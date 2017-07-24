/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.dao;

import com.beecode.toolvendor.model.Permission;
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
public class PermissionDAO {
    private final String TAG = PermissionDAO.class.getName();
    private final String fieldId = "id";
    private final static String queryId = "from Permission WHERE id= :id";
    private final static String queryDuplex = "from Permission WHERE groupId= :groupId AND moduleId= :moduleId";
    private final static String queryAll = "from Permission WHERE groupId= :groupId";
    private final String queryDelete = "delete from Permission where id = :id";
    
    public void add(Permission entity) {
        Session session = SessionUtil1.getSession();
        Transaction tx = session.beginTransaction();
        add(session, entity);
        tx.commit();
        session.close();
    }
    
    private void add(Session session, Permission entity) {
        Permission object = entity;
        session.save(object);
    }
    
    public void update(Permission entity) {
        Session session = SessionUtil1.getSession();
        Transaction tx = session.beginTransaction();
        update(session, entity);
        tx.commit();
        session.close();
    }
    
    private void update(Session session, Permission entity) {
        Permission object = entity;
        session.update(object);
    }
    
    public List getAllByGroup(Integer groupId) {
        Session session = SessionUtil1.getSession();
        List result = null;
        try{
           Criteria cr = session.createCriteria(Permission.class);
           cr.add(Restrictions.eq("group_id", groupId));
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
    
    public Permission findById(int id) {
        Session session = SessionUtil1.getSession();
        Permission result = null;
        try {
           Criteria cr = session.createCriteria(Permission.class);
           // Add restriction.
           cr.add(Restrictions.eq(fieldId, id));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (Permission) cr.uniqueResult();
           if ( result!=null )
                System.out.print("Premission group id: " + result.getGroupId()); 
        } catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        } finally {
           session.close(); 
        }
        return result;
    }
    
    public Permission findByGroupModule(int groupId, int moduleId) {
        Session session = SessionUtil1.getSession();
        Permission result = null;
        try {
           Criteria cr = session.createCriteria(Permission.class);
           // Add restriction.
           cr.add(Restrictions.eq("group_id", groupId));
           cr.add(Restrictions.eq("module_id", moduleId));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (Permission) cr.uniqueResult();
           if ( result!=null )
                System.out.print("Premission group id: " + result.getGroupId()); 
        } catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        } finally {
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
    
    /*public List getAll(int groupId) {
        Session session = SessionUtil1.getSession();
        Query query = session.createQuery(queryAll);
        query.setInteger("groupId", groupId);
        List result = query.list();
        return result;
    }
    
    public List findById(int id) {
        Session session = SessionUtil1.getSession();
        Query query = session.createQuery(queryId);
        query.setInteger(fieldId, id);
        List result = query.list();
        return result;
    }
    
    public List findByModuleId(int groupId, int moduleId) {
        Session session = SessionUtil1.getSession();
        Query query = session.createQuery(queryDuplex);
        query.setInteger("groupId", groupId);
        query.setInteger("moduleId", moduleId);
        List result = query.list();
        return result;
    }*/
    
}
