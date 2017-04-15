/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.controller;

import com.beecode.toolvendor.model.ConfigEmail;
import com.beecode.toolvendor.model.User;
import com.beecode.toolvendor.service.ConfigEmailServiceImpl;
import com.beecode.toolvendor.service.SecurityServiceImpl;
import com.beecode.toolvendor.util.AppPreferences;
import java.util.HashMap;
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
public class ConfigEmailController {
    // ------------------------------- OBJECTS ----------------------------------------
    Map<String,Object> result = new HashMap<String,Object>();
    
    // ------------------------------- SERVICES ----------------------------------------
    ConfigEmailServiceImpl service;
    SecurityServiceImpl security;
    
    //-------------------Retrieve Single ConfigEmail by Company--------------------------------------------------------
     
    @RequestMapping(value = "/configemail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getConfigEmail(
            @RequestHeader(value="Access-Token") String accessToken) {
        
        result = new HashMap<String,Object>();
        service = new ConfigEmailServiceImpl();
        security = new SecurityServiceImpl();
        
        // Usamos la clase security para validar la permisología del usuario
        System.out.println("Fetching Header Access Token " + accessToken);
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            //Use headers to get the information about all the request headers
            System.out.println("Fetching ConfigEmail with id " + session.getCompanyId());
            ConfigEmail object = service.findByCompanyId(session.getCompanyId());
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
    }
    
    //-------------------Create a ConfigEmail--------------------------------------------------------
    @RequestMapping(value = "/configemail", method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> createConfigEmail(
            @RequestHeader(value="Access-Token") String accessToken, 
            @RequestBody ConfigEmail configemail,  
            UriComponentsBuilder ucBuilder) {
        
            result = new HashMap<String,Object>();
            service = new ConfigEmailServiceImpl();
            security = new SecurityServiceImpl();

            System.out.println("Fetching Header Access Token " + accessToken);
            // Usamos la clase security para validar la permisología del usuario
            User session = security.inicialized(accessToken);
            if ( session==null ) {
                result.put("success", Boolean.FALSE);
                result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
                return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
            } else {
                // Se forza a guardar el registro relacionado con el Token
                configemail.setCompanyId(session.getCompanyId());
                //----------------------------- crea un nuevo registro -------------------------------
                String message = service.save(configemail);
                if ( message.isEmpty() ) {
                    ConfigEmail object = service.findByCompanyId(session.getCompanyId());
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
    }
     
    // ------------------- Update a ConfigEmail --------------------------------------------------------
    @RequestMapping(value = "/configemail/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String,Object>> updateConfigEmail(
            @RequestHeader(value="Access-Token") String accessToken, 
            @PathVariable("id") int id, 
            @RequestBody ConfigEmail configemail) {
        
            result = new HashMap<String,Object>();
            service = new ConfigEmailServiceImpl();
            security = new SecurityServiceImpl();

            // Usamos la clase security para validar la permisología del usuario
            System.out.println("Fetching Header Access Token " + accessToken);
            User session = security.inicialized(accessToken);
            if ( session==null ) {
                result.put("success", Boolean.FALSE);
                result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
                return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
            } else {
                System.out.println("Updating ConfigEmail " + id);
                //------ se verifica que el Id existe y pertenece a la misma empresa ----
                if ( service.findCompanyId(session.getCompanyId()) ) {
                    // Se forza a guardar el registro relacionado con el Token
                    configemail.setCompanyId(session.getCompanyId());
                    //------ se actualiza el registro en la base de datos ----
                    String message = service.update(configemail);
                    if ( message.isEmpty() ) {
                        ConfigEmail object = service.findByCompanyId(session.getCompanyId());
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
 
    //------------------- Delete a ConfigEmail --------------------------------------------------------
    
    @RequestMapping(value = "/configemail/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String,Object>> deleteConfigEmail(
            @RequestHeader(value="Access-Token") String accessToken, 
            @PathVariable("id") int id) {
        
            result = new HashMap<String,Object>();
            service = new ConfigEmailServiceImpl();
            security = new SecurityServiceImpl();

            System.out.println("Fetching Header Access Token " + accessToken);
            // Usamos la clase security para validar la permisología del usuario
            User session = security.inicialized(accessToken);
            if ( session==null ) {
                result.put("success", Boolean.FALSE);
                result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
                return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
            } else {
                System.out.println("Fetching & Deleting ConfigEmail with id " + id);
                //------ se obtiene el registro de la busqueda ----
                ConfigEmail object = service.findByCompanyId(session.getCompanyId());
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
