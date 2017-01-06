/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;

import com.beecode.toolvendor.dao.PermissionDAO;
import com.beecode.toolvendor.interfaces.PermissionService;
import com.beecode.toolvendor.model.Modules;
import com.beecode.toolvendor.model.Permission;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author luisvespa
 */
public class PermissionServiceImpl implements PermissionService {
    //----------------------------- OBJECTS ---------------------------------
    private static final AtomicLong counter = new AtomicLong();
    
    //----------------------------- SERVICES ---------------------------------
    ModulesServiceImpl modulesserv = new ModulesServiceImpl();
    GroupsServiceImpl groupserv = new GroupsServiceImpl();
    
    //----------------------------- DAO --------------------------------------
    private PermissionDAO dao = new PermissionDAO();

    public PermissionServiceImpl() {
    }
    
    //----------------------- Agregar nuevo registro ---------------------------------
    @Override
    public String save(Permission obj, Integer companyId) {
        Permission current = null;
        String message="";
        if ( obj==null ) {
            message="Se espero un objeto Permission en formato JSON";
        } else if ( obj.getGroupId()==null ) {
            message="El campo GroupId no puede estar vacio";
        } else if ( obj.getGroupId()==0 ) {
            message="El campo GroupId no puede ser igual a 0";
        } else if ( obj.getModuleId()==null ) {
            message="El campo ModuleId no puede estar vacio";
        } else if ( obj.getModuleId()==0 ) {
            message="El campo ModuleId no puede ser igual a 0";
        } else if ( findGroupModule(obj.getGroupId(), obj.getModuleId()) ) {
            message="Este usuario ya tiene asignada este grupo";
        } else {
            //--- obtiene el usuario a partir del userId ---
            Modules module = modulesserv.findById(obj.getModuleId());
            if (module==null) {
                message="El campo ModuleId no existe o es invalido";
            } else if ( !groupserv.findId(obj.getGroupId(), companyId) ) {
                //--- obtiene el grupo a partir del groupId y companyId ---
                message="El campo groupId no existe o es invalido";
            } else {
                //--- AtCreated fecha de creación del registro
                dao.add(obj);
                //--- obtiene el registro guardado con toda su info para la respuesta ---
                current = findByGroupModule(obj.getGroupId(), obj.getModuleId());
                if ( current==null) {
                    message="El registro no se pudo guardar, ocurrio un error inesperado.";
                }
            }
        }
        
        return message;
    }
 
    //------------------- Actualizar los datos de un registro existente --------------------------
    @Override
    public String update(Permission obj, Integer companyId) {
        Permission current = null;
        String message="";
        
        if ( obj==null ) {
            message="Se espero un objeto Permission en formato JSON";
        } else if ( obj.getId()==null ) {
            message="El campo PermissionId no puede estar vacio";
        } else if ( obj.getId()==0 ) {
            message="El campo PermissionId no puede ser igual a 0";
        } else if ( obj.getGroupId()==null ) {
            message="El campo GroupId no puede estar vacio";
        } else if ( obj.getGroupId()==0 ) {
            message="El campo GroupId no puede ser igual a 0";
        } else if ( obj.getModuleId()==null ) {
            message="El campo ModuleId no puede estar vacio";
        } else if ( obj.getModuleId()==0 ) {
            message="El campo ModuleId no puede ser igual a 0";    
        } else {
            //--- obtiene el registro con toda su info para luego editar ---
            current = findById(obj.getId());
            if (current!=null) {
                //--- se reemplaza solo los campos obtenidos y que no vengan null desde el front
                if (obj.getIscreate()!=null) current.setIscreate(obj.getIscreate());
                if (obj.getIsdelete()!=null) current.setIsdelete(obj.getIsdelete());
                if (obj.getIsread()!=null) current.setIsread(obj.getIsread());
                if (obj.getIsupdate()!=null) current.setIsupdate(obj.getIsupdate());
                /*--- No es necesario actualizar los campos ModuleId y GroupId ----
                de esta forma validamos para que no se repitan los registros por grupo.
                y el usuario solo pueda actualizar los campos booleanos, si desea cambiar el grupo 
                entonces puede eliminarlo y agregar el modulo en el otro grupo.
                */
                //--- se ejecuta el update en la capa de datos ---
                dao.update(current);
            } else {
                message="No se encontro un registro asociado para este id";
            }
        }
        
        return message;
    }
 
    @Override
    public boolean delete(int id) {
        boolean result = false;
        try {
            Permission obj = dao.findById(id);
            if (obj != null) {
                int i = dao.delete(id);
                result = i==1;
            }
        } catch ( Exception e ) {
            System.out.println("Error in usertype delete: " + e.getMessage());
        }
        
        return result;
    }
 
    //--------------------- FIND BY ID BOOLEAN --------------------------
    @Override
    public boolean findId(int id) {
        // se consulta en la BD si el id del UserType existe y es valido
        return dao.findById(id)!=null;
    }
    
    //--------------------- FIND BY NAME BOOLEAN --------------------------
    @Override
    public boolean findGroupModule(int groupId, int moduleId)  {
        // se consulta en la BD si el name del usuario existe y es valido
        return dao.findByGroupModule(groupId, moduleId)!=null;
    }
    
    //--------------------- FIND BY ID OBJECT PERMISSION --------------------------
    @Override
    public Permission findById(int id) {
        Permission result = null;
        try {
            // Se busca en la bd los datos del usuario por Id.
            result = dao.findById(id);
        } catch ( Exception e ) {
            System.out.println("Error in Permission findById: " + e.getMessage());
        }
        
        return result;
    }
    
    //--------------------- FIND BY NAME OBJECT BRAND --------------------------
    @Override
    public Permission findByGroupModule(int groupId, int moduleId) {
        Permission result = null;
        try {
            // Se busca en la bd los datos del usuario por Email.
            result = dao.findByGroupModule(groupId, moduleId);
        } catch ( Exception e ) {
            System.out.println("Error in Permission findByGroupModule: " + e.getMessage());
        }
        
        return result;
    }
    
    @Override
    public List getAllByGroup(Integer groupId) {
        List list = null;
        try {
            // Se consulta en la bd los permisos del grupo.
            list = dao.getAllByGroup(groupId);
        } catch ( Exception e ) {
            System.out.println("Error in UserType getAll: " + e.getMessage());
        }
        return list;
    }
 
    
}
