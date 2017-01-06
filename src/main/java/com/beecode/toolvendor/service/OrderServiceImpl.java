/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;


import com.beecode.toolvendor.dao.OrderDAO;
import com.beecode.toolvendor.interfaces.OrderService;
import com.beecode.toolvendor.interfaces.OrderService;
import com.beecode.toolvendor.model.Order;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author luisvespa
 */
public class OrderServiceImpl implements OrderService {

    private static final AtomicLong counter = new AtomicLong();
     
    //----------------------------- DAO ------------------------------------------
    private final OrderDAO dao = new OrderDAO();
    //----------------------------- SERVICES -------------------------------------
    private final UserServiceImpl orderserv = new UserServiceImpl();
    private final CustomerServiceImpl cstmrserv = new CustomerServiceImpl();
    private final OrderTypeServiceImpl ordertypeserv = new OrderTypeServiceImpl();
    
    public OrderServiceImpl() {
    }
    
    //----------------------- Agregar nuevo registro ---------------------------------
    @Override
    public String save(Order order) {
        Order currentOrder = null;
        String message="";
        if ( order==null ) {
            message="Se espero un objeto order en formato JSON";
        } else if ( order.getCustomerId()==null ) {
            message="El campo CustomerId no puede ser nullo";
        } else if ( order.getUserId()==null ) {
            message="El campo UserId no puede ser nullo";
        } else if ( order.getOrderType()==null ) {
            message="El campo OrderTypeId no puede ser nullo";
        } else if ( order.getUserId()==0 ) {
            message="El campo UserId no puede ser nullo";
        } else if ( order.getCustomerId()==0 ) {
            message="El campo CustomerId no puede ser nullo";
        } else if ( order.getOrderType().getId()==0 ) {
            message="El campo OrderTypeId no puede estar vacio";
        } else if ( !orderserv.findId(order.getUserId(), order.getCompanyId()) ) {
            message="No existe un registro con este UserId.";
        } else if ( !cstmrserv.findId(order.getCustomerId(), order.getCompanyId()) ) {
            message="No existe un registro con este CustomerId.";   
        } else if ( !ordertypeserv.findId(order.getOrderType().getId()) ) {
            message="No existe un registro con este OrderTypeId.";
        } else {
            //--- Created At fecha de creación del registro
            order.setCreatedAt(new Date() );
            dao.add(order);
        }
        //-------------- si ocurrio un error la variable contiene el mensaje de error ---------------
        
        return message;
    }
 
    //----------------------- Actualizar los datos de un registro existente --------------------------
    @Override
    public String update(Order order) {
        Order currentOrder = null;
        String message="";
        if ( order==null ) {
            message="Se espero un objeto order en formato JSON";
        } else if ( order.getId()==null ) {
            message="El campo Id no puede ser nullo";
        } else if ( order.getId()==0 ) {
            message="El campo Id no puede ser 0";
        } else if ( order.getUserId()!=null && !orderserv.findId(order.getUserId(), order.getCompanyId()) ) {
            message="No existe un registro con este UserId.";
        } else if ( order.getCustomerId()!=null && !cstmrserv.findId(order.getCustomerId(), order.getCompanyId()) ) {
            message="No existe un registro con este CustomerId.";
        } else if ( order.getOrderType()!=null && !ordertypeserv.findId(order.getOrderType().getId()) ) {
            message="No existe un registro con este orderucttypeId.";
        } else {
            //--- obtiene el registro con toda su info para luego editar ---
            currentOrder = dao.findById(order.getId(), order.getCompanyId());
            if (currentOrder!=null) {
                //--- se reemplaza solo los campos obtenidos y que no vengan null desde el front
                if (order.getId()!=null) currentOrder.setId(order.getId());
                if (order.getUserId()!=null) currentOrder.setUserId(order.getUserId());
                if (order.getCustomerId()!=null) currentOrder.setCustomerId(order.getCustomerId());
                if (order.getOrderType()!=null) currentOrder.setOrderType(order.getOrderType());
                //if (order.getOrderPictures()!=null) currentOrder.setOrderPictures(order.getOrderPictures());
                //--- LastUpdate fecha de actualizacion del registro
                currentOrder.setLastUpdate(new Date());
                //--- se ejecuta el update en la capa de datos ---
                dao.update(currentOrder);
            } else {
                message="No se encontro un registro asociado para este id";
            }
        }
        
        return message;
    }
 
    //----------------------------- DELETE USER ----------------------------------
    @Override
    public boolean delete(int id) {
        boolean result = false;
        try {
            int i = dao.delete(id);
            result = i==1;
        } catch ( Exception e ) {
            System.out.println("Error in order delete: " + e.getMessage());
        }
        
        return result;
    }

    //--------------------- FIND BY ID OBJECT USER --------------------------
    @Override
    public Order findById(int id, int companyId) {
        Order result = null;
        try {
            // Se busca en la bd los datos del usuario por Id.
            result = dao.findById(id, companyId);
        } catch ( Exception e ) {
            System.out.println("Error in order findById: " + e.getMessage());
        }
        
        return result;
    }
    
    //--------------------- FIND BY ID BOOLEAN --------------------------
    @Override
    public boolean findId(int id, int companyId) {
        // se consulta en la BD si el id del usuario existe y es valido
        return dao.findById(id, companyId)!=null;
    }
 
    
    //--------------------- GET ALL COMPANY LIST --------------------------
    @Override
    public List getAllByCompany(Integer companyId) {
        List<Order> list = null;
        try {
            // Se consulta en la bd los usuarios registrados de una compañia.
            list = dao.getAllByCompany(companyId);
        } catch ( Exception e ) {
            System.out.println("Error in order getAllByCompany: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List getAllByUser(Integer orderId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getAllByCustomer(Integer customerId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}