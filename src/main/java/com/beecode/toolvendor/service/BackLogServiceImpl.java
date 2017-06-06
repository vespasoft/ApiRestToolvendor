/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;

import com.beecode.toolvendor.dao.BackLogDAO;
import com.beecode.toolvendor.interfaces.BackLogService;
import com.beecode.toolvendor.model.BackLog;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author luisvespa
 */
public class BackLogServiceImpl implements BackLogService {

    private static final AtomicLong counter = new AtomicLong();
    
    //------------------------------- DAO ------------------------------------
    private BackLogDAO dao;

    public BackLogServiceImpl() {
    }
    
    //----------------------- Agregar nuevo registro ---------------------------------
    @Override
    public boolean save(BackLog obj) {
        dao = new BackLogDAO();
        if ( obj==null ) {
            return false;
        } else {
            //--- AtCreated fecha de creación del registro
            obj.setCreatedAt(new Date());
            dao.add(obj);
            System.out.println("Error in class: " +obj.getClass_name()+" method: "+ obj.getVoid_name() +". " + obj.getError_message());
            return true;
        }
    }
 
    //------------------- Actualizar los datos de un registro existente --------------------------
    @Override
    public boolean update(BackLog obj) {
        dao = new BackLogDAO();
        BackLog current = null;
        if ( obj==null ) {
            return false;
        } else if ( obj.getId()==0 ) {
            return false;
        } else {
            //--- obtiene el registro con toda su info para luego editar ---
            current = dao.findById(obj.getId());
            if ( current!=null) {
                //--- se reemplaza solo los campos obtenidos y que no vengan null desde el front
                if (obj.getId()!=0) current.setId(obj.getId());
                if (obj.getClass_name()!=null) current.setClass_name(obj.getClass_name());
                if (obj.getVoid_name()!=null) current.setVoid_name(obj.getVoid_name());
                if (obj.getCompanyId()!=null) current.setCompanyId(obj.getCompanyId());
                if (obj.getModules_id()!=null) current.setModules_id(obj.getModules_id());
                if (obj.getUserId()!=null) current.setUserId(obj.getUserId());
                //--- se ejecuta el update en la capa de datos ---
                dao.update(current);
                return true;
            } else {
                return false;
            }
        }
    }
    
    //--------------------- FIND BY ID BOOLEAN --------------------------
    @Override
    public boolean findId(int id) {
        // se consulta en la BD si el id del usuario existe y es valido
        return dao.findById(id)!=null;
    }
    
    //--------------------- FIND BY ID OBJECT BRAND --------------------------
    @Override
    public BackLog findById(int id) {
        BackLog result = null;
        try {
            // Se busca en la bd los datos del usuario por Id.
            result = dao.findById(id);
        } catch ( Exception e ) {
            System.out.println("Error in backlog findById: " + e.getMessage());
        }
        
        return result;
    }
    
    @Override
    public boolean delete(int id) {
        boolean result = false;
        try {
            int i = dao.delete(id);
            result = i==1;
        } catch ( Exception e ) {
            System.out.println("Error in backlog delete: " + e.getMessage());
        }
        
        return result;
    }

    @Override
    public List getAllByCompany(Integer companyId) {
        List<BackLog> list = null;
        try {
            // Se consulta en la bd las marcas registradas de una compañia.
            list = dao.getAllByCompany(companyId);
        } catch ( Exception e ) {
            System.out.println("Error in brand getAllByCompany: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List getAllByUser(Integer userId) {
        List<BackLog> list = null;
        try {
            // Se consulta en la bd las marcas registradas de una compañia.
            list = dao.getAllByUser(userId);
        } catch ( Exception e ) {
            System.out.println("Error in brand getAllByCompany: " + e.getMessage());
        }
        return list;
    }
    
}
