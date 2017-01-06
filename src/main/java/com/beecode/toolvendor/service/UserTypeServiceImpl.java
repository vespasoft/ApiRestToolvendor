/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;

import com.beecode.toolvendor.dao.UserTypeDAO;
import com.beecode.toolvendor.interfaces.UserTypeService;
import com.beecode.toolvendor.model.UserType;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author luisvespa
 */
public class UserTypeServiceImpl implements UserTypeService {

    private static final AtomicLong counter = new AtomicLong();
     
    private final UserTypeDAO dao = new UserTypeDAO();

    public UserTypeServiceImpl() {
    }
    
    
    //----------------------- Agregar nuevo registro ---------------------------------
    @Override
    public String save(UserType obj) {
        String message="";
        try {
            if ( obj==null ) {
                message="Se espero un objeto user en formato JSON";
            } else if ( obj.getDescription()==null ) {
                message="El campo description no puede estar vacio";
            } else if ( obj.getType()==null ) {
                message="El campo Type no puede estar vacio";
            } else if ( obj.getDescription().length()==0 ) {
                message="El campo Description no puede estar vacio";
            } else if ( obj.getType().length()==0 ) {
                message="El campo Type no puede estar vacio";
            } else if ( findName(obj.getDescription(), obj.getCompanyId()) ) {
                message="Ya existe un tipo de usuario con la misma descripcion";
            } else {
                //--- AtCreated fecha de creación del registro
                dao.add(obj);
            }
        } catch ( Exception e ) {
            System.out.println("Error in usertype save: " + e.getMessage());
        }
        //-------------- si ocurrio un error la variable contiene el mensaje de error ---------------
        
        return message;
    }
 
    //------------------- Actualizar los datos de un registro existente --------------------------
    @Override
    public String update(UserType obj) {
        UserType currentType;
        String message="";
        try {
            if ( obj==null ) {
                message="Se espero un objeto usertype en formato JSON";
            } else if ( obj.getId()==null ) {
                message="El campo UserTypeId no puede estar vacio";
            } else if ( obj.getId()==0 ) {
                message="El campo UserTypeId no puede estar vacio";
            } else {
                //--- obtiene el registro con toda su info para luego editar ---
                currentType = dao.findById(obj.getId(), obj.getCompanyId());
                if (currentType!=null) {
                    //--- se reemplaza solo los campos obtenidos y que no vengan null desde el front
                    if (obj.getId()!=null) currentType.setId(obj.getId());
                    if (obj.getDescription()!=null) currentType.setDescription(obj.getDescription());
                    if (obj.getType()!=null) currentType.setType(obj.getType());
                    //--- se ejecuta el update en la capa de datos ---
                    dao.update(currentType);
                } else {
                    message="No se encontro un registro asociado para este id";
                }
            }
        } catch ( Exception e ) {
            System.out.println("Error in usertype save: " + e.getMessage());
        }
        
        return message;
    }
    
    @Override
    public boolean delete(int id) {
        boolean result = false;
        try {
            int i = dao.delete(id);
            result = i==1;

        } catch ( Exception e ) {
            System.out.println("Error in usertype delete: " + e.getMessage());
        }
        
        return result;
    }

 
    //--------------------- FIND BY ID BOOLEAN --------------------------
    @Override
    public boolean findId(int id, int companyId) {
        // se consulta en la BD si el id del UserType existe y es valido
        return dao.findById(id, companyId)!=null;
    }
    
    //--------------------- FIND BY NAME BOOLEAN --------------------------
    @Override
    public boolean findName(String name, int companyId) {
        // se consulta en la BD si el name del usuario existe y es valido
        return dao.findByDescription(name, companyId)!=null;
    }
    
    //--------------------- FIND BY ID OBJECT USERTYPE --------------------------
    @Override
    public UserType findById(int id, int companyId) {
        UserType result = null;
        try {
            // Se busca en la bd los datos del usuario por Id.
            result = dao.findById(id, companyId);
        } catch ( Exception e ) {
            System.out.println("Error in UserType findById: " + e.getMessage());
        }
        
        return result;
    }
    
    //--------------------- FIND BY NAME OBJECT BRAND --------------------------
    @Override
    public UserType findByName(String name, int companyId) {
        UserType result = null;
        try {
            // Se busca en la bd los datos del usuario por Email.
            result = dao.findByDescription(name, companyId);
        } catch ( Exception e ) {
            System.out.println("Error in UserType findByName: " + e.getMessage());
        }
        
        return result;
    }   
    
    //--------------------- GET ALL BY COMPANY --------------------------
    @Override
    public List getAllByCompany(Integer companyId) {
        List list = null;
        try {
            // Se consulta en la bd los country registrados.
            list = dao.getAllByCompany(companyId);
        } catch ( Exception e ) {
            System.out.println("Error in UserType getAll: " + e.getMessage());
        }
        return list;
    }
    
}
