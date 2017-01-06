/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.dao;

import com.beecode.toolvendor.model.ProductType;
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
public class ProductTypeDAO {
    private static String TAG = ProductTypeDAO.class.getName();
    private static String fieldId = "id";
    private static String queryId = "from ProductType WHERE id= :id";
    private static String queryName = "from ProductType WHERE description= :description";
    private static String queryAll = "from ProductType";
    private static String queryDelete = "delete from ProductType where id = :id";
    
    public void add(ProductType entity) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        add(session, entity);
        tx.commit();
        session.close();
    }
    
    private void add(Session session, ProductType entity) {
        ProductType object = entity;
        session.save(object);
    }
    
    public void update(ProductType entity) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        update(session, entity);
        tx.commit();
        session.close();
    }
    
    private void update(Session session, ProductType entity) {
        ProductType object = entity;
        session.update(object);
    }
    
    public List getAllByCompany(Integer companyId) {
        Session session = SessionUtil.getSession();
        List result = null;
        try{
           Criteria cr = session.createCriteria(ProductType.class);
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
    
    public ProductType findById(int id, int companyId) {
        Session session = SessionUtil.getSession();
        ProductType result = null;
        Transaction tx = null;
        try{
           tx = session.beginTransaction();
           Criteria cr = session.createCriteria(ProductType.class);
           // Add restriction.
           cr.add(Restrictions.eq(fieldId, id));
           cr.add(Restrictions.eq("companyId", companyId));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (ProductType) cr.uniqueResult();
           
           tx.commit();
        }catch (HibernateException e) {
           if (tx!=null) tx.rollback();
           System.out.println("Error DAO: "+ e.getMessage());
        }finally {
           session.close(); 
        }
            
        return result;
    }
    
    public ProductType findByName(String name, int companyId) {
        Session session = SessionUtil.getSession();
        ProductType result = null;
        Transaction tx = null;
        try{
           tx = session.beginTransaction();
           Criteria cr = session.createCriteria(ProductType.class);
           // Add restriction.
           cr.add(Restrictions.eq("description", name));
           cr.add(Restrictions.eq("companyId", companyId));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (ProductType) cr.uniqueResult();
           
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
