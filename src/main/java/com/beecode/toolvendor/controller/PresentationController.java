/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.controller;

import com.beecode.toolvendor.model.Presentation;
import com.beecode.toolvendor.model.User;
import com.beecode.toolvendor.service.PresentationServiceImpl;
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
public class PresentationController {
    // ------------------------------- OBJECTS ----------------------------------------
    Map<String,Object> result = new HashMap<String,Object>();
    
    // ------------------------------- SERVICES ----------------------------------------
    PresentationServiceImpl service;
    SecurityServiceImpl security;
    
    //-------------------Retrieve All Presentation--------------------------------------------------------
    @RequestMapping(value = "/presentation", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getAllPresentation(@RequestHeader(value="Access-Token") String accessToken) {
        result = new HashMap<String,Object>();
        service = new PresentationServiceImpl();
        security = new SecurityServiceImpl();
        
        System.out.println("Fetching Header Access Token " + accessToken);
        // Usamos la clase security para validar la permisología del usuario
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else  {
            System.out.println("Get all groups by company with id " + session.getCompany().getId());
            // Obtenemos el objeto Company del usuario autorizado
            if ( session.getCompany().getId()!=0 ) {
                // Obtenemos el listado de groups de la compañia
                List list = service.getAllByCompany(session.getCompany().getId());
                result.put("success", Boolean.TRUE);
                result.put("result", list);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                result.put("success", Boolean.FALSE);
                result.put("message", AppPreferences.MESSAGE_HTTP_ID_FAILED);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
        }
    }
    
    //-------------------Retrieve Single Presentation--------------------------------------------------------
     
    @RequestMapping(value = "/presentation/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getPresentation(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id) {
        result = new HashMap<String,Object>();
        service = new PresentationServiceImpl();
        security = new SecurityServiceImpl();
        
        System.out.println("Fetching Header Access Token " + accessToken);
        // Usamos la clase security para validar la permisología del usuario
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            //Use headers to get the information about all the request headers
            System.out.println("Fetching Presentation with id " + id);
            Presentation object = service.findById(id, session.getCompany().getId());
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
    
    //-------------------Create a Presentation--------------------------------------------------------
    @RequestMapping(value = "/presentation", method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> createPresentation(@RequestHeader(value="Access-Token") String accessToken, @RequestBody Presentation presentation,  UriComponentsBuilder ucBuilder) {
        result = new HashMap<String,Object>();
        service = new PresentationServiceImpl();
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
            presentation.setCompanyId(session.getCompany().getId());
            //----------------------------- crea un nuevo registro -------------------------------
            String message = service.save(presentation);
            if ( message.isEmpty() ) {
                Presentation object = service.findByName(presentation.getPresentation(), session.getCompany().getId());
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
     
    // ------------------- Update a Presentation --------------------------------------------------------
    @RequestMapping(value = "/presentation/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String,Object>> updatePresentation(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id, @RequestBody Presentation presentation) {
        result = new HashMap<String,Object>();
        service = new PresentationServiceImpl();
        security = new SecurityServiceImpl();
        
        System.out.println("Fetching Header Access Token " + accessToken);
        // Usamos la clase security para validar la permisología del usuario
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            System.out.println("Updating Presentation " + id);
            //------ se verifica que el Id existe y pertenece a la misma empresa ----
            if ( service.findId(id, session.getCompany().getId()) ) {
                // Se forza a guardar el registro relacionado con el Token
                presentation.setCompanyId(session.getCompany().getId());
                //------ se actualiza el registro en la base de datos ----
                String message = service.update(presentation);
                if ( message.isEmpty() ) {
                    Presentation object = service.findById(id, session.getCompany().getId());
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
 
    //------------------- Delete a Presentation --------------------------------------------------------
    
    @RequestMapping(value = "/presentation/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String,Object>> deletePresentation(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id) {
        result = new HashMap<String,Object>();
        service = new PresentationServiceImpl();
        security = new SecurityServiceImpl();
        
        System.out.println("Fetching Header Access Token " + accessToken);
        // Usamos la clase security para validar la permisología del usuario
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            System.out.println("Fetching & Deleting Presentation with id " + id);
            //------ se obtiene el registro de la busqueda ----
            Presentation object = service.findById(id, session.getCompany().getId());
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
