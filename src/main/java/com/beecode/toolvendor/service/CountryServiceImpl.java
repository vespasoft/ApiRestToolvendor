/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;

import com.beecode.toolvendor.dao.CityDAO;
import com.beecode.toolvendor.dao.CountryDAO;
import com.beecode.toolvendor.interfaces.CountryService;
import com.beecode.toolvendor.model.Country;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author luisvespa
 */
public class CountryServiceImpl implements CountryService {

    private static final AtomicLong counter = new AtomicLong();
     
    private CountryDAO dao = new CountryDAO();
    private CityDAO citydao = new CityDAO();

    public CountryServiceImpl() {
    }
    
    //----------------------- Agregar nuevo registro ---------------------------------
    @Override
    public String save(Country obj) {
        Country current = null;
        String message="";
        try {
            
        } catch ( Exception e ) {
            System.out.println("Error in country save: " + e.getMessage());
        }
        if ( obj==null ) {
            message="Se espero un objeto user en formato JSON";
        } else if ( obj.getName()==null ) {
            message="El campo Country no puede estar vacio";
        } else if ( obj.getName().length()==0 ) {
            message="El campo Description no puede estar vacio";
        } else if ( findName(obj.getName()) ) {
            message="Ya existe un pais con el mismo nombre";
        } else {
            //--- AtCreated fecha de creación del registro
            dao.add(obj);
            // se actualiza los mapas contra la base de datos
            dao = new CountryDAO();
            //--- obtiene el registro con toda su info para la respuesta ---
            current = dao.findByName(obj.getName());
            if ( current==null) {
                message="El registro no se pudo guardar, ocurrio un error inesperado.";
            }
        }
        //-------------- si ocurrio un error la variable contiene el mensaje de error ---------------
        
        return message;
    }
 
    //------------------- Actualizar los datos de un registro existente --------------------------
    @Override
    public String update(Country obj) {
        Country current = null;
        String message="";
        try {
            if ( obj==null ) {
                message="Se espero un objeto Country en formato JSON";
            } else if ( obj.getName()==null ) {
                message="El campo Country no puede estar vacio";
            } else if ( obj.getName().length()==0 ) {
                message="El campo Description no puede estar vacio";
            } else {
                //--- obtiene el registro con toda su info para luego editar ---
                current = dao.findById(obj.getId());
                if (current!=null) {
                    //--- se reemplaza solo los campos obtenidos y que no vengan null desde el front
                    if (obj.getId()!=0) current.setId(obj.getId());
                    if (obj.getName()!=null) current.setName(obj.getName());
                    //--- se ejecuta el update en la capa de datos ---
                    dao.update(current);
                } else {
                    message="No se encontro un registro asociado para este id";
                }
            }
        } catch ( Exception e ) {
            System.out.println("Error in country update: " + e.getMessage());
        }
        
        return message;
    }
 
    @Override
    public boolean delete(int id) {
        boolean result = false;
        try {
            Country obj = dao.findById(id);
            if (obj != null) {
                int i = dao.delete(id);
                result = i==1;
            }
        } catch ( Exception e ) {
            System.out.println("Error in country delete: " + e.getMessage());
        }
        
        return result;
    }
    
    //--------------------- FIND BY ID BOOLEAN --------------------------
    @Override
    public boolean findId(int id) {
        // se actualiza los mapas contra la base de datos
        dao = new CountryDAO();
        // se consulta en la BD si el id del usuario existe y es valido
        return dao.findById(id)!=null;
    }
    
    //--------------------- FIND BY NAME BOOLEAN --------------------------
    @Override
    public boolean findName(String name) {
        // se actualiza los mapas contra la base de datos
        dao = new CountryDAO();
        // se consulta en la BD si el name del usuario existe y es valido
        return dao.findByName(name)!=null;
    }
    
    //--------------------- FIND BY ID OBJECT BRAND --------------------------
    @Override
    public Country findById(int id) {
        // se actualiza los mapas contra la base de datos
        dao = new CountryDAO();
        Country result = null;
        try {
            // Se busca en la bd los datos del usuario por Id.
            result = dao.findById(id);
        } catch ( Exception e ) {
            System.out.println("Error in country findById: " + e.getMessage());
        }
        
        return result;
    }
    
    //--------------------- FIND BY NAME OBJECT BRAND --------------------------
    @Override
    public Country findByName(String name) {
        Country result = null;
        try {
            // Se busca en la bd los datos del usuario por Email.
            result = dao.findByName(name);
        } catch ( Exception e ) {
            System.out.println("Error in country findByName: " + e.getMessage());
        }
        
        return result;
    }
    
    @Override
    public List getAll() {
        // se actualiza los mapas contra la base de datos
        dao = new CountryDAO();
        List list = null;
        try {
            // Se consulta en la bd los country registrados.
            list = dao.getAll();
        } catch ( Exception e ) {
            System.out.println("Error in country getAll: " + e.getMessage());
        }
        return list;
    }
    
}
