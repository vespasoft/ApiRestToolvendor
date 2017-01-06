/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;

import com.beecode.toolvendor.dao.CategoryDAO;
import com.beecode.toolvendor.interfaces.CategoryService;
import com.beecode.toolvendor.model.Category;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author luisvespa
 */
public class CategoryServiceImpl implements CategoryService {

    private static final AtomicLong counter = new AtomicLong();
    
    //------------------------------- DAO --------------------------------------------
    private final CategoryDAO dao = new CategoryDAO();

    public CategoryServiceImpl() {
    }
    
    //----------------------- Agregar nuevo registro ---------------------------------
    @Override
    public String save(Category obj) {
        Map<String,Object> result = new HashMap<String,Object>();
        Category current = null;
        String message="";
        if ( obj==null ) {
            message="Se espero un objeto user en formato JSON";
        } else if ( obj.getCategory()==null ) {
            message="El campo Category no puede estar vacio";
        } else if ( obj.getCategory().length()==0 ) {
            message="El campo Description no puede estar vacio";
        } else if ( findName(obj.getCategory(), obj.getCompanyId()) ) {
            message="Ya existe un registro con el mismo nombre";
        } else {
            //--- AtCreated fecha de creación del registro
            dao.add(obj);
            //--- obtiene el registro con toda su info para la respuesta ---
            current = findByName(obj.getCategory(), obj.getCompanyId());
            if ( current==null) {
                message="El registro no se pudo guardar, ocurrio un error inesperado.";
            }
        }
        //-------------- si ocurrio un error la variable contiene el mensaje de error ---------------
        
        
        return message;
    }
 
    //------------------- Actualizar los datos de un registro existente --------------------------
    @Override
    public String update(Category obj) {
        Category current = null;
        String message="";
        
        if ( obj==null ) {
            message="Se espero un objeto Category en formato JSON";
        } else if ( obj.getCategory()==null ) {
            message="El campo Category no puede estar vacio";
        } else if ( obj.getCategory().length()==0 ) {
            message="El campo Description no puede estar vacio";
        } else if ( obj.getId()==0 ) {
            message="Debe indicar el Id del registro";   
        } else {
            //--- obtiene el registro con toda su info para luego editar ---
            current = findById(obj.getId(), obj.getCompanyId());
            if (current!=null) {
                //--- se reemplaza solo los campos obtenidos y que no vengan null desde el front
                if (obj.getId()!=0) current.setId(obj.getId());
                if (obj.getCategory()!=null) current.setCategory(obj.getCategory());
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
    public boolean findId(int id, int companyId) {
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
    public Category findById(int id, int companyId) {
        Category result = null;
        try {
            // Se busca en la bd los datos del usuario por Id.
            result = dao.findById(id, companyId);
        } catch ( Exception e ) {
            System.out.println("Error in Category findById: " + e.getMessage());
        }
        
        return result;
    }
    
    //--------------------- FIND BY NAME OBJECT BRAND --------------------------
    @Override
    public Category findByName(String name, int companyId) {
        Category result = null;
        try {
            // Se busca en la bd los datos del usuario por Email.
            result = dao.findByName(name, companyId);
        } catch ( Exception e ) {
            System.out.println("Error in Category findByName: " + e.getMessage());
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
            System.out.println("Error in Category delete: " + e.getMessage());
        }
        
        return result;
    }

    @Override
    public List getAllByCompany(Integer companyId) {
        List<Category> list = null;
        try {
            // Se consulta en la bd las marcas registradas de una compañia.
            list = dao.getAllByCompany(companyId);
        } catch ( Exception e ) {
            System.out.println("Error in Category getAllByCompany: " + e.getMessage());
        }
        return list;
    }
    
}
