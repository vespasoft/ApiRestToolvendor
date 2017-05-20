/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.dao;

import com.beecode.toolvendor.model.Company;
import com.beecode.toolvendor.model.User;
import com.beecode.toolvendor.util.SessionUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

/**
 *
 * @author luisvespa
 */
public class UserDAO {
    private static String TAG = UserDAO.class.getName();
    private static String fieldId = "id";
    private static String queryAuth = "from User where email = :email AND password = :password";
    private static String queryDelete = "delete from User where id = :id";
    
    public boolean add(User entity) {
        
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        boolean result = false;
        try {
            add(session, entity);
            tx.commit();
            result = true;
        } catch ( Exception e ) {
            System.out.println("Error in DAO user add: " + e.getMessage());
            result = false;
        }finally {
           session.close(); 
        }
        return result;
    }
    
    private void add(Session session, User entity) {
        User object = entity;
        session.save(object);
    }
    
    public boolean update(User entity) {
        boolean success = false;
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        try {
            update(session, entity);
            tx.commit();
            success = true;
        } catch ( Exception e ) {
            System.out.println("Error in DAO user add: " + e.getMessage());
        }
        session.close();
        return success;
    }
    
    private void update(Session session, User entity) {
        User object = entity;
        session.update(object);
    }
    
    /*public int update(User entity) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(queryUpdate);
        query.setString("name", entity.getName());
        //query.setString("email", entity.getEmail()); no se puede modificar el email
        query.setString("password", entity.getPassword());
        query.setString("phone", entity.getPhone());
        query.setInteger("user_type_id", entity.getUserTypeId());
        query.setBoolean("enabled", entity.getEnabled());
        query.setFloat("latitud", entity.getLatitud());
        query.setFloat("longitude", entity.getLongitude());
        query.setDate("last_update", entity.getLastUpdate());
        query.setBinary("photo", entity.getPhoto());
        query.setInteger("id", entity.getUserId());
        int rowCount = query.executeUpdate();
        System.out.println(TAG+". filas afectadas: " + rowCount);
        tx.commit();
        session.close();
        return rowCount;
    }*/
    
    public List getAllByCompanyV2(Integer companyId) {
        Session session = SessionUtil.getSession();
        List result = null;
        try{
            Query query = session.createQuery("select u.id, u.email, u.name, u.phone, u.photo from User u where u.companyId = :companyId ");
            query.setInteger("companyId", companyId);
            result = query.list();
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
    
    public User findByIdV2(int id) {
        
        Session session = SessionUtil.getSession();
        User result = null;
        try{
           //session.refresh(User.class); 
           Query query = session.createQuery("select u.id, u.email, u.name, u.phone, u.photo from User u where u.id = :id ");
           query.setMaxResults(1);
           query.setInteger("id", id);
           result = (User) query.uniqueResult();
           if ( result!=null )
                System.out.print("User Name sin Transaction: " + result.getName()); 
        }catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        }finally {
           session.close(); 
        }
        return result;
    }
    
    public User findByIdV2(int id, int companyId) {
        
        Session session = SessionUtil.getSession();
        User result = null;
        try{
           //session.refresh(User.class); 
           Query query = session.createQuery("select u.id, u.email, u.name, u.phone, u.photo from User u where u.id = :id and u.companyId = :companyId");
           query.setMaxResults(1);
           query.setInteger("id", id);
           query.setInteger("companyId", companyId);
           result = (User) query.uniqueResult();
           if ( result!=null )
                System.out.print("User Name sin Transaction: " + result.getName()); 
        }catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        }finally {
           session.close(); 
        }
        return result;
    }
    
    public User findByEmailV2(String email) {
        Session session = SessionUtil.getSession();
        User result = null;
        try{
           //session.refresh(User.class); 
           Query query = session.createQuery("select u.id, u.email, u.name, u.phone, u.photo from User u where u.id = :id and u.companyId = :companyId");
           query.setMaxResults(1);
           query.setParameter("email", email);
           result = (User) query.uniqueResult();

           if ( result!=null )
                System.out.print("User Email: " + result.getEmail());  
        }catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        }finally {
           session.close(); 
        }
            
