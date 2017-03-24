/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.dao;

import com.beecode.toolvendor.model.VisitPicture;
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
public class VisitPictureDAO {
    private static String TAG = VisitPictureDAO.class.getName();
    private static String fieldId = "id";
    private static String queryDelete = "delete from VisitPicture where id = :id";
    
    public void add(VisitPicture entity) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        add(session, entity);
        tx.commit();
        session.close();
    }
    
    private void add(Session session, VisitPicture entity) {
        VisitPicture object = entity;
        session.save(object);
    }
    
    public void update(VisitPicture entity) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        update(session, entity);
        tx.commit();
        session.close();
    }
    
    private void update(Session session, VisitPicture entity) {
        VisitPicture object = entity;
        session.update(object);
    }
    
    /*
    Method Name: getAllByVisit
    Description: obtiene todos los registros relacionados con una visita.
    Parameters:  visitId (int)
    Return: List<VisitPicture>
    */
    public List getAllByVisit(int visitId) {
        Session session = SessionUtil.getSession();
        List result = null;
        try{
           Criteria cr = session.createCriteria(VisitPicture.class);
           // Add restriction.
           cr.add(Restrictions.eq("visitId", visitId));
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
    
    /*
    Method Name: findById
    Description: obtiene un objeto tipo VisitPicture a partir de un Id.
    Parameters:  id (int)
    Return: VisitPicture
    */
    public VisitPicture findById(Integer id) {
        Session session = SessionUtil.getSession();
        VisitPicture result = null;
        try{
           Criteria cr = session.createCriteria(VisitPicture.class);
           // Add restriction.
           cr.add(Restrictions.eq("id", id));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (VisitPicture) cr.uniqueResult();
           if ( result!=null )
                System.out.print("VisitPicture: " + result.getPicture()); 
        }catch (HibernateException e) {
            if ( e != null )
                System.out.print("Error DAO: " + e.getMessage());
        }finally {
           session.close(); 
        }
        return result;
    }
    
    /*
    Method Name: findByPicture
    Description: obtiene un objecto VisitPicture si existe una imagen con este nombre
    Parameters:  picture (String)
    Return: VisitPicture
    */
    public VisitPicture findByPicture(String picture) {
        Session session = SessionUtil.getSession();
        VisitPicture result = null;
        try{
           Criteria cr = session.createCriteria(VisitPicture.class);
           // Add restriction.
           cr.add(Restrictions.eq("picture", picture));
           cr.setMaxResults(1);
           result = (VisitPicture) cr.uniqueResult();
           if ( result!=null )
                System.out.print("VisitPicture: " + result.getPicture()); 
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
