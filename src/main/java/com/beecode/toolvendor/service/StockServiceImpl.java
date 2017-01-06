/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;

import com.beecode.toolvendor.dao.StockDAO;
import com.beecode.toolvendor.interfaces.StockService;
import com.beecode.toolvendor.model.Stock;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author luisvespa
 */
public class StockServiceImpl implements StockService {

    private static final AtomicLong counter = new AtomicLong();
     
    //------------------------------- SERVICES -----------------------------------------
    private final ProductServiceImpl productserv = new ProductServiceImpl();
    private final CellarServiceImpl cellarserv = new CellarServiceImpl();
    
    //------------------------------- DAO -----------------------------------------
    private final StockDAO dao = new StockDAO();

    public StockServiceImpl() {
    }
    
    //----------------------- Agregar nuevo registro ---------------------------------
    public String save(Stock obj, Integer companyId) {
        Stock current = null;
        String message="";
        if ( obj==null ) {
            message="Se espero un objeto Stock en formato JSON";
        } else if ( obj.getProductId()==null ) {
            message="El campo productId no puede ser nullo";
        } else if ( obj.getProductId()==0 ) {
            message="El campo productId no puede ser igual a 0";
        } else if ( obj.getCellarId()==null ) {
            message="El campo cellarId no puede estar vacio";
        } else if ( obj.getCellarId()==0 ) {
            message="El campo cellarId no puede ser igual a 0";    
        } else if ( productserv.findById(obj.getProductId(), companyId) == null ) {
            message="No existe ningun registro con este productId";
        } else if ( !cellarserv.findId(obj.getCellarId(), companyId) ) {
            message="No existe ningun registro con este cellarId";    
        } else {
            //--- AtCreated fecha de creación del registro
            dao.add(obj);
        }
        //-------------- si ocurrio un error la variable contiene el mensaje de error ---------------
        
        return message;
    }
 
    //------------------- Actualizar los datos de un registro existente --------------------------
    public String update(Stock obj, Integer companyId) {
        Stock current = null;
        String message="";
        
        if ( obj==null ) {
            message="Se espero un objeto Group en formato JSON";
        } else if ( obj.getId()==null ) {
            message="El campo Id no puede estar vacio";
        } else if ( obj.getId()==0 ) {
            message="El campo Id no puede estar vacio";
        } else {
            //--- obtiene el registro con toda su info para luego editar ---
            current = findById(obj.getId());
            if (current!=null) {
                //--- se reemplaza solo los campos obtenidos y que no vengan null desde el front
                if (obj.getCellarId()!=null) current.setCellarId(obj.getCellarId());
                if (obj.getId()!=null) current.setId(obj.getId());
                if (obj.getProductId()!=null) current.setProductId(obj.getProductId());
                if (obj.getCost()!=null) current.setCost(obj.getCost());
                if (obj.getQuantity()!=null) current.setQuantity(obj.getQuantity());
                //--- se verifica que el productId sea valido y exista ---
                if ( !productserv.findId(obj.getProductId(), companyId) ) {
                    message="El campo productId no existe o es invalido";
                } else if ( cellarserv.findId(obj.getCellarId(), companyId) ) {
                    message="El campo cellarId no existe o es invalido";
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
            System.out.println("Error in Stock delete: " + e.getMessage());
        }
        
        return result;
    }

    //----- encontrar un registro por id -----
    @Override
    public Stock findById(int id) {
        Stock result = null;
        try {
            // Se busca en la bd los datos del usuario por Id.
            result = dao.findById(id);
        } catch ( Exception e ) {
            System.out.println("Error in Stock findById: " + e.getMessage());
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
        List<Stock> list = null;
        try {
            // Se consulta en la bd las marcas registradas de una compañia.
            list = dao.getAllByProduct(productId);
        } catch ( Exception e ) {
            System.out.println("Error in Stock getAllByProduct: " + e.getMessage());
        }
        return list;
    }
    
}
