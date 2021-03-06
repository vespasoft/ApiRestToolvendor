/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.dao;

import com.beecode.toolvendor.model.Category;
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
public class CategoryDAO {
    private static String TAG = CategoryDAO.class.getName();
    private static String fieldId = "id";
    private static String queryId = "from Category WHERE id= :id";
    private static String queryName = "from Category WHERE category= :category";
    private static String queryAll = "from Category";
    private static String queryDelete = "delete from Category where id = :id";
    
    public void add(Category entity) {
        Session session = SessionUtil1.getSession();
        Transaction tx = session.beginTransaction();
        add(session, entity);
        tx.commit();
        session.close();
    }
    
    private void add(Session session, Category entity) {
        Category object = entity;
        session.save(object);
    }
    
    public void update(Category entity) {
        Session session = SessionUtil1.getSession();
        Transaction tx = session.beginTransaction();
        update(session, entity);
        tx.commit();
        session.close();
    }
    
    private void update(Session session, Category entity) {
        Category object = entity;
        session.update(object);
    }
    
    public List getAllByCompany(Integer companyId) {
        Session session = SessionUtil1.getSession();
        List result = null;
        try{
           Criteria cr = session.createCriteria(Category.class);
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
    
    public Category findById(int id, int companyId) {
        Session session = SessionUtil1.getSession();
        Category result = null;
        Transaction tx = null;
        try{
           tx = session.beginTransaction();
           Criteria cr = session.createCriteria(Category.class);
           // Add restriction.
           cr.add(Restrictions.eq(fieldId, id));
           cr.add(Restrictions.eq("companyId", companyId));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (Category) cr.uniqueResult();
           
           tx.commit();
        }catch (HibernateException e) {
           if (tx!=null) tx.rollback();
           System.out.println("Error DAO: "+ e.getMessage());
        }finally {
           session.close(); 
        }
            
        return result;
    }
    
    public Category findByName(String name, int companyId) {
        Session session = SessionUtil1.getSession();
        Category result = null;
        Transaction tx = null;
        try{
           tx = session.beginTransaction();
           Criteria cr = session.createCriteria(Category.class);
           // Add restriction.
           cr.add(Restrictions.eq("category", name));
           cr.add(Restrictions.eq("companyId", companyId));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (Category) cr.uniqueResult();
           
           tx.commit();
        }catch (HibernateException e) {
           if (tx!=null) tx.rollback();
           System.out.println("Error DAO: "+ e.getMessage());
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
