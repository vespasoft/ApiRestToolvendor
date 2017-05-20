/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.controller;

import com.beecode.toolvendor.model.Order;
import com.beecode.toolvendor.model.User;
import com.beecode.toolvendor.service.OrderDetailServiceImpl;
import com.beecode.toolvendor.service.OrderServiceImpl;
import com.beecode.toolvendor.service.SecurityServiceImpl;
import com.beecode.toolvendor.util.AppPreferences;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author luisvespa
 */
@Controller
public class OrderController {
    
    // ------------------------------- OBJECTS ----------------------------------------
    Map<String,Object> result = new HashMap<String,Object>();
    
    // ------------------------------- SERVICES ---------------------------------------
    OrderServiceImpl service = new OrderServiceImpl();
    SecurityServiceImpl security = new SecurityServiceImpl();
    
    //-------------------Retrieve All Order--------------------------------------------------------
    
    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getAllOrder(@RequestHeader(value="Access-Token") String accessToken) {
        
        result = new HashMap<String,Object>();
        System.out.println("Fetching Header Access Token " + accessToken);
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
             //---- Obtiene todos los customer registrados en el sistema -----
            System.out.println("List all order by company " + session.getCompany().getId());
            List list = service.getAllByCompany(session.getCompany().getId());
            if ( list==null ) {
                result.put("success", Boolean.FALSE);
                result.put("message", AppPreferences.MESSAGE_HTTP_IS_EMPTY);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            } else {
                result.put("success", Boolean.TRUE);
                result.put("result", list);
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        }
        
    }
    
    //-------------------Retrieve Single Order--------------------------------------------------------
     
    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getOrder(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") Integer id) {
        Map<String,Object> result = new HashMap<String,Object>();
        
        result = new HashMap<String,Object>();
        System.out.println("Fetching Header Access Token " + accessToken);
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            //Use headers to get the information about all the request headers
            System.out.println("Order with id " + id);
            Order object = service.findById(id, session.getCompany());
            if ( object!=null ) {
                result.put("success", Boolean.TRUE);
                result.put("result", object);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                result.put("success", Boolean.FALSE);
                result.put("message", AppPreferences.MESSAGE_HTTP_ID_FAILED_NOT_ACCESS);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
        }
    }
    
    //-------------------Create a Order --------------------------------------------------------
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> createOrder(@RequestHeader(value="Access-Token") String accessToken, @RequestBody Order order,  UriComponentsBuilder ucBuilder) {
        
        result = new HashMap<String,Object>();
        System.out.println("Fetching Header Access Token " + accessToken);
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            // Se forza a guardar el registro relacionado con el Token
            order.setCompanyId(session.getCompany().getId());
            //----------------------------- crea un nuevo registro -------------------------------
            String message = service.save(order, session.getCompany());
            if ( message.isEmpty() ) {
                result.put("success", Boolean.TRUE);
                result.put("message", AppPreferences.MESSAGE_HTTP_SAVE_OK);
                return new ResponseEntity<>(result, HttpStatus.CREATED);
            } else {
                result.put("success", Boolean.FALSE);
                result.put("message", message);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
        }
    }
     
    // ------------------- Update a Order --------------------------------------------------------
    @RequestMapping(value = "/order/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String,Object>> updateOrder(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") Integer id, @RequestBody Order order) {
        
        result = new HashMap<String,Object>();
        System.out.println("Fetching Header Access Token " + accessToken);
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            System.out.println("Updating order " + id);
            //------ se verifica que el Id existe y pertenece a la misma empresa ----
            if ( service.findId(id, session.getCompany()) ) {
                // Se forza a guardar el registro relacionado con el Token
                order.setCompanyId(session.getCompany().getId());
                //------ se actualiza el registro en la base de datos ----
                String message = service.update(order, session.getCompany());
                if ( message.isEmpty() ) {
                    Order object = service.findById(id, session.getCompany());
                    if ( object==null ) {
                        result.put("success", Boolean.FALSE);
                        result.put("message", AppPreferences.MESSAGE_HTTP_ID_FAILED);
                        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
                    } else {
                        result.put("success", Boolean.TRUE);
                        result.put("message", AppPreferences.MESSAGE_HTTP_UPDATE_OK);
                        result.put("result", object);
                        return new ResponseEntity<>(result, HttpStatus.OK);
                    }
                } else {
                    result.put("success", Boolean.FALSE);
                    result.put("message", message);
                    return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
                }
            } else {
                result.put("success", Boolean.FALSE);
                result.put("message", AppPreferences.MESSAGE_HTTP_ID_FAILED);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
        }
        
    }
 
    //------------------- Delete a Order --------------------------------------------------------
    
    @RequestMapping(value = "/order/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String,Object>> deleteOrder(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") Integer id) {
        result = new HashMap<String,Object>();
        System.out.println("Fetching Header Access Token " + accessToken);
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            System.out.println("Fetching & Deleting Order with id " + id);
            //------ se obtiene el registro de la busqueda ----
            Order object = service.findById(id, session.getCompany());
            if ( object!=null ) {
                //------ se elimina el registro en la base de datos ----
                boolean success = service.delete(id);
                if ( success ) {
                    result.put("success", Boolean.TRUE);
                    result.put("message", AppPreferences.MESSAGE_HTTP_DELETE_OK);
                    return new ResponseEntity<>(result, HttpStatus.OK);
                } else {
                    result.put("success", Boolean.FALSE);
                    result.put("message", AppPreferences.MESSAGE_HTTP_DELETE_FAILED);
                    return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
                }
            } else {
                result.put("success", Boolean.FALSE);
                result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
        }
    }
    
    //-------------------Retrieve All OrderPicture--------------------------------------------------------
    
    @RequestMapping(value = "/order/{id}/detail", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getAllOrderDetail(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") Integer id) {
        OrderDetailServiceImpl orderserv = new OrderDetailServiceImpl();
        result = new HashMap<String,Object>();
        System.out.println("Fetching Header Access Token " + accessToken);
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            System.out.println("List all orderpicture with orderId " + id);
            // se verifica que el id de la ordera se valido
            Order object = service.findById(id, session.getCompany());
            if ( object!=null ) {
                //---- Obtiene todas las pictures de la ordera -----
                List list = orderserv.getAllByOrder(id);
                if ( list==null ) {
                    result.put("success", Boolean.FALSE);
                    result.put("message", AppPreferences.MESSAGE_HTTP_IS_EMPTY);
                    return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
                } else {
                    result.put("success", Boolean.TRUE);
                    result.put("result", list);
                    return new ResponseEntity<>(result, HttpStatus.OK);
                }
            } else {
                result.put("success", Boolean.FALSE);
                result.put("message", AppPreferences.MESSAGE_HTTP_ID_FAILED_NOT_ACCESS);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
        }
        
    }
    
}
