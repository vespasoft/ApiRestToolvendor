/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.controller;

import com.beecode.toolvendor.model.UserZone;
import com.beecode.toolvendor.model.User;
import com.beecode.toolvendor.service.UserZoneServiceImpl;
import com.beecode.toolvendor.service.SecurityServiceImpl;
import com.beecode.toolvendor.service.UserZoneServiceImpl;
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
public class UserZoneController {
    // ------------------------------- OBJECTS ----------------------------------------
    Map<String,Object> result = new HashMap<String,Object>();
    
    // ------------------------------- SERVICES ----------------------------------------
    UserZoneServiceImpl service = new UserZoneServiceImpl();
    SecurityServiceImpl security = new SecurityServiceImpl();
    
    //-------------------Retrieve Single UserZone--------------------------------------------------------
     
    @RequestMapping(value = "/userzone/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getUserZone(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id) {
        
        System.out.println("Fetching Header Access Token " + accessToken);
        result = new HashMap<String,Object>();
        // Usamos la clase security para validar la permisología del usuario
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            //Use headers to get the information about all the request headers
            System.out.println("Fetching UserZone with id " + id);
            UserZone object = service.findById(id);
            if ( object!=null ) {
                if ( security.hasAccessUser(object.getUserId())!=null ) {
                    result.put("success", Boolean.TRUE);
                    result.put("result", object);
                    return new ResponseEntity<>(result, HttpStatus.OK);
                } else {
                    result.put("success", Boolean.FALSE);
                    result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
                    return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
                }
            } else {
                result.put("success", Boolean.FALSE);
                result.put("message", AppPreferences.MESSAGE_HTTP_ID_FAILED);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
        }
    }
    
    //-------------------Create a UserZone--------------------------------------------------------
    @RequestMapping(value = "/userzone", method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> createUserZone(@RequestHeader(value="Access-Token") String accessToken, @RequestBody UserZone userzone,  UriComponentsBuilder ucBuilder) {
        
        result = new HashMap<String,Object>();
        System.out.println("Fetching Header Access Token " + accessToken);
        // Usamos la clase security para validar la permisología del usuario
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            // Se forza a guardar el registro relacionado con el Token
            userzone.setUserId(session.getId());
            //----------------------------- crea un nuevo registro -------------------------------
            String message = service.save(userzone, session.getCompany().getId());
            if ( message.isEmpty() ) {
                if ( service.findUserZone(session.getId(), userzone.getId()) ) {
                    result.put("success", Boolean.FALSE);
                    result.put("message", AppPreferences.MESSAGE_HTTP_SAVE_FAILED);
                    return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
                } else {
                    result.put("success", Boolean.TRUE);
                    result.put("message", AppPreferences.MESSAGE_HTTP_SAVE_OK);
                    return new ResponseEntity<>(result, HttpStatus.CREATED);
                }
            } else {
                result.put("success", Boolean.FALSE);
                result.put("message", message);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
        }
    }
     
    // ------------------- Update a UserZone --------------------------------------------------------
    @RequestMapping(value = "/userzone/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String,Object>> updateUserZone(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id, @RequestBody UserZone userzone) {
        
        System.out.println("Fetching Header Access Token " + accessToken);
        result = new HashMap<String,Object>();
        // Usamos la clase security para validar la permisología del usuario
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            System.out.println("Updating UserZone " + id);
            //------ se verifica que el Id existe y pertenece a la misma empresa ----
            if ( service.findId(id) ) {
                // Se forza a guardar el registro relacionado con el Token
                userzone.setUserId(session.getId());
                //------ se actualiza el registro en la base de datos ----
                String message = service.update(userzone, session.getCompany().getId());
                if ( message.isEmpty() ) {
                    UserZone object = service.findById(id);
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
 
    //------------------- Delete a UserZone --------------------------------------------------------
    
    @RequestMapping(value = "/userzone/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String,Object>> deleteUserZone(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id) {
        
        result = new HashMap<String,Object>();
        System.out.println("Fetching Header Access Token " + accessToken);
        // Usamos la clase security para validar la permisología del usuario
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            System.out.println("Fetching & Deleting UserZone with id " + id);
            //------ se obtiene el registro de la busqueda ----
            UserZone object = service.findById(id);
            if ( object!=null ) {
                if ( security.hasAccessUser(object.getUserId())!=null ) {
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
                
            } else {
                result.put("success", Boolean.FALSE);
                result.put("message", AppPreferences.MESSAGE_HTTP_ID_FAILED);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
        }
    }
    
}
