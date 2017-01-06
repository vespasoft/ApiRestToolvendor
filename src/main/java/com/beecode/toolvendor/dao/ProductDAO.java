/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.dao;

import com.beecode.toolvendor.model.Product;
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
public class ProductDAO {
    private static final String TAG = ProductDAO.class.getName();
    private static final String fieldId = "id";
    private static final String queryAll = "from Product";
    private static final String queryDelete = "delete from Product where id = :id";
    
    public void add(Product entity) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        add(session, entity);
        tx.commit();
        session.close();
    }
    
    private void add(Session session, Product entity) {
        Product object = entity;
        session.save(object);
    }
    
    public void update(Product entity) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        update(session, entity);
        tx.commit();
        session.close();
    }
    
    private void update(Session session, Product entity) {
        Product object = entity;
        session.update(object);
    }
    
    /*
        Method Name: getAllByCompany
        Description: obtiene todos los registros de la tabla product relacionados con una compañia
        Parameters:  company (Company)
        Return: List<Product>
    */
    public List getAllByCompany(Integer companyId) {
        Session session = SessionUtil.getSession();
        List result = null;
        try{
           Criteria cr = session.createCriteria(Product.class);
           // Add restriction.
           cr.add(Restrictions.eq("companyId", companyId));
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
    
    public Product findById(Integer id) {
        Session session = SessionUtil.getSession();
        Product result = null;
        try{
           Criteria cr = session.createCriteria(Product.class);
           // Add restriction.
           cr.add(Restrictions.eq("id", id));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (Product) cr.uniqueResult();
           if ( result!=null )
                System.out.print("Product Name sin Transaction: " + result.getName()); 
        }catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        }finally {
           session.close(); 
        }
        return result;
    }
    
    public Product findById(Integer id, int companyId) {
        Session session = SessionUtil.getSession();
        Product result = null;
        try{
           Criteria cr = session.createCriteria(Product.class);
           // Add restriction.
           cr.add(Restrictions.eq("id", id));
           cr.add(Restrictions.eq("companyId", companyId));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (Product) cr.uniqueResult();
           if ( result!=null )
                System.out.print("Product Name sin Transaction: " + result.getName()); 
        }catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        }finally {
           session.close(); 
        }
        return result;
    }
    
    public Product findByName(String name, int companyId) {
        Session session = SessionUtil.getSession();
        Product result = null;
        try{
           Criteria cr = session.createCriteria(Product.class);
           // Add restriction.
           cr.add(Restrictions.eq("name", name));
           cr.add(Restrictions.eq("companyId", companyId));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (Product) cr.uniqueResult();

           if ( result!=null )
                System.out.print("Product Name: " + result.getName());  
        }catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        }finally {
           session.close(); 
        }
            
        return result;
    }
    
    public Product findByBarcode(String barcode, int companyId) {
        Session session = SessionUtil.getSession();
        Product result = null;
        try{
           Criteria cr = session.createCriteria(Product.class);
           // Add restriction.
           cr.add(Restrictions.eq("barcode", barcode));
           cr.add(Restrictions.eq("companyId", companyId));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (Product) cr.uniqueResult();
           if ( result!=null )
                System.out.print("Product Name: " + result.getName());  
        }catch (HibernateException e) {
           if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        }finally {
           session.close(); 
        }
            
        return result;
    }
    
    public int delete(Integer id) {
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
