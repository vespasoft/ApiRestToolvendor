/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.dao;

import com.beecode.toolvendor.model.Call;
import com.beecode.toolvendor.model.User;
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
public class CallDAO {
    private static String TAG = UserDAO.class.getName();
    private static String fieldId = "id";
    private static String queryId = "from Call WHERE id= :id";
    private static String queryPhone = "from Call WHERE phone= :phone";
    private static String queryAll = "from Call  where user_id = :user_id";
    private static String queryDelete = "delete from Call where id = :id";
    
    public void add(Call entity) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        add(session, entity);
        tx.commit();
        session.close();
    }
    
    private void add(Session session, Call entity) {
        Call object = entity;
        session.save(object);
    }
    
    public void update(Call entity) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        update(session, entity);
        tx.commit();
        session.close();
    }
    
    private void update(Session session, Call entity) {
        Call object = entity;
        session.update(object);
    }
    
    /*
    Method Name: getAllByUser
    Description: obtiene todos los registros de la tabla Call relacionados con un userId
    Parameters:  userId (int)
    Return: List<Call>
    */
    public List getAllByUser(Integer userId) {
        Session session = SessionUtil.getSession();
        List result = null;
        try{
           Criteria cr = session.createCriteria(Call.class);
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
    
    public List getAllByPhone(String phone) {
        Session session = SessionUtil.getSession();
        List result = null;
        try{
           Criteria cr = session.createCriteria(Call.class);
           // Add restriction.
           cr.add(Restrictions.eq("phone", phone));
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
    
    public Call findById(int id) {
        Session session = SessionUtil.getSession();
        Call result = null;
        try{
           Criteria cr = session.createCriteria(Call.class);
           // Add restriction.
           cr.add(Restrictions.eq(fieldId, id));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (Call) cr.uniqueResult();
           if ( result!=null )
                System.out.print("Call findById: " + result.getPhone()); 
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
