/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;

import com.beecode.toolvendor.dao.StockDAO;
import com.beecode.toolvendor.interfaces.StockService;
import com.beecode.toolvendor.model.Stock;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author luisvespa
 */
public class StockServiceImpl implements StockService {

    private static final AtomicLong counter = new AtomicLong();
     
    //------------------------------- SERVICES -----------------------------------------
    private ProductServiceImpl productserv;
    private CellarServiceImpl cellarserv;
    
    //------------------------------- DAO -----------------------------------------
    private StockDAO dao;

    public StockServiceImpl() {
        productserv = new ProductServiceImpl();
        cellarserv = new CellarServiceImpl();
        dao = new StockDAO();
    }
    
    //----------------------- Agregar nuevo registro ---------------------------------
    public String save(Stock obj, Integer companyId) {
        productserv = new ProductServiceImpl();
        cellarserv = new CellarServiceImpl();
        dao = new StockDAO();
        Stock current = null;
        String message="";
        if ( obj==null ) {
            message="Se espero un objeto Stock en formato JSON";
        } else if ( obj.getProductId()==null ) {
            message="El campo productId no puede ser nullo";
        } else if ( obj.getProductId()==0 ) {
            message="El campo productId no puede ser igual a 0";
        } else if ( obj.getCellar().getId()==null ) {
            message="El campo cellarId no puede estar vacio";
        } else if ( obj.getCellar().getId()==0 ) {
            message="El campo cellarId no puede ser igual a 0";    
        } else if ( productserv.findById(obj.getProductId(), companyId) == null ) {
            message="No existe ningun registro con este productId";
        } else if ( !cellarserv.findId(obj.getCellar().getId(), companyId) ) {
            message="No existe ningun registro con este cellarId";    
        } else {
            //--- AtCreated fecha de creación del registro
            obj.setCreatedAt(new Date());
            dao.add(obj);
        }
        //-------------- si ocurrio un error la variable contiene el mensaje de error ---------------
        
        return message;
    }
 
    //------------------- Actualizar los datos de un registro existente --------------------------
    public String update(Stock obj, Integer companyId) {
        productserv = new ProductServiceImpl();
        cellarserv = new CellarServiceImpl();
        dao = new StockDAO();
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
                if (obj.getCellar().getId()!=null) current.setCellar(obj.getCellar());
                if (obj.getId()!=null) current.setId(obj.getId());
                if (obj.getProductId()!=null) current.setProductId(obj.getProductId());
                if (obj.getCost()!=null) current.setCost(obj.getCost());
                if (obj.getQuantity()!=null) current.setQuantity(obj.getQuantity());
                //--- se verifica que el productId sea valido y exista ---
                if ( !productserv.findId(obj.getProductId(), companyId) ) {
                    message="El campo productId no existe o es invalido";
                } else if ( !cellarserv.findId(obj.getCellar().getId(), companyId) ) {
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
        dao = new StockDAO();
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
        dao = new StockDAO();
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
        dao = new StockDAO();
         // se consulta en la BD si el id del usuario existe y es valido
        return dao.findById(id)!=null;
    }

    //----- retorna un listado registro por productId -----
    @Override
    public List getAllByProduct(Integer productId) {
        dao = new StockDAO();
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
