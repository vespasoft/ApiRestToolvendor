/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;

import com.beecode.toolvendor.dao.VisitTypeDAO;
import com.beecode.toolvendor.interfaces.VisitTypeService;
import com.beecode.toolvendor.model.VisitType;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author luisvespa
 */
public class VisitTypeServiceImpl implements VisitTypeService {

    private static final AtomicLong counter = new AtomicLong();
    //------------------------------- DAO --------------------------------------------
    private final VisitTypeDAO dao = new VisitTypeDAO();

    public VisitTypeServiceImpl() {
    }
    
    //----------------------- Agregar nuevo registro ---------------------------------
    @Override
    public String save(VisitType obj) {
        VisitType currentType = null;
        String message="";
        if ( obj==null ) {
            message="Se espero un objeto user en formato JSON";
        } else if ( obj.getName()==null ) {
            message="El campo Name no puede ser nulo";
        } else if ( obj.getName().length()==0 ) {
            message="El campo Name no puede estar vacio";
        } else if ( findName(obj.getName(), obj.getCompanyId()) ) {
            message="Ya existe un tipo de visita con el mismo nombre";
        } else {
            //--- AtCreated fecha de creación del registro
            dao.add(obj);
            //--- obtiene el usuario registrado con toda su info para la respuesta ---
            currentType = dao.findByName(obj.getName(), obj.getCompanyId());
            if ( currentType==null) {
                message="El registro no se pudo guardar, ocurrio un error inesperado.";
            }
        }
        //-------------- si ocurrio un error la variable contiene el mensaje de error ---------------
        
        return message;
    }
 
    //------------------- Actualizar los datos de un registro existente --------------------------
    @Override
    public String update(VisitType obj) {
        VisitType currentType = null;
        String message="";
        
        if ( obj==null ) {
            message="Se espero un objeto usertype en formato JSON";
        } else if ( obj.getId()==null ) {
            message="El campo Id no puede ser nulo";
        } else if ( obj.getId()==0 ) {
            message="El campo Id no puede ser 0";
        } else {
            //--- obtiene el registro con toda su info para luego editar ---
            currentType = dao.findById(obj.getId(), obj.getCompanyId());
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
    public boolean findId(int id, int companyId ) {
        // se consulta en la BD si el id del usuario existe y es valido
        return dao.findById(id, companyId)!=null;
    }
    
    //--------------------- FIND BY NAME BOOLEAN --------------------------
    @Override
    public boolean findName(String name, int companyId) {
        // se consulta en la BD si el name del usuario existe y es valido
        return dao.findByName(name, companyId)!=null;
    }
    
    //--------------------- FIND BY ID OBJECT BRAND --------------------------
    @Override
    public VisitType findById(int id, int companyId) {
        VisitType result = null;
        try {
            // Se busca en la bd los datos del usuario por Id.
            result = dao.findById(id, companyId);
        } catch ( Exception e ) {
            System.out.println("Error in VisitType findById: " + e.getMessage());
        }
        
        return result;
    }
    
    //--------------------- FIND BY NAME OBJECT BRAND --------------------------
    @Override
    public VisitType findByName(String name, int companyId) {
        VisitType result = null;
        try {
            // Se busca en la bd los datos del usuario por Email.
            result = dao.findByName(name, companyId);
        } catch ( Exception e ) {
            System.out.println("Error in VisitType findByName: " + e.getMessage());
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
            System.out.println("Error in VisitType delete: " + e.getMessage());
        }
        
        return result;
    }

    @Override
    public List getAllByCompany(Integer companyId) {
        List<VisitType> list = null;
        try {
            // Se consulta en la bd las marcas registradas de una compañia.
            list = dao.getAllByCompany(companyId);
        } catch ( Exception e ) {
            System.out.println("Error in VisitType getAllByCompany: " + e.getMessage());
        }
        return list;
    }

    
}
