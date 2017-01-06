/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;

import com.beecode.toolvendor.dao.ZoneDAO;
import com.beecode.toolvendor.interfaces.ZoneService;
import com.beecode.toolvendor.model.Company;
import com.beecode.toolvendor.model.User;
import com.beecode.toolvendor.model.Zone;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author luisvespa
 */
public class ZoneServiceImpl implements ZoneService {

    private static final AtomicLong counter = new AtomicLong();
     
    private List<Zone> list;
    
    private final ZoneDAO dao = new ZoneDAO();

    public ZoneServiceImpl() {
    }
    
    //----------------------- Agregar nuevo registro ---------------------------------
    @Override
    public String save(Zone obj) {
        Zone current = null;
        String message="";
        try {
            if ( obj==null ) {
                message="Se espero un objeto user en formato JSON";
            } else if ( obj.getDescription()==null ) {
                message="El campo Description no puede estar vacio";
            } else if ( obj.getDescription().length()==0 ) {
                message="El campo Description no puede estar vacio";
            } else if ( findName(obj.getDescription(), obj.getCompanyId()) ) {
                message="Ya existe un registro con el mismo nombre";
            } else {
                //--- AtCreated fecha de creación del registro
                dao.add(obj);
                //--- obtiene el usuario registrado con toda su info para la respuesta ---
                current = findByName(obj.getDescription(), obj.getCompanyId());
                if ( current==null) {
                    message="El registro no se pudo guardar, ocurrio un error inesperado.";
                }
            }
        } catch ( Exception e ) {
            System.out.println("Error in zone save: " + e.getMessage());
        }
        //-------------- si ocurrio un error la variable contiene el mensaje de error ---------------
        
        return message;
    }
 
    //------------------- Actualizar los datos de un registro existente --------------------------
    @Override
    public String update(Zone obj) {
        Zone current = null;
        String message="";
        try {
            if ( obj==null ) {
                message="Se espero un objeto Zone en formato JSON";
            } else if ( obj.getDescription()==null ) {
                message="El campo Description no puede estar vacio";
            } else if ( obj.getId()==0 ) {
                message="El campo ZoneId no puede estar vacio";
            } else if ( obj.getDescription().length()==0 ) {
                message="El campo Description no puede estar vacio";
            } else {
                //--- obtiene el registro con toda su info para luego editar ---
                current = findById(obj.getId(), obj.getCompanyId());
                if (current!=null) {
                    //--- se reemplaza solo los campos obtenidos y que no vengan null desde el front
                    if (obj.getId()!=0) current.setId(obj.getId());
                    if (obj.getDescription()!=null) current.setDescription(obj.getDescription());
                    //--- se ejecuta el update en la capa de datos ---
                    dao.update(current);
                } else {
                    message="No se encontro un registro asociado para este id";
                }
            }
        } catch ( Exception e ) {
            System.out.println("Error in zone update: " + e.getMessage());
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
            System.out.println("Error in zone delete: " + e.getMessage());
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
    
    //--------------------- FIND BY ID OBJECT ZONE --------------------------
    @Override
    public Zone findById(int id, int companyId) {
        Zone result = null;
        try {
            // Se busca en la bd los datos del usuario por Id.
            result = dao.findById(id, companyId);
        } catch ( Exception e ) {
            System.out.println("Error in Zone findById: " + e.getMessage());
        }
        
        return result;
    }
    
    //--------------------- FIND BY NAME OBJECT ZONE --------------------------
    @Override
    public Zone findByName(String name, int companyId) {
        Zone result = null;
        try {
            // Se busca en la bd los datos del usuario por Email.
            result = dao.findByName(name, companyId);
        } catch ( Exception e ) {
            System.out.println("Error in Zone findByName: " + e.getMessage());
        }
        
        return result;
    }
 
    @Override
    public List getAllByCompany(Integer companyId) {
        List list = null;
        try {
            // Se consulta en la bd los country registrados.
            list = dao.getAllByCompany(companyId);
        } catch ( Exception e ) {
            System.out.println("Error in Zones getAll: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List getAllByUser(Integer userId) {
        List list = null;
        try {
            // Se consulta en la bd los country registrados.
            list = dao.getAllByUser(userId);
        } catch ( Exception e ) {
            System.out.println("Error in Zones getAll: " + e.getMessage());
        }
        return list;
    }
    
}
