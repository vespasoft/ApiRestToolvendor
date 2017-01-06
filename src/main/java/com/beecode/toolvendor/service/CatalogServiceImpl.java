/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;

import com.beecode.toolvendor.dao.CatalogDAO;
import com.beecode.toolvendor.interfaces.CatalogService;
import com.beecode.toolvendor.model.Catalog;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author luisvespa
 */
public class CatalogServiceImpl implements CatalogService {

    private static final AtomicLong counter = new AtomicLong();
    
    //------------------------------- SERVICES -----------------------------------------
    private final ProductServiceImpl productserv = new ProductServiceImpl();
    
    //------------------------------- DAO -----------------------------------------
    private final CatalogDAO dao = new CatalogDAO();

    public CatalogServiceImpl() {
    }
    
    //----------------------- Agregar nuevo registro ------------------------------
    @Override
    public String save(Catalog obj) {
        Catalog current = null;
        String message="";
        if ( obj==null ) {
            message="Se espero un objeto Catalog en formato JSON";
        } else if ( obj.getProductId()==null ) {
            message="El objecto Product no puede ser nulo";
        } else if ( obj.getDescription()==null ) {
            message="El campo Description no puede ser nulo";
        } else if ( obj.getDescription().length()==0 ) {
            message="El campo Description no puede estar vacio";    
        } else if ( obj.getPhoto()==null ) {
            message="El campo photo no puede ser nulo";    
        } else if ( !productserv.findId(obj.getProductId()) ) {
            message="No existe un registro con este productId.";
        } else {
            //--- AtCreated fecha de creación del registro
            dao.add(obj);
            
        }
        //-------------- si ocurrio un error la variable contiene el mensaje de error ---------------
        
        return message;
    }
 
    //------------------- Actualizar los datos de un registro existente --------------------------
    @Override
    public String update(Catalog obj) {
        Catalog current = null;
        String message="";
        
        if ( obj==null ) {
            message="Se espero un objeto Catalog en formato JSON";
        } else if ( obj.getId()==0 ) {
            message="El campo Id no puede estar vacio";
        } else {
            //--- obtiene el registro con toda su info para luego editar ---
            current = findById(obj.getId());
            if (current!=null) {
                //--- se reemplaza solo los campos obtenidos y que no vengan null desde el front
                if (obj.getProductId()!=null) current.setProductId(obj.getProductId());
                if (obj.getDescription()!=null) current.setDescription(obj.getDescription());
                if (obj.getPhoto()!=null) current.setPhoto(obj.getPhoto());
                //--- obtiene el usuario a partir del userId ---
                if ( !productserv.findId(obj.getProductId()) ) {
                    message="El campo productId no existe o es invalido";
                } else {
                    //--- se ejecuta el update en la capa de datos ---
                    dao.update(current);
                }
            } else {
                message="No se encontro un registro asociado para este id";
            }
        }
        
        return message;
    }

    //----- eliminar un registro por id -----
    @Override
    public boolean delete(int id) {
        boolean result = false;
        try {
            int i = dao.delete(id);
            result = i==1;
        } catch ( Exception e ) {
            System.out.println("Error in catalog delete: " + e.getMessage());
        }
        
        return result;
    }

    //----- encontrar un registro por id -----
    @Override
    public Catalog findById(int id) {
        Catalog result = null;
        try {
            // Se busca en la bd los datos del usuario por Id.
            result = dao.findById(id);
        } catch ( Exception e ) {
            System.out.println("Error in Catalog findById: " + e.getMessage());
        }
        
        return result;
    }

    //----- encontrar un registro por id -----
    @Override
    public boolean findId(int id) {
         // se consulta en la BD si el id del usuario existe y es valido
        return dao.findById(id)!=null;
    }

    //----- retorna un listado registro por productId -----
    @Override
    public List getAllByProduct(Integer productId) {
        List<Catalog> list = null;
        try {
            // Se consulta en la bd las marcas registradas de una compañia.
            list = dao.getAllByProduct(productId);
        } catch ( Exception e ) {
            System.out.println("Error in Catalog getAllByProduct: " + e.getMessage());
        }
        return list;
    }
    
}
