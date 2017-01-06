/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;

import com.beecode.toolvendor.dao.OrderTypeDAO;
import com.beecode.toolvendor.interfaces.OrderTypeService;
import com.beecode.toolvendor.model.OrderType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author luisvespa
 */
public class OrderTypeServiceImpl implements OrderTypeService {

    private static final AtomicLong counter = new AtomicLong();
    //------------------------------- DAO --------------------------------------------
    private final OrderTypeDAO dao = new OrderTypeDAO();

    public OrderTypeServiceImpl() {
    }
    
    //----------------------- Agregar nuevo registro ---------------------------------
    @Override
    public String save(OrderType obj) {
        OrderType currentType = null;
        String message="";
        if ( obj==null ) {
            message="Se espero un objeto user en formato JSON";
        } else if ( obj.getName()==null ) {
            message="El campo Name no puede ser nulo";
        } else if ( obj.getName().length()==0 ) {
            message="El campo Name no puede estar vacio";
        } else if ( findName(obj.getName()) ) {
            message="Ya existe un tipo de visita con el mismo nombre";
        } else {
            //--- AtCreated fecha de creación del registro
            dao.add(obj);
            //--- obtiene el usuario registrado con toda su info para la respuesta ---
            currentType = dao.findByName(obj.getName());
            if ( currentType==null) {
                message="El registro no se pudo guardar, ocurrio un error inesperado.";
            }
        }
        //-------------- si ocurrio un error la variable contiene el mensaje de error ---------------
        
        return message;
    }
 
    //------------------- Actualizar los datos de un registro existente --------------------------
    @Override
    public String update(OrderType obj) {
        OrderType currentType = null;
        String message="";
        
        if ( obj==null ) {
            message="Se espero un objeto usertype en formato JSON";
        } else if ( obj.getId()==null ) {
            message="El campo Id no puede ser nulo";
        } else if ( obj.getId()==0 ) {
            message="El campo Id no puede ser 0";
        } else {
            //--- obtiene el registro con toda su info para luego editar ---
            currentType = dao.findById(obj.getId());
            if (currentType!=null) {
                //--- se reemplaza solo los campos obtenidos y que no vengan null desde el front
                if (obj.getId()!=null) currentType.setId(obj.getId());
                if (obj.getName()!=null) currentType.setName(obj.getName());
                //--- se ejecuta el update en la capa de datos ---
                dao.update(currentType);
            } else {
                message="No se encontro un registro asociado para este id";
            }
        }
        
        return message;
    }
 
    //--------------------- FIND BY ID BOOLEAN --------------------------
    @Override
    public boolean findId(int id ) {
        // se consulta en la BD si el id del usuario existe y es valido
        return dao.findById(id)!=null;
    }
    
    //--------------------- FIND BY NAME BOOLEAN --------------------------
    @Override
    public boolean findName(String name) {
        // se consulta en la BD si el name del usuario existe y es valido
        return dao.findByName(name)!=null;
    }
    
    //--------------------- FIND BY ID OBJECT BRAND --------------------------
    @Override
    public OrderType findById(int id) {
        OrderType result = null;
        try {
            // Se busca en la bd los datos del usuario por Id.
            result = dao.findById(id);
        } catch ( Exception e ) {
            System.out.println("Error in OrderType findById: " + e.getMessage());
        }
        
        return result;
    }
    
    //--------------------- FIND BY NAME OBJECT BRAND --------------------------
    @Override
    public OrderType findByName(String name) {
        OrderType result = null;
        try {
            // Se busca en la bd los datos del usuario por Email.
            result = dao.findByName(name);
        } catch ( Exception e ) {
            System.out.println("Error in OrderType findByName: " + e.getMessage());
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
            System.out.println("Error in OrderType delete: " + e.getMessage());
        }
        
        return result;
    }

    @Override
    public List getAll() {
        List<OrderType> list = null;
        try {
            // Se consulta en la bd las marcas registradas de una compañia.
            list = dao.getAll();
        } catch ( Exception e ) {
            System.out.println("Error in OrderType getAll: " + e.getMessage());
        }
        return list;
    }

    
}
