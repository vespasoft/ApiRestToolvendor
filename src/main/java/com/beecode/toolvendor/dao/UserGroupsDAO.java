/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.dao;

import com.beecode.toolvendor.model.Groups;
import com.beecode.toolvendor.model.UserGroups;
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
public class UserGroupsDAO {
    private static String TAG = UserDAO.class.getName();
    private static String fieldId = "id";
    private static String queryId = "from UserGroups WHERE id= :id";
    private static String queryDuplex = "from UserGroups WHERE userId= :userId AND groupId= :groupId";
    private static String queryAll = "from UserGroups WHERE userId= :userId";
    private static String queryDelete = "delete from UserGroups where id = :id";
    
    public void add(UserGroups entity) {
        Session session = SessionUtil1.getSession();
        Transaction tx = session.beginTransaction();
        add(session, entity);
        tx.commit();
        session.close();
    }
    
    private void add(Session session, UserGroups entity) {
        UserGroups object = entity;
        session.save(object);
    }
    
    public void update(UserGroups entity) {
        Session session = SessionUtil1.getSession();
        Transaction tx = session.beginTransaction();
        update(session, entity);
        tx.commit();
        session.close();
    }
    
    private void update(Session session, UserGroups entity) {
        UserGroups object = entity;
        session.update(object);
    }
    
    public List getAllByUser(Integer userId) {
        Session session = SessionUtil1.getSession();
        List result = null;
        try{
           Criteria cr = session.createCriteria(UserGroups.class)
           .add(Restrictions.eq("user_id", userId));
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
    
    public UserGroups findById(int id) {
        Session session = SessionUtil1.getSession();
        UserGroups result = null;
        try {
           Criteria cr = session.createCriteria(UserGroups.class);
           // Add restriction.
           cr.add(Restrictions.eq(fieldId, id));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (UserGroups) cr.uniqueResult();
           if ( result!=null )
                System.out.print("UserGroup: " + result.getId()); 
        } catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        } finally {
           session.close(); 
        }
        return result;
    }
    
    public UserGroups findByUserGroups(Integer userId, Integer groupId) {
        Session session = SessionUtil1.getSession();
        UserGroups result = null;
        try {
           Criteria cr = session.createCriteria(Groups.class);
           // Add restriction.
           cr.add(Restrictions.eq("user_id", userId));
           cr.add(Restrictions.eq("group_id", groupId));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (UserGroups) cr.uniqueResult();
           if ( result!=null )
                System.out.print("UserGroup: " + result.getId()); 
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
}
