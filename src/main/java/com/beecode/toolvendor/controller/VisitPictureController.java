/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.controller;

import com.beecode.toolvendor.model.VisitPicture;
import com.beecode.toolvendor.model.User;
import com.beecode.toolvendor.service.VisitPictureServiceImpl;
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
public class VisitPictureController {
    
    // ------------------------------- OBJECTS ----------------------------------------
    Map<String,Object> result = new HashMap<String,Object>();
    
    // ------------------------------- SERVICES ---------------------------------------
    VisitPictureServiceImpl service;
    SecurityServiceImpl security;
    
    //-------------------Retrieve Single VisitPicture--------------------------------------------------------
     
    @RequestMapping(value = "/visitpicture/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getVisitPicture(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") Integer id) {
        result = new HashMap<String,Object>();
        service = new VisitPictureServiceImpl();
        security = new SecurityServiceImpl();
        
        System.out.println("Fetching Header Access Token " + accessToken);
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            //Use headers to get the information about all the request headers
            System.out.println("VisitPicture with id " + id);
            VisitPicture object = service.findById(id);
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
    
    //-------------------Create a VisitPicture --------------------------------------------------------
    @RequestMapping(value = "/visitpicture", method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> createVisitPicture(@RequestHeader(value="Access-Token") String accessToken, @RequestBody VisitPicture visitpicture,  UriComponentsBuilder ucBuilder) {
        result = new HashMap<String,Object>();
        service = new VisitPictureServiceImpl();
        security = new SecurityServiceImpl();
        
        System.out.println("Fetching Header Access Token " + accessToken);
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            //----------------------------- crea un nuevo registro -------------------------------
            String message = service.save(visitpicture, session.getCompany().getId());
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
     
    // ------------------- Update a VisitPicture --------------------------------------------------------
    @RequestMapping(value = "/visitpicture/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String,Object>> updateVisitPicture(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") Integer id, @RequestBody VisitPicture visitpicture) {
        result = new HashMap<String,Object>();
        service = new VisitPictureServiceImpl();
        security = new SecurityServiceImpl();
        
        System.out.println("Fetching Header Access Token " + accessToken);
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            System.out.println("Updating visitpicture " + id);
            //------ se verifica que el Id existe y pertenece a la misma empresa ----
            if ( service.findId(id) ) {
                //------ se actualiza el registro en la base de datos ----
                String message = service.update(visitpicture, session.getCompany().getId());
                if ( message.isEmpty() ) {
                    VisitPicture object = service.findById(id);
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
 
    //------------------- Delete a VisitPicture --------------------------------------------------------
    
    @RequestMapping(value = "/visitpicture/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String,Object>> deleteVisitPicture(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") Integer id) {
        result = new HashMap<String,Object>();
        service = new VisitPictureServiceImpl();
        security = new SecurityServiceImpl();
        
        System.out.println("Fetching Header Access Token " + accessToken);
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            System.out.println("Fetching & Deleting VisitPicture with id " + id);
            //------ se obtiene el registro de la busqueda ----
            VisitPicture object = service.findById(id);
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
