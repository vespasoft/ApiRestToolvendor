/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;

import com.beecode.toolvendor.dao.OrderDetailDAO;
import com.beecode.toolvendor.interfaces.OrderDetailService;
import com.beecode.toolvendor.model.OrderDetail;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author luisvespa
 */
public class OrderDetailServiceImpl implements OrderDetailService {

    private static final AtomicLong counter = new AtomicLong();
     
    //----------------------------- SERVICES -------------------------------------
    private final OrderServiceImpl orderserv = new OrderServiceImpl();
    private final ProductServiceImpl productserv = new ProductServiceImpl();
    
    //----------------------------- DAO ------------------------------------------
    private final OrderDetailDAO dao = new OrderDetailDAO();

    public OrderDetailServiceImpl() {
    }
    
    //----------------------- Agregar nuevo registro ---------------------------------
    @Override
    public String save(OrderDetail obj, Integer companyId) {
        OrderDetail current = null;
        String message="";
        if ( obj==null ) {
            message="Se espero un objeto OrderDetail en formato JSON";
        } else if ( obj.getProduct()==null ) {
            message="El campo Product no puede ser null";
        } else if ( obj.getOrder()==null ) {
            message="El campo Order no puede ser null";
        } else if ( obj.getOrder()!=null && !orderserv.findId(obj.getOrder().getId(), companyId) ) {
            message="El campo OrderId no existe o es invalido.";    
        } else if ( obj.getProduct()!=null && !productserv.findId(obj.getProduct().getId(), companyId) ) {
            message="El campo productId no existe o es invalido.";    
        } else if ( obj.getCant()==null || obj.getCant()==0 ) {
            message="El campo cant no puede ser nullo o igual a 0";
        } else {
            if ( obj.getPrice()==null )
                obj.setPrice(0.00);
            //--- AtCreated fecha de creación del registro
            dao.add(obj);

        }
        //-------------- si ocurrio un error la variable contiene el mensaje de error ---------------
        
        return message;
    }
 
    //------------------- Actualizar los datos de un registro existente --------------------------
    @Override
    public String update(OrderDetail obj, Integer companyId) {
        OrderDetail current = null;
        String message="";
        
        if ( obj==null ) {
            message="Se espero un objeto Group en formato JSON";
        } else if ( obj.getId()==0 ) {
            message="El campo Id no puede estar vacio";
        } else if ( obj.getOrder()!=null && !orderserv.findId(obj.getOrder().getId(), companyId) ) {
            message="El campo OrderId no existe o es invalido.";
        } else {
            //--- obtiene el registro con toda su info para luego editar --- 
            current = dao.findById( obj.getId() );
            if (current!=null) {
                //--- se reemplaza solo los campos obtenidos y que no vengan null desde el front
                if (obj.getCant()!=null) current.setCant(obj.getCant());
                if (obj.getPrice()!=null) current.setPrice(obj.getPrice());
                if (obj.getProduct()!=null) current.setProduct(obj.getProduct());
                
                //--- se ejecuta el update en la capa de datos ---
                dao.update(current);
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
    public OrderDetail findById(int id) {
        OrderDetail result = null;
        try {
            // Se busca en la bd una imagen por Id.
            result = dao.findById(id);
        } catch ( Exception e ) {
            System.out.println("Error in OrderDetail findById: " + e.getMessage());
        }
        
        return result;
    }

    //-----  encontrar un registro por id  -----
    @Override
    public boolean findId(int id) {
         // se consulta en la BD si el id del usuario existe y es valido
        return dao.findById(id)!=null;
    }

    //----- retorna un listado registro por productId -----
    @Override
    public List getAllByOrder(Integer orderId) {
        List<OrderDetail> list = null;
        try {
            // Se consulta en la bd las marcas registradas de una compañia.
            list = dao.getAllByOrder(orderId);
        } catch ( Exception e ) {
            System.out.println("Error in Stock getAllByProduct: " + e.getMessage());
        }
        return list;
    }
    
}
