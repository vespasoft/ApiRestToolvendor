/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.controller;

import com.beecode.toolvendor.model.OrderDetail;
import com.beecode.toolvendor.model.User;
import com.beecode.toolvendor.service.OrderDetailServiceImpl;
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
public class OrderDetailController {
    
    // ------------------------------- OBJECTS ----------------------------------------
    Map<String,Object> result = new HashMap<String,Object>();
    
    // ------------------------------- SERVICES ---------------------------------------
    OrderDetailServiceImpl service = new OrderDetailServiceImpl();
    SecurityServiceImpl security = new SecurityServiceImpl();
    
    //-------------------Retrieve Single OrderDetail--------------------------------------------------------
     
    @RequestMapping(value = "/orderdetail/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getOrderDetail(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") Integer id) {
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
            System.out.println("OrderDetail with id " + id);
            OrderDetail object = service.findById(id);
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
    
    //-------------------Create a OrderDetail --------------------------------------------------------
    @RequestMapping(value = "/orderdetail", method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> createOrderDetail(@RequestHeader(value="Access-Token") String accessToken, @RequestBody OrderDetail orderdetail,  UriComponentsBuilder ucBuilder) {
        
        result = new HashMap<String,Object>();
        System.out.println("Fetching Header Access Token " + accessToken);
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            //----------------------------- crea un nuevo registro -------------------------------
            String message = service.save(orderdetail, session.getCompany());
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
     
    // ------------------- Update a OrderDetail --------------------------------------------------------
    @RequestMapping(value = "/orderdetail/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String,Object>> updateOrderDetail(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") Integer id, @RequestBody OrderDetail orderdetail) {
        
        result = new HashMap<String,Object>();
        System.out.println("Fetching Header Access Token " + accessToken);
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            System.out.println("Updating orderdetail " + id);
            //------ se verifica que el Id existe y pertenece a la misma empresa ----
            if ( service.findId(id) ) {
                //------ se actualiza el registro en la base de datos ----
                String message = service.update(orderdetail, session.getCompany());
                if ( message.isEmpty() ) {
                    OrderDetail object = service.findById(id);
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
 
    //------------------- Delete a OrderDetail --------------------------------------------------------
    
    @RequestMapping(value = "/orderdetail/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String,Object>> deleteOrderDetail(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") Integer id) {
        result = new HashMap<String,Object>();
        System.out.println("Fetching Header Access Token " + accessToken);
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            System.out.println("Fetching & Deleting OrderDetail with id " + id);
            //------ se obtiene el registro de la busqueda ----
            OrderDetail object = service.findById(id);
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
    
}
