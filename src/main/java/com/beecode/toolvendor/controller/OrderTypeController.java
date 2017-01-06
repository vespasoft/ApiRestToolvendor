/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.controller;

import com.beecode.toolvendor.model.User;
import com.beecode.toolvendor.model.OrderType;
import com.beecode.toolvendor.service.SecurityServiceImpl;
import com.beecode.toolvendor.service.UserServiceImpl;
import com.beecode.toolvendor.service.OrderTypeServiceImpl;
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
public class OrderTypeController {
    // ------------------------------- OBJECTS ----------------------------------------
    Map<String,Object> result = new HashMap<String,Object>();
    
    // ------------------------------- SERVICES ----------------------------------------
    UserServiceImpl userserv = new UserServiceImpl();
    SecurityServiceImpl security = new SecurityServiceImpl();
    OrderTypeServiceImpl service = new OrderTypeServiceImpl();
    
    //-------------------Retrieve All Users--------------------------------------------------------
     
    @RequestMapping(value = "/ordertype", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getAllOrderType() {
        
        result = new HashMap<String,Object>();
        
        System.out.println("Get all ordertype... ");
        //---- obtiene listado de registros ----
        List list = service.getAll();
        // Verificamos que la lista no esta vacia
        if ( !list.isEmpty() ) {
            result.put("success", Boolean.TRUE);
            result.put("result", list);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_HTTP_IS_EMPTY);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }
    
    //-------------------Retrieve Single User--------------------------------------------------------
     
    @RequestMapping(value = "/ordertype/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getOrderType( @PathVariable("id") int id) {
        
        
            //Use headers to get the information about all the request headers
            System.out.println("Fetching OrderType with id " + id);
            
            //------ se obtiene el registro de la busqueda ----
            OrderType object = service.findById(id);
            if ( object!=null ) {
                result.put("success", Boolean.TRUE);
                result.put("result", object);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                result.put("success", Boolean.FALSE);
                result.put("message", AppPreferences.MESSAGE_HTTP_ID_FAILED);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
    }
    
    //-------------------Create a OrderType--------------------------------------------------------
    @RequestMapping(value = "/ordertype", method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> createOrderType(@RequestHeader(value="Access-Token") String accessToken, @RequestBody OrderType ordertype,  UriComponentsBuilder ucBuilder) {
        result = new HashMap<String,Object>();
        
        System.out.println("Fetching Header Access Token " + accessToken);
        // Usamos la clase security para validar la permisología del usuario
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            //----------------------------- crea un nuevo registro -------------------------------
            String message = service.save(ordertype);
            if ( message.isEmpty() ) {
                OrderType object = service.findByName(ordertype.getName());
                if ( object==null ) {
                    result.put("success", Boolean.FALSE);
                    result.put("message", AppPreferences.MESSAGE_HTTP_SAVE_FAILED);
                    return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
                } else {
                    result.put("success", Boolean.TRUE);
                    result.put("message", AppPreferences.MESSAGE_HTTP_SAVE_OK);
                    result.put("result", object);
                    return new ResponseEntity<>(result, HttpStatus.CREATED);
                }
            } else {
                result.put("success", Boolean.FALSE);
                result.put("message", message);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
        }
        //HttpHeaders headers = new HttpHeaders();
        //headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(currentUser.getUserId()).toUri());
        
    }
     
    // ------------------- Update a OrderType --------------------------------------------------------
    @RequestMapping(value = "/ordertype/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String,Object>> updateOrderType(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id, @RequestBody OrderType ordertype) {
        
        System.out.println("Fetching Header Access Token " + accessToken);
        result = new HashMap<String,Object>();
        // Usamos la clase security para validar la permisología del usuario
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            System.out.println("Updating Usertype " + id);
            //------ se verifica que el Id existe y pertenece a la misma empresa ----
            if ( service.findId(id) ) {
                //------ se actualiza el registro en la base de datos ----
                String message = service.update(ordertype);
                if ( message.isEmpty() ) {
                    OrderType object = service.findById(id);
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
 
    //------------------- Delete a User --------------------------------------------------------
    
    @RequestMapping(value = "/ordertype/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String,Object>> deleteUser(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id) {
        
        result = new HashMap<String,Object>();
        System.out.println("Fetching Header Access Token " + accessToken);
        // Usamos la clase security para validar la permisología del usuario
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            System.out.println("Fetching & Deleting Usertype with id " + id);
            //------ se obtiene el registro de la busqueda ----
            OrderType object = service.findById(id);
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
                result.put("message", AppPreferences.MESSAGE_HTTP_ID_FAILED);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
            
        }
    }
    
}
