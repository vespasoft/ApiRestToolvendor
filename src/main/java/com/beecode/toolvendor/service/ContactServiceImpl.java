/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;

import com.beecode.toolvendor.dao.ContactDAO;
import com.beecode.toolvendor.interfaces.ContactService;
import com.beecode.toolvendor.model.Contact;
import com.beecode.toolvendor.model.User;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author luisvespa
 */
public class ContactServiceImpl implements ContactService {

    private static final AtomicLong counter = new AtomicLong();
    
    private final UserServiceImpl userserv = new UserServiceImpl();
    private final ContactDAO dao = new ContactDAO();

    public ContactServiceImpl() {
    }
    
    //----------------------- Agregar nuevo registro ---------------------------------
    @Override
    public String save(Contact obj, Integer companyId) {
        Contact current = null;
        String message="";
        try {
            if ( obj==null ) {
                message="Se espero un objeto user en formato JSON";
            } else if ( obj.getName()==null ) {
                message="El campo Name no puede ser nullo";  
            } else if ( obj.getPhone()==null ) {
                message="El campo Phone no puede ser nullo";
            } else if ( obj.getName().length()==0 ) {
                message="El campo Name no puede estar vacio";
            } else if ( obj.getPhone().length()==0 ) {
                message="El campo Phone no puede estar vacio";
            } else if ( obj.getUserId()==0 ) {
                message="El campo UserId no puede ser igual a 0";
            } else if ( findName(obj.getName() ) ) {
                message="Ya existe un contacto con el mismo nombre";
            } else {
                //--- obtiene el usuario a partir del userId ---
                User user = userserv.findById(obj.getUserId(), companyId);
                if (user==null) {
                    message="El campo userId no existe o es invalido";
                } else {
                    //--- AtCreated fecha de creación del registro
                    dao.add(obj);
                    //--- obtiene el usuario registrado con toda su info para la respuesta ---
                    current = findByName(obj.getName());
                    if ( current==null) {
                        message="El registro no se pudo guardar, ocurrio un error inesperado.";
                    }
                }
            }
        } catch ( Exception e ) {
            System.out.println("Error in contact save: " + e.getMessage());
        }
        //-------------- si ocurrio un error la variable contiene el mensaje de error ---------------
        return message;
    }
 
    //------------------- Actualizar los datos de un registro existente --------------------------
    @Override
    public String update(Contact obj, Integer companyId) {
        Contact current = null;
        String message="";
        try {
            if ( obj==null ) {
                message="Se espero un objeto user en formato JSON";
            } else if ( obj.getName()==null ) {
                message="El campo Name no puede ser nullo";
            } else if ( obj.getPhone()==null ) {
                message="El campo Phone no puede ser nullo";
            } else if ( obj.getName().length()==0 ) {
                message="El campo Name no puede estar vacio";
            } else if ( obj.getPhone().length()==0 ) {
                message="El campo Phone no puede estar vacio";
            } else if ( obj.getUserId()==0 ) {
                message="El campo UserId no puede ser igual a 0";
            } else {
                //--- obtiene el registro con toda su info para luego editar ---
                current = findById(obj.getId());
                if (current!=null) {
                    //--- obtiene el usuario a partir del userId ---
                    User user = userserv.findById(obj.getUserId(), companyId);
                    if ( user==null) {
                        message="El campo userId no existe o es invalido";
                    } else  {
                        //--- se reemplaza solo los campos obtenidos y que no vengan null desde el front
                        if (obj.getId()!=null) current.setId(obj.getId());
                        if (obj.getName()!=null) current.setName(obj.getName());
                        if (obj.getUserId()!=null) current.setUserId(obj.getUserId());
                        if (obj.getPhone()!=null) current.setPhone(obj.getPhone());
                        if (obj.getPhone2()!=null) current.setPhone2(obj.getPhone2());
                        //--- se ejecuta el update en la capa de datos ---
                        dao.update(current);
                    }
                } else {
                    message="No se encontro un registro asociado para este id";
                }
            }
        } catch ( Exception e ) {
            System.out.println("Error in contact update: " + e.getMessage());
        }
        //-------------- si ocurrio un error la variable contiene el mensaje de error ---------------
        return message;
    }
 
    //----------------- DELETE CONTACT BY ID BOOLEAN --------------------------
    @Override
    public boolean delete(int id) {
        boolean result = false;
        try {
            Contact obj = dao.findById(id);
            if (obj != null) {
                int i = dao.delete(id);
                result = i==1;
            }
        } catch ( Exception e ) {
            System.out.println("Error in contact delete: " + e.getMessage());
        }
        
        return result;
    }

    @Override
    public boolean findId(int id) {
        // se consulta en la BD si el id del UserType existe y es valido
        return dao.findById(id)!=null;
    }
    
    @Override
    public boolean findName(String name) {
        // se consulta en la BD si el name del contact existe y es valido
        return dao.findByName(name)!=null;
    }

    @Override
    public Contact findById(int id) {
        Contact result = null;
        try {
            // Se busca en la bd los datos del usuario por Id.
            result = dao.findById(id);
        } catch ( Exception e ) {
            System.out.println("Error in Contact findById: " + e.getMessage());
        }
        
        return result;
    }

    @Override
    public Contact findByName(String name) {
        Contact result = null;
        try {
            // Se busca en la bd los datos del usuario por Id.
            result = dao.findByName(name);
        } catch ( Exception e ) {
            System.out.println("Error in Contact findByName: " + e.getMessage());
        }
        
        return result;
    }

    @Override
    public List getAllByUser(Integer userId) {
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
