/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;

import com.beecode.toolvendor.dao.ConfigEmailDAO;
import com.beecode.toolvendor.interfaces.ConfigEmailService;
import com.beecode.toolvendor.model.ConfigEmail;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author luisvespa
 */
public class ConfigEmailServiceImpl implements ConfigEmailService {

    private static final AtomicLong counter = new AtomicLong();
     
    //------------------------------- DAO --------------------------------------------
    private final ConfigEmailDAO dao = new ConfigEmailDAO();

    public ConfigEmailServiceImpl() {
    }
    
    //----------------------- Agregar nuevo registro ---------------------------------
    @Override
    public String save(ConfigEmail obj) {
        ConfigEmail current = null;
        String message="";
        if ( obj==null ) {
            message="Se espero un objeto user en formato JSON";
        } else if ( obj.getCompanyId()==null ) {
            message="El campo ConfigEmail no puede estar vacio";
        } else if ( obj.getCompanyId()==0 ) {
            message="El campo Description no puede estar vacio";
        } else {
            //--- AtCreated fecha de creación del registro
            dao.add(obj);
            //--- obtiene el registro con toda su info para la respuesta ---
            current = findByCompanyId(obj.getCompanyId());
            if ( current==null) {
                message="El registro no se pudo guardar, ocurrio un error inesperado.";
            }
        }
        //-------------- si ocurrio un error la variable contiene el mensaje de error ---------------
        
        return message;
    }
 
    //------------------- Actualizar los datos de un registro existente --------------------------
    @Override
    public String update(ConfigEmail obj) {
        ConfigEmail current = null;
        String message="";
        
        if ( obj==null ) {
            message="Se espero un objeto ConfigEmail en formato JSON";
       } else if ( obj.getCompanyId()==null ) {
            message="El campo CompanyId no puede estar vacio";
        } else if ( obj.getCompanyId()==0 ) {
            message="El campo CompanyId no puede estar vacio";
        } else if ( obj.getId()==0 ) {
            message="Debe indicar el Id del registro";    
        } else {
            //--- obtiene el registro con toda su info para luego editar ---
            current = findByCompanyId(obj.getCompanyId());
            if (current!=null) {
                //--- se reemplaza solo los campos obtenidos y que no vengan null desde el front
                if (obj.getEmailWellcomeCompany()!=null) current.setEmailWellcomeCompany(obj.getEmailWellcomeCompany());
                if (obj.getEmailWellcomeUser()!=null) current.setEmailWellcomeUser(obj.getEmailWellcomeUser());
                if (obj.getEmailForgot()!=null) current.setEmailForgot(obj.getEmailForgot());
                if (obj.getEmailProgramVisit()!=null) current.setEmailProgramVisit(obj.getEmailProgramVisit());
                if (obj.getEmailOrderCustomer()!=null) current.setEmailOrderCustomer(obj.getEmailOrderCustomer());
                //--- se ejecuta el update en la capa de datos ---
                dao.update(current);
            } else {
                message="No se encontro un registro asociado para este id";
            }
        }
        
        return message;
    }
 
    //--------------------- FIND BY ID BOOLEAN --------------------------
    @Override
    public boolean findCompanyId(int companyId) {
        // se consulta en la BD si el id del usuario existe y es valido
        return dao.findByCompanyId(companyId)!=null;
    }
    
    //--------------------- FIND BY ID OBJECT BRAND --------------------------
    @Override
    public ConfigEmail findByCompanyId(int companyId) {
        ConfigEmail result = null;
        try {
            // Se busca en la bd los datos del usuario por Id.
            result = dao.findByCompanyId(companyId);
        } catch ( Exception e ) {
            System.out.println("Error in ConfigEmail findById: " + e.getMessage());
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
            System.out.println("Error in ConfigEmail delete: " + e.getMessage());
        }
        
        return result;
    }
    
}
