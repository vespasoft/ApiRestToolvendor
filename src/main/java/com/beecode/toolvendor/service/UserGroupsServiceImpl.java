/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;

import com.beecode.toolvendor.dao.UserGroupsDAO;
import com.beecode.toolvendor.interfaces.UserGroupsService;
import com.beecode.toolvendor.model.Company;
import com.beecode.toolvendor.model.Groups;
import com.beecode.toolvendor.model.User;
import com.beecode.toolvendor.model.UserGroups;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author luisvespa
 */
public class UserGroupsServiceImpl implements UserGroupsService {

    private static final AtomicLong counter = new AtomicLong();
    
    private final UserServiceImpl userserv = new UserServiceImpl();
    private final GroupsServiceImpl groupserv = new GroupsServiceImpl();
    private final UserGroupsDAO dao = new UserGroupsDAO();

    public UserGroupsServiceImpl() {
    
    }
    
    //----------------------- Agregar nuevo registro ---------------------------------
    @Override
    public String save(UserGroups obj, Company company) {
        UserGroups current = null;
        String message="";
        try {
            if ( obj==null ) {
                message="Se espero un objeto UserGroups en formato JSON";
            } else if ( obj.getUserId()==0 ) {
                message="El campo UserId no puede ser igual a 0";
            } else if ( obj.getGroupId()==null ) {
                message="El campo GroupId no puede estar vacio";
            } else if ( obj.getGroupId()==0 ) {
                message="El campo GroupId no puede ser igual a 0";    
            } else if ( findUserGroups(obj.getUserId(), obj.getGroupId()) ) {
                message="Este usuario ya tiene asignado este grupo";
            } else {
                //--- obtiene el usuario a partir del userId ---
                User user = userserv.findById(obj.getUserId(), company);
                //--- obtiene el grupo a partir del groupId ---
                Groups group = groupserv.findById(obj.getGroupId(), company.getId());
                if (user==null) {
                    message="El campo userId no existe o es invalido";
                } else if (group==null) {
                    message="El campo groupId no existe o es invalido";
                } else {
                    //--- AtCreated fecha de creación del registro
                    dao.add(obj);
                    //--- Se verifica si el grupo se agrego correctamente al usuario ---
                    if ( !findUserGroups(obj.getUserId(), obj.getGroupId()) ) {
                        message="El registro no se pudo guardar, ocurrio un error inesperado.";
                    }
                }
            }
        } catch ( Exception e ) {
            System.out.println("Error in usergroups save: " + e.getMessage());
        }
        
        //-------------- si ocurrio un error la variable contiene el mensaje de error ---------------
        
        return message;
    }
 
    //------------------- Actualizar los datos de un registro existente --------------------------
    @Override
    public String update(UserGroups obj, Company company) {
        UserGroups current = null;
        String message="";
        try {
            if ( obj==null ) {
                message="Se espero un objeto UserGroups en formato JSON";
            } else if ( obj.getId()==null ) {
                message="El campo Id no puede estar vacio";
            } else if ( obj.getId()==0 ) {
                message="El campo Id no puede estar vacio";
            } else {
                //--- obtiene el registro con toda su info para luego editar ---
                current = findById(obj.getId());
                if (current!=null) {
                    //--- se reemplaza solo los campos obtenidos y que no vengan null desde el front
                    if (obj.getGroupId()!=null) current.setGroupId(obj.getGroupId());
                    if (obj.getId()!=null) current.setId(obj.getId());
                    if (obj.getUserId()!=null) current.setUserId(obj.getUserId());
                    //--- obtiene el usuario a partir del userId ---
                    User user = userserv.findById(current.getUserId(), company);
                    //--- obtiene el grupo a partir del groupId ---
                    Groups group = groupserv.findById(current.getGroupId(), company.getId());
                    if (user==null) {
                        message="El campo userId no existe o es invalido";
                    } else if (group==null) {
                        message="El campo groupId no existe o es invalido";
                    } else {
                        //--- se ejecuta el update en la capa de datos ---
                        dao.update(current);
                    }

                } else {
                    message="No se encontro un registro asociado para este id";
                }
            }
        } catch ( Exception e ) {
            System.out.println("Error in usergroups update: " + e.getMessage());
        }
        
        return message;
    }
    
    @Override
    public boolean delete(int id) {
        boolean result = false;
        try {
            UserGroups obj = dao.findById(id);
            if (obj != null) {
                int i = dao.delete(id);
                result = i==1;
            }
        } catch ( Exception e ) {
            System.out.println("Error in UserGroups delete: " + e.getMessage());
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
    public boolean findUserGroups(int userId, int groupId) {
        // se consulta en la BD si el name del usuario existe y es valido
        return dao.findByUserGroups(userId, groupId)!=null;
    }
    
    //--------------------- FIND BY ID OBJECT BRAND --------------------------
    @Override
    public UserGroups findById(int id) {
        UserGroups result = null;
        try {
            // Se busca en la bd los datos del usuario por Id.
            result = dao.findById(id);
        } catch ( Exception e ) {
            System.out.println("Error in UserGroups findById: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List getAllByUser(Integer userId) {
        List<User> list = null;
        try {
            // Se consulta en la bd los customer registrados para un usuario.
            list = dao.getAllByUser(userId);
        } catch ( Exception e ) {
            System.out.println("Error in usergroups getAllByUser: " + e.getMessage());
        }
        return list;
    }
    
}
