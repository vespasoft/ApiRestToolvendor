/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;

import com.beecode.toolvendor.dao.GroupsDAO;
import com.beecode.toolvendor.interfaces.GroupsService;
import com.beecode.toolvendor.model.Groups;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author luisvespa
 */
public class GroupsServiceImpl implements GroupsService {

    private static final AtomicLong counter = new AtomicLong();
    
    private final GroupsDAO dao = new GroupsDAO();

    public GroupsServiceImpl() {
    }
    
    //----------------------- Agregar nuevo registro ---------------------------------
    @Override
    public String save(Groups obj) {
        Map<String,Object> result = new HashMap<String,Object>();
        Groups current = null;
        String message="";
        try {
            if ( obj==null ) {
                message="Se espero un objeto user en formato JSON";
            } else if ( obj.getName()==null ) {
                message="El campo Name no puede estar vacio";
            } else if ( obj.getDescription()==null ) {
                message="El campo Description no puede estar vacio";
            } else if ( obj.getName().length()==0 ) {
                message="El campo Name no puede estar vacio";
            } else if ( obj.getDescription().length()==0 ) {
                message="El campo Description no puede estar vacio";
            } else if ( findName(obj.getName(), obj.getCompanyId()) ) {
                message="Ya existe un registro con el mismo nombre";
            } else {
                //--- AtCreated fecha de creación del registro
                dao.add(obj);
                //--- obtiene el usuario registrado con toda su info para la respuesta ---
                current = findByName(obj.getName(), obj.getCompanyId());
                if ( current==null) {
                    message="El registro no se pudo guardar, ocurrio un error inesperado.";
                }
            }
        } catch ( Exception e ) {
            System.out.println("Error in groups save: " + e.getMessage());
        }
        
        //-------------- si ocurrio un error la variable contiene el mensaje de error ---------------
        return message;
    }
 
    //------------------- Actualizar los datos de un registro existente --------------------------
    @Override
    public String update(Groups obj) {
        Groups current = null;
        String message="";
        try {
            if ( obj==null ) {
                message="Se espero un objeto Group en formato JSON";
            } else if ( obj.getId()==null ) {
                message="El campo GroupId no puede estar vacio";
            } else if ( obj.getId()==0 ) {
                message="El campo GroupId no puede estar vacio";
            } else {
                //--- obtiene el registro con toda su info para luego editar ---
                current = findById(obj.getId(), obj.getCompanyId());
                if (current!=null) {
                    //--- se reemplaza solo los campos obtenidos y que no vengan null desde el front
                    if (obj.getId()!=null) current.setId(obj.getId());
                    if (obj.getDescription()!=null) current.setDescription(obj.getDescription());
                    if (obj.getName()!=null) current.setName(obj.getName());
                    //--- se ejecuta el update en la capa de datos ---
                    dao.update(current);
                } else {
                    message="No se encontro un registro asociado para este id";
                }
            }
        } catch ( Exception e ) {
            System.out.println("Error in groups save: " + e.getMessage());
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
            System.out.println("Error in Groups delete: " + e.getMessage());
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
        return dao.findByName(name, companyId)!=null;
    }
    
    //--------------------- FIND BY ID OBJECT BRAND --------------------------
    @Override
    public Groups findById(int id, int companyId) {
        Groups result = null;
        try {
            // Se busca en la bd los datos del usuario por Id.
            result = dao.findById(id, companyId);
        } catch ( Exception e ) {
            System.out.println("Error in Groups findById: " + e.getMessage());
        }
        
        return result;
    }
    
    //--------------------- FIND BY NAME OBJECT BRAND --------------------------
    @Override
    public Groups findByName(String name, int companyId) {
        Groups result = null;
        try {
            // Se busca en la bd los datos del usuario por Email.
            result = dao.findByName(name, companyId);
        } catch ( Exception e ) {
            System.out.println("Error in Groups findByName: " + e.getMessage());
        }
        
        return result;
    }
    
    @Override
    public List getAllByCompany(Integer companyId) {
        List list = null;
        try {
            // Se consulta en la bd los grupos de una compañia.
            list = dao.getAllByCompany(companyId);
        } catch ( Exception e ) {
            System.out.println("Error in UserType getAll: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List getAllByUser(Integer userId) {
        List list = null;
        try {
            // Se consulta en la bd los grupos de un usuario.
            list = dao.getAllByUser(userId);
        } catch ( Exception e ) {
            System.out.println("Error in UserType getAll: " + e.getMessage());
        }
        return list;
    }
    
}
