/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.controller;

import com.beecode.toolvendor.model.User;
import com.beecode.toolvendor.model.Zone;
import com.beecode.toolvendor.service.SecurityServiceImpl;
import com.beecode.toolvendor.service.ZoneServiceImpl;
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
public class ZoneController {
    // ------------------------------- OBJECTS ----------------------------------------
    Map<String,Object> result = new HashMap<String,Object>();
    
    // ------------------------------- SERVICES ----------------------------------------
    ZoneServiceImpl service = new ZoneServiceImpl();
    SecurityServiceImpl security = new SecurityServiceImpl();
    
    //-------------------Retrieve All Zone--------------------------------------------------------
     
    @RequestMapping(value = "/zone", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getAllZone(@RequestHeader(value="Access-Token") String accessToken) {
        result = new HashMap<String,Object>();
        System.out.println("Fetching Header Access Token " + accessToken);
        // Usamos la clase security para validar la permisología del usuario
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else  {
            System.out.println("Get all zone by company with id " + session.getCompany().getId());
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
    
    //-------------------Retrieve Single Zone--------------------------------------------------------
     
    @RequestMapping(value = "/zone/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getZone(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id) {
        
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
            System.out.println("Fetching Zone with id " + id);
            Zone object = service.findById(id, session.getCompany().getId());
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
    
    //-------------------Create a Zone--------------------------------------------------------
    @RequestMapping(value = "/zone", method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> createZone(@RequestHeader(value="Access-Token") String accessToken, @RequestBody Zone zone,  UriComponentsBuilder ucBuilder) {
        
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
            zone.setCompanyId(session.getCompany().getId());
            //----------------------------- crea un nuevo registro -------------------------------
            String message = service.save(zone);
            if ( message.isEmpty() ) {
                Zone object = service.findByName(zone.getDescription(), session.getCompany().getId());
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
     
    // ------------------- Update a Zone --------------------------------------------------------
    @RequestMapping(value = "/zone/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String,Object>> updateZone(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id, @RequestBody Zone zone) {
        
        System.out.println("Fetching Header Access Token " + accessToken);
        result = new HashMap<String,Object>();
        // Usamos la clase security para validar la permisología del usuario
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            System.out.println("Updating zone " + id);
            //------ se verifica que el Id existe y pertenece a la misma empresa ----
            if ( service.findId(id, session.getCompany().getId()) ) {
                // Se forza a guardar el registro relacionado con el Token
                zone.setCompanyId(session.getCompany().getId());
                //------ se actualiza el registro en la base de datos ----
                String message = service.update(zone);
                if ( message.isEmpty() ) {
                    Zone object = service.findById(id, session.getCompany().getId());
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
 
    //------------------- Delete a Zone --------------------------------------------------------
    
    @RequestMapping(value = "/zone/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String,Object>> deleteZone(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id) {
        
        result = new HashMap<String,Object>();
        System.out.println("Fetching Header Access Token " + accessToken);
        // Usamos la clase security para validar la permisología del usuario
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            System.out.println("Fetching & Deleting Zone with id " + id);
            //------ se obtiene el registro de la busqueda ----
            Zone object = service.findById(id, session.getCompany().getId());
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
