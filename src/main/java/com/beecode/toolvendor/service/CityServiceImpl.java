/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;

import com.beecode.toolvendor.dao.CityDAO;
import com.beecode.toolvendor.interfaces.CityService;
import com.beecode.toolvendor.model.City;
import com.beecode.toolvendor.model.Country;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author luisvespa
 */
public class CityServiceImpl implements CityService {

    private static final AtomicLong counter = new AtomicLong();
     
    private CityDAO dao = new CityDAO();
    private CountryServiceImpl countryserv = new CountryServiceImpl();

    public CityServiceImpl() {
    }
    
    //----------------------- Agregar nuevo registro ---------------------------------
    @Override
    public String save(City obj) {
        // se actualiza los mapas contra la base de datos
        dao = new CityDAO();
        Map<String,Object> result = new HashMap<String,Object>();
        City current = null;
        String message="";
        try {
            if ( obj==null ) {
                message="Se espero un objeto user en formato JSON";
            } else if ( obj.getCity()==null ) {
                message="El campo City no puede ser nullo";
            } else if ( obj.getCity().length()==0 ) {
                message="El campo Description no puede estar vacio";
            } else if ( obj.getCountry().getId()==0 ) {
                message="El campo countryId no puede ser 0";    
            } else if ( findName(obj.getCity()) ) {
                message="Ya existe un registro en la tabla City con el mismo nombre";
            } else if ( !countryserv.findId(obj.getCountry().getId()) ) {
                message="No existe ningun registro en la tabla Country con este Id.";    
            } else {
                //--- AtCreated fecha de creación del registro
                dao.add(obj);
                //--- obtiene el registro con toda su info para la respuesta ---
                current = dao.findByName(obj.getCity());
                if ( current==null) {
                    message="El registro no se pudo guardar, ocurrio un error inesperado.";
                }
            }
        } catch ( Exception e ) {
            System.out.println("Error in city save: " + e.getMessage());
        }
        //-------------- si ocurrio un error la variable contiene el mensaje de error ---------------
        
        return message;
    }
 
    //------------------- Actualizar los datos de un registro existente --------------------------
    @Override
    public String update(City obj) {
        // se actualiza los mapas contra la base de datos
        dao = new CityDAO();
        Map<String,Object> result = new HashMap<String,Object>();
        City current = null;
        String message="";
        try {
            if ( obj==null ) {
                message="Se espero un objeto user en formato JSON";
            } else if ( obj.getCity()==null ) {
                message="El campo City no puede ser nullo";
            } else if ( obj.getCity().length()==0 ) {
                message="El campo Description no puede estar vacio";
            } else if ( obj.getCountry().getId()==0 ) {
                message="El campo CountryId no puede ser 0";
            } else if ( !countryserv.findId(obj.getCountry().getId()) ) {
                message="No existe ningun registro en la tabla Country con este Id.";        
            } else {
                //--- obtiene el registro con toda su info para luego editar ---
                current = dao.findById(obj.getId());
                if (current!=null) {
                    //--- se reemplaza solo los campos obtenidos y que no vengan null desde el front
                    if (obj.getId()!=0) current.setId(obj.getId());
                    if (obj.getCity()!=null) current.setCity(obj.getCity());
                    if (obj.getCountry().getId()!=null) current.setCountry(obj.getCountry());
                    //--- se ejecuta el update en la capa de datos ---
                    dao.update(current);
                } else {
                    message="No se encontro un registro asociado para este id";
                }
            }
        } catch ( Exception e ) {
            System.out.println("Error in city update: " + e.getMessage());
        }
        
        return message;
    }
 
    @Override
    public boolean delete(int id) {
        // se actualiza los mapas contra la base de datos
        dao = new CityDAO();
        boolean result = false;
        try {
            City obj = dao.findById(id);
            if (obj != null) {
                int i = dao.delete(id);
                result = i==1;
            }
        } catch ( Exception e ) {
            System.out.println("Error in city delete: " + e.getMessage());
        }
        
        return result;
    }

 
    //--------------------- FIND BY ID BOOLEAN --------------------------
    @Override
    public boolean findId(int id) {
        // se actualiza los mapas contra la base de datos
        dao = new CityDAO();
        // se consulta en la BD si el id del usuario existe y es valido
        return dao.findById(id)!=null;
    }
    
    //--------------------- FIND BY NAME BOOLEAN --------------------------
    @Override
    public boolean findName(String name) {
        // se actualiza los mapas contra la base de datos
        dao = new CityDAO();
        // se consulta en la BD si el name del usuario existe y es valido
        return dao.findByName(name)!=null;
    }
    
    //--------------------- FIND BY ID OBJECT BRAND --------------------------
    @Override
    public City findById(int id) {
        // se actualiza los mapas contra la base de datos
        dao = new CityDAO();
        City result = null;
        try {
            // Se busca en la bd los datos del usuario por Id.
            result = dao.findById(id);
        } catch ( Exception e ) {
            System.out.println("Error in city findById: " + e.getMessage());
        }
        
        return result;
    }
    
    //--------------------- FIND BY NAME OBJECT BRAND --------------------------
    @Override
    public City findByName(String name) {
        // se actualiza los mapas contra la base de datos
        dao = new CityDAO();
        City result = null;
        try {
            // Se busca en la bd los datos del usuario por Email.
            result = dao.findByName(name);
        } catch ( Exception e ) {
            System.out.println("Error in city findByName: " + e.getMessage());
        }
        
        return result;
    }
    
    @Override
    public List getAllByCountry(Country country) {
        // se actualiza los mapas contra la base de datos
        dao = new CityDAO();
        List list = null;
        try {
            // Se consulta en la bd los country registrados.
            list = dao.getAllByCountry(country);
        } catch ( Exception e ) {
            System.out.println("Error in country getAll: " + e.getMessage());
        }
        return list;
    }
    
    
}