        return result;
    }
    
    public User authenticationV2(String email, String password) {
        User result = null;
        Session session = SessionUtil.getSession();
        try{
            Query query = session.createQuery("select u.id, u.email, u.name, u.phone, u.photo from User u where u.id = :id and u.companyId = :companyId");
            query.setMaxResults(1);
            query.setParameter("email", email);
            query.setParameter("password", password);
            result = (User) query.uniqueResult();
            
            if (result!=null)
                System.out.println(TAG+". Usuario autenticado: " + result.getName());
        }catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        }finally {
           session.close(); 
        }
        return result;
    }
    
    public List getAllByCompany(Company company) {
        Session session = SessionUtil.getSession();
        List result = null;
        try{
           Criteria cr = session.createCriteria(User.class);
           cr.add(Restrictions.eq("company", company));
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
    
    public User findById(int id) {
        
        Session session = SessionUtil.getSession();
        User result = null;
        try{
           //session.refresh(User.class); 
           Criteria cr = session.createCriteria(User.class);
           cr.setProjection(Projections.projectionList()
                   .add(Projections.property("id"), "id")
                   .add(Projections.property("name"), "name")
                   .add(Projections.property("phone"), "phone")
                   .add(Projections.property("email"), "email")
                   .add(Projections.property("photo"), "photo")
                   .add(Projections.property("latitud"), "latitud")
                   .add(Projections.property("longitude"), "longitude")
                   .add(Projections.property("companyId"), "companyId"));
           cr.setResultTransformer(Transformers.aliasToBean(User.class)); 
           // Add restriction.
           cr.add(Restrictions.eq("id", id));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           
           result = (User) cr.uniqueResult();
           if ( result!=null )
                System.out.print("User Name sin Transaction: " + result.getName()); 
        }catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        }finally {
           session.close(); 
        }
        return result;
    }
    
    public User findById(int id, Company company) {
        Session session = SessionUtil.getSession();
        User result = null;
        try{
           //session.refresh(User.class);
           Criteria cr = session.createCriteria(User.class);
           // Add restriction.
           cr.add(Restrictions.eq("id", id));
           cr.add(Restrictions.eq("company", company));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (User) cr.uniqueResult();
           if ( result!=null )
                System.out.print("User Name sin Transaction: " + result.getName()); 
        }catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        }finally {
           session.close(); 
        }
        return result;
    }
    
    public User findByEmail(String email) {
        Session session = SessionUtil.getSession();
        User result = null;
        try{
           //session.refresh(User.class); 
           Criteria cr = session.createCriteria(User.class);
           // Add restriction.
           cr.add(Restrictions.eq("email", email));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (User) cr.uniqueResult();

           if ( result!=null )
                System.out.print("User Email: " + result.getEmail());  
        }catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        }finally {
           session.close(); 
        }
            
        return result;
    }
    
    public User authentication(String email, String password) {
        User result = null;
        Session session = SessionUtil.getSession();
        try{
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery(queryAuth);
            query.setString("email", email);
            query.setString("password", password);
            query.setMaxResults(1);
            result = (User) query.uniqueResult();
            if (result!=null)
                System.out.println(TAG+". Usuario autenticado: " + result.getName());
            tx.commit();
        }catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        }finally {
           session.close(); 
        }
        return result;
    }
    
    /* Method: findByStringField
       @param: String field, String value
       Este metodo permite hacer una busqueda por cualquier campo String de la tabla.
    */
    public User findByStringField(String field, String value) {
        Session session = SessionUtil.getSession();
        User result = null;
        try{
           Criteria cr = session.createCriteria(User.class);
           // Add restriction.
           cr.add(Restrictions.eq(field, value));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (User) cr.uniqueResult();

           if ( result!=null )
                System.out.print("Company Search: " + result.getEmail());  
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
