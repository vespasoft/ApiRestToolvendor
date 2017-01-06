/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;

import com.beecode.toolvendor.dao.UserZoneDAO;
import com.beecode.toolvendor.interfaces.UserZoneService;
import com.beecode.toolvendor.model.User;
import com.beecode.toolvendor.model.UserZone;
import com.beecode.toolvendor.model.Zone;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author luisvespa
 */
public class UserZoneServiceImpl implements UserZoneService {

    private static final AtomicLong counter = new AtomicLong();
    
    //----------------------------- SERVICES ---------------------------------
    private final UserServiceImpl userserv = new UserServiceImpl();
    private final ZoneServiceImpl zoneserv = new ZoneServiceImpl();
    //----------------------------- DAO --------------------------------------
    private final UserZoneDAO dao = new UserZoneDAO();

    public UserZoneServiceImpl() {
    }
    
    //----------------------- Agregar nuevo registro ---------------------------------
    @Override
    public String save(UserZone obj, Integer companyId) {
        String message="";
        try {
            if ( obj==null ) {
                message="Se espero un objeto UserZone en formato JSON";
            } else if ( obj.getUserId()==0 ) {
                message="El campo UserId no puede ser igual a 0";
            } else if ( obj.getZoneId()==null ) {
                message="El campo ZoneId no puede estar vacio";
            } else if ( obj.getZoneId()==0 ) {
                message="El campo ZoneId no puede ser igual a 0"; 
            } else if ( findUserZone(obj.getUserId(), obj.getZoneId()) ) {
                message="Este usuario ya tiene asignada esta zona";
            } else {
                //--- obtiene el usuario a partir del userId ---
                User user = userserv.findById(obj.getUserId(), companyId);
                //--- obtiene el grupo a partir del groupId ---
                Zone zone = zoneserv.findById(obj.getZoneId(), companyId);
                if (user==null) {
                    message="El campo userId no existe o es invalido";
                } else if (zone==null) {
                    message="El campo zoneId no existe o es invalido";
                } else {
                    //--- AtCreated fecha de creación del registro
                    dao.add(obj);
                    //--- obtiene el registro guardado con toda su info para la respuesta ---
                    if ( !findUserZone(obj.getUserId(), obj.getZoneId()) ) {
                        message="El registro no se pudo guardar, ocurrio un error inesperado.";
                    }
                }
            }
        } catch ( Exception e ) {
            System.out.println("Error in UserZone save: " + e.getMessage());
        }
        
        //-------------- si ocurrio un error la variable contiene el mensaje de error ---------------
        return message;
    }
 
    //------------------- Actualizar los datos de un registro existente --------------------------
    @Override
    public String update(UserZone obj, Integer companyId) {
        UserZone current = null;
        String message="";
        try {
            if ( obj==null ) {
                message="Se espero un objeto UserZone en formato JSON";
            } else if ( obj.getId()==0 ) {
                message="El campo Id no puede estar vacio";
            } else {
                //--- obtiene el registro con toda su info para luego editar ---
                current = findById(obj.getId());
                if (current!=null) {
                    //--- se reemplaza solo los campos obtenidos y que no vengan null desde el front
                    if (obj.getZoneId()!=0) current.setZoneId(obj.getZoneId());
                    if (obj.getUserId()!=0) current.setUserId(obj.getUserId());
                    //--- obtiene el usuario a partir del userId ---
                    User user = userserv.findById(obj.getUserId(), companyId);
                    //--- obtiene el grupo a partir del groupId ---
                    Zone zone = zoneserv.findById(obj.getZoneId(), companyId);
                    if (user==null) {
                        message="El campo userId no existe o es invalido";
                    } else if (zone==null) {
                        message="El campo zoneId no existe o es invalido";
                    } else {
                        //--- se ejecuta el update en la capa de datos ---
                        dao.update(current);
                    }
                } else {
                    message="No se encontro un registro asociado para este id";
                }
            }
        } catch ( Exception e ) {
            System.out.println("Error in UserZone update: " + e.getMessage());
        }
        
        return message;
    }
 
    
    @Override
    public boolean delete(int id) {
        boolean result = false;
        try {
            UserZone obj = dao.findById(id);
            if (obj != null) {
                int i = dao.delete(id);
                result = i==1;
            }
        } catch ( Exception e ) {
            System.out.println("Error in UserZone delete: " + e.getMessage());
        }
        
        return result;
    }

 
    //--------------------- FIND BY ID BOOLEAN --------------------------
    @Override
    public boolean findId(int id) {
        // se consulta en la BD si el id del UserType existe y es valido
        return dao.findById(id)!=null;
    }
    
    
    
    //--------------------- FIND BY ID OBJECT BRAND --------------------------
    @Override
    public UserZone findById(int id) {
        UserZone result = null;
        try {
            // Se busca en la bd los datos del usuario por Id.
            result = dao.findById(id);
        } catch ( Exception e ) {
            System.out.println("Error in UserZone findById: " + e.getMessage());
        }
        
        return result;
    }

    @Override
    public boolean findUserZone(Integer userId, Integer zoneId) {
        // se consulta en la BD si el name del usuario existe y es valido
        return dao.findByUserZone(userId, zoneId)!=null;
    }

    @Override
    public List getAllByUser(Integer userId) {
        List<User> list = null;
        try {
            // Se consulta en la bd los customer registrados para un usuario.
            list = dao.getAllByUser(userId);
        } catch ( Exception e ) {
            System.out.println("Error in userzone getAllByUser: " + e.getMessage());
        }
        return list;
    }
    
}
