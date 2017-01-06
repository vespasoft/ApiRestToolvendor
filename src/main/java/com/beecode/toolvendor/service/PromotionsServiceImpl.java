/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;

import com.beecode.toolvendor.dao.PromotionsDAO;
import com.beecode.toolvendor.interfaces.PromotionsService;
import com.beecode.toolvendor.model.Promotions;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author luisvespa
 */
public class PromotionsServiceImpl implements PromotionsService {

    private static final AtomicLong counter = new AtomicLong();
    
    //------------------------------- SERVICES ------------------------------------
    private final ProductServiceImpl productserv = new ProductServiceImpl();
    
    //------------------------------- DAO -----------------------------------------
    private PromotionsDAO dao = new PromotionsDAO();

    public PromotionsServiceImpl() {
    }
    
    //----------------------- Agregar nuevo registro ------------------------------
    @Override
    public String save(Promotions obj, Integer companyId) {
        String message="";
        if ( obj==null ) {
            message="Se espero un objeto Promotions en formato JSON";
        } else if ( obj.getProduct()==null ) {
            message="El campo ProductId no puede ser nulo";
        } else if ( obj.getStartDate()==null ) {
            message="El campo StartDate no puede ser nulo";
        } else if ( obj.getExpirationAt()==null ) {
            message="El campo ExpirationAt no puede estar vacio";    
        } else if ( obj.getDiscount()==null ) {
            message="El campo Discount no puede ser nulo";    
        } else if ( !productserv.findId(obj.getProduct().getId(), companyId) ) {
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
    public String update(Promotions obj, Integer companyId) {
        Promotions current = null;
        String message="";
        
        if ( obj==null ) {
            message="Se espero un objeto Promotions en formato JSON";
        } else if ( obj.getId()==0 ) {
            message="El campo Id no puede estar vacio";
        } else {
            //--- obtiene el registro con toda su info para luego editar ---
            current = findById(obj.getId());
            if ( current!=null) {
                //--- se reemplaza solo los campos obtenidos y que no vengan null desde el front
                if (obj.getProduct()!=null) current.setProduct(obj.getProduct());
                if (obj.getStartDate()!=null) current.setStartDate(obj.getStartDate());
                if (obj.getExpirationAt()!=null) current.setExpirationAt(obj.getExpirationAt());
                if (obj.getDiscount()!=null) current.setDiscount(obj.getDiscount());
                
                //--- obtiene el usuario a partir del userId ---
                if ( !productserv.findId(obj.getProduct().getId(), companyId) ) {
                    message="No existe un registro con este productId";
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
    public Promotions findById(int id) {
        Promotions result = null;
        try {
            // Se busca en la bd los datos del usuario por Id.
            result = dao.findById(id);
        } catch ( Exception e ) {
            System.out.println("Error in Promotions findById: " + e.getMessage());
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
    public List getAllByCompany(Integer companyId) {
        List<Promotions> list = null;
        try {
            // Se consulta en la bd las marcas registradas de una compañia.
            list = dao.getAllByCompany(companyId);
        } catch ( Exception e ) {
            System.out.println("Error in Promotions getAllByProduct: " + e.getMessage());
        }
        return list;
    }
    
}
