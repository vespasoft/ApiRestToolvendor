/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.dao;

import com.beecode.toolvendor.model.ConfigEmail;
import com.beecode.toolvendor.util.SessionUtil1;
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
public class ConfigEmailDAO {
    private final String TAG = ConfigEmailDAO.class.getName();
    private final String fieldId = "id";
    private final String queryDelete = "delete from ConfigEmail where id = :id";
    
    public void add(ConfigEmail entity) {
        Session session = SessionUtil1.getSession();
        Transaction tx = session.beginTransaction();
        add(session, entity);
        tx.commit();
        session.close();
    }
    
    private void add(Session session, ConfigEmail entity) {
        ConfigEmail object = entity;
        session.save(object);
    }
    
    public void update(ConfigEmail entity) {
        Session session = SessionUtil1.getSession();
        Transaction tx = session.beginTransaction();
        update(session, entity);
        tx.commit();
        session.close();
    }
    
    private void update(Session session, ConfigEmail entity) {
        ConfigEmail object = entity;
        session.update(object);
    }
    
    public ConfigEmail findByCompanyId(int companyId) {
        Session session = SessionUtil1.getSession();
        ConfigEmail result = null;
        Transaction tx = null;
        try{
           tx = session.beginTransaction();
           Criteria cr = session.createCriteria(ConfigEmail.class);
           // Add restriction.
           cr.add(Restrictions.eq("companyId", companyId));
           //crit.add(Restrictions.like("id", id+"%"));
           cr.setMaxResults(1);
           result = (ConfigEmail) cr.uniqueResult();
           
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
