/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.controller;

import com.beecode.toolvendor.model.Cellar;
import com.beecode.toolvendor.model.User;
import com.beecode.toolvendor.service.CellarServiceImpl;
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
public class CellarController {
    // ------------------------------- OBJECTS ----------------------------------------
    Map<String,Object> result = new HashMap<String,Object>();
    
    // ------------------------------- SERVICES ----------------------------------------
    CellarServiceImpl service;
    SecurityServiceImpl security;
    
    //-------------------Retrieve All Cellar--------------------------------------------------------
    @RequestMapping(value = "/cellar", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getAllCellar(@RequestHeader(value="Access-Token") String accessToken) {
        result = new HashMap<String,Object>();
        service = new CellarServiceImpl();
        security = new SecurityServiceImpl();
        
        System.out.println("Fetching Header Access Token " + accessToken);
        // Usamos la clase security para validar la permisología del usuario
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else  {
            System.out.println("Get all groups by company with id " + session.getCompanyId());
            // Obtenemos el objeto Company del usuario autorizado
            if ( session.getCompanyId()!=0 ) {
                // Obtenemos el listado de groups de la compañia
                List list = service.getAllByCompany(session.getCompanyId());
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
    
    //-------------------Retrieve Single Cellar--------------------------------------------------------
     
    @RequestMapping(value = "/cellar/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getCellar(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id) {
        result = new HashMap<String,Object>();
        service = new CellarServiceImpl();
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
            System.out.println("Fetching Cellar with id " + id);
            Cellar object = service.findById(id, session.getCompanyId());
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
    
    //-------------------Create a Cellar--------------------------------------------------------
    @RequestMapping(value = "/cellar", method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> createCellar(@RequestHeader(value="Access-Token") String accessToken, @RequestBody Cellar cellar,  UriComponentsBuilder ucBuilder) {
        result = new HashMap<String,Object>();
        service = new CellarServiceImpl();
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
            cellar.setCompanyId(session.getCompanyId());
            //----------------------------- crea un nuevo registro -------------------------------
            String message = service.save(cellar);
            if ( message.isEmpty() ) {
                Cellar object = service.findByName(cellar.getCellar(), session.getCompanyId());
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
     
    // ------------------- Update a Cellar --------------------------------------------------------
    @RequestMapping(value = "/cellar/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String,Object>> updateCellar(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id, @RequestBody Cellar cellar) {
        result = new HashMap<String,Object>();
        service = new CellarServiceImpl();
        security = new SecurityServiceImpl();
        
        // Usamos la clase security para validar la permisología del usuario
        System.out.println("Fetching Header Access Token " + accessToken);
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            System.out.println("Updating Cellar " + id);
            //------ se verifica que el Id existe y pertenece a la misma empresa ----
            if ( service.findId(id, session.getCompanyId()) ) {
                // Se forza a guardar el registro relacionado con el Token
                cellar.setCompanyId(session.getCompanyId());
                //------ se actualiza el registro en la base de datos ----
                String message = service.update(cellar);
                if ( message.isEmpty() ) {
                    Cellar object = service.findById(id, session.getCompanyId());
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
 
    //------------------- Delete a Cellar --------------------------------------------------------
    
    @RequestMapping(value = "/cellar/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String,Object>> deleteCellar(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id) {
        result = new HashMap<String,Object>();
        service = new CellarServiceImpl();
        security = new SecurityServiceImpl();
        
        System.out.println("Fetching Header Access Token " + accessToken);
        // Usamos la clase security para validar la permisología del usuario
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            System.out.println("Fetching & Deleting Cellar with id " + id);
            //------ se obtiene el registro de la busqueda ----
            Cellar object = service.findById(id, session.getCompanyId());
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
