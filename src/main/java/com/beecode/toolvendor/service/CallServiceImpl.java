/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;

import com.beecode.toolvendor.dao.CallDAO;
import com.beecode.toolvendor.interfaces.CallService;
import com.beecode.toolvendor.model.Call;
import com.beecode.toolvendor.model.User;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author luisvespa
 */
public class CallServiceImpl implements CallService {

    private static final AtomicLong counter = new AtomicLong();
    
    private final UserServiceImpl userserv = new UserServiceImpl();
    private final CallDAO dao = new CallDAO();

    public CallServiceImpl() {
    }
    
    //----------------------- Agregar nuevo registro ---------------------------------
    @Override
    public String save(Call obj) {
        Map<String,Object> result = new HashMap<String,Object>();
        Call current = null;
        String message="";
        try {
            if ( obj==null ) {
                message="Se espero un objeto user en formato JSON"; 
            } else if ( obj.getPhone()==null ) {
                message="El campo Phone no puede ser nullo";
            } else if ( obj.getPhone().length()==0 ) {
                message="El campo Phone no puede estar vacio";
            } else if ( obj.getUserId()==0 ) {
                message="El campo UserId no puede ser igual a 0";
            } else {
                //--- LastUpdate fecha de actualizacion del registro
                obj.setCreatedAt(new Date());
                //--- AtCreated fecha de creación del registro
                dao.add(obj);
            }
        } catch ( Exception e ) {
            System.out.println("Error in call save: " + e.getMessage());
        }
        //-------------- si ocurrio un error la variable contiene el mensaje de error ---------------
        
        
        return message;
    }
 
    //------------------- Actualizar los datos de un registro existente --------------------------
    @Override
    public String update(Call obj) {
        Call current = null;
        String message="";
        try {
            if ( obj==null ) {
                message="Se espero un objeto user en formato JSON";
            } else if ( obj.getPhone()==null ) {
                message="El campo Phone no puede ser nullo";
            } else if ( obj.getPhone().length()==0 ) {
                message="El campo Phone no puede estar vacio";
            } else if ( obj.getUserId()==0 ) {
                message="El campo UserId no puede ser igual a 0";
            } else {
                //--- obtiene el registro con toda su info para luego editar ---
                current = findById(obj.getId());
                if (current!=null) {
                    //--- se reemplaza solo los campos obtenidos y que no vengan null desde el front
                    if (obj.getCallBeginTime()!=null) current.setCallBeginTime(obj.getCallBeginTime());
                    if (obj.getCallEndTime()!=null) current.setCallEndTime(obj.getCallEndTime());
                    if (obj.getUserId()!=0) current.setUserId(obj.getUserId());
                    if (obj.getPhone()!=null) current.setPhone(obj.getPhone());
                    //--- se ejecuta el update en la capa de datos ---
                    dao.update(current);
                } else {
                    message="No se encontro un registro asociado para este id";
                }
            }
        } catch ( Exception e ) {
            System.out.println("Error in call save: " + e.getMessage());
        }
        //-------------- si ocurrio un error la variable contiene el mensaje de error ---------------
        
        return message;
    }
 
    //----------------- DELETE CALL BY ID BOOLEAN --------------------------
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
    public boolean findId(int id) {
        // se consulta en la BD si el id del UserType existe y es valido
        return dao.findById(id)!=null;
    }
    
    
    //--------------------- FIND BY ID OBJECT BRAND --------------------------
    @Override
    public Call findById(int id) {
        Call result = null;
        try {
            // Se busca en la bd los datos del usuario por Id.
            result = dao.findById(id);
        } catch ( Exception e ) {
            System.out.println("Error in call findById: " + e.getMessage());
        }
        
        return result;
    }
    
    //--------------------- GET ALL CALL BY USER ----------------------------
    @Override
    public List getAllByUser(Integer userId){
        List list = null;
        try {
            // Se consulta en la bd los customer registrados para un usuario.
            list = dao.getAllByUser(userId);
        } catch ( Exception e ) {
            System.out.println("Error in call getAllByUser: " + e.getMessage());
        }
        return list;
    }
 
    
}
