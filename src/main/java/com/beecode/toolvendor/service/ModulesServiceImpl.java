/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;

import com.beecode.toolvendor.dao.ModulesDAO;
import com.beecode.toolvendor.interfaces.ModulesService;
import com.beecode.toolvendor.model.Modules;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author luisvespa
 */
public class ModulesServiceImpl implements ModulesService {

    private static final AtomicLong counter = new AtomicLong();
     
    //----------------------------- DAO --------------------------------------
    private ModulesDAO dao = new ModulesDAO();

    public ModulesServiceImpl() {
    }
    
    //----------------------- Agregar nuevo registro ---------------------------------
    @Override
    public String save(Modules obj) {
        Map<String,Object> result = new HashMap<String,Object>();
        String message="";
        if ( obj==null ) {
            message="Se espero un objeto user en formato JSON";
        } else if ( obj.getDescription()==null ) {
            message="El campo description no puede ser Nulo";
        } else if ( obj.getName()==null ) {
            message="El campo Name no puede ser Nulo";
        } else if ( obj.getDescription().length()==0 ) {
            message="El campo Description no puede estar vacio";
        } else if ( obj.getName().length()==0 ) {
            message="El campo Name no puede estar vacio";
        } else if ( findName(obj.getName()) ) {
            message="Ya existe un modulo con el mismo nombre";
        } else {
            //--- AtCreated fecha de creación del registro
            dao.add(obj);
            //--- verifica si el modulo se registro satisfactoriamente ---
            if ( !findName(obj.getName()) ) {
                message="El registro no se pudo guardar, ocurrio un error inesperado.";
            }
        }
        //-------------- si ocurrio un error la variable contiene el mensaje de error ---------------
        
        return message;
    }
 
    //------------------- Actualizar los datos de un registro existente --------------------------
    @Override
    public String update(Modules obj) {
        Map<String,Object> result = new HashMap<String,Object>();
        Modules current = null;
        String message="";
        
        if ( obj==null ) {
            message="Se espero un objeto user en formato JSON";
        } else if ( obj.getDescription()==null ) {
            message="El campo description no puede ser Nulo";
        } else if ( obj.getName()==null ) {
            message="El campo Name no puede ser Nulo";
        } else if ( obj.getDescription().length()==0 ) {
            message="El campo Description no puede estar vacio";
        } else if ( obj.getName().length()==0 ) {
            message="El campo Name no puede estar vacio";
        } else {
            //--- obtiene el registro con toda su info para luego editar ---
            current = findById(obj.getId());
            if (current!=null) {
                //--- se reemplaza solo los campos obtenidos y que no vengan null desde el front
                if (obj.getName()!=null) current.setName(obj.getName());
                if (obj.getDescription()!=null) current.setDescription(obj.getDescription());
                if (obj.getIcon()!=null) current.setIcon(obj.getIcon());
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
            int i = dao.delete(id);
            result = i==1;
        } catch ( Exception e ) {
            System.out.println("Error in Modules delete: " + e.getMessage());
        }
        
        return result;
    }
 
    //--------------------- FIND BY ID BOOLEAN --------------------------
    @Override
    public boolean findId(int id) {
        // se consulta en la BD si el id del modulo existe y es valido
        return dao.findById(id)!=null;
    }
    
    //--------------------- FIND BY NAME BOOLEAN --------------------------
    @Override
    public boolean findName(String name) {
        // se consulta en la BD si el name del modulo existe y es valido
        return dao.findByName(name)!=null;
    }
    
    //--------------------- FIND BY ID OBJECT BRAND --------------------------
    @Override
    public Modules findById(int id) {
        Modules result = null;
        try {
            // Se busca en la bd los datos del usuario por Id.
            result = dao.findById(id);
        } catch ( Exception e ) {
            System.out.println("Error in Modules findById: " + e.getMessage());
        }
        
        return result;
    }
    
    //--------------------- FIND BY NAME OBJECT BRAND --------------------------
    @Override
    public Modules findByName(String name) {
        Modules result = null;
        try {
            // Se busca en la bd los datos del usuario por Email.
            result = dao.findByName(name);
        } catch ( Exception e ) {
            System.out.println("Error in Modules findByName: " + e.getMessage());
        }
        
        return result;
    }
    
    //------- retorna un listado de registros almacenados en la tabla  ---------
    @Override
    public List getAll(){
        List list = null;
        try {
            // Se consulta en la bd los grupos de una compañia.
            list = dao.getAll();
        } catch ( Exception e ) {
            System.out.println("Error in Modules getAll: " + e.getMessage());
        }
        return list;
    }
    
}
