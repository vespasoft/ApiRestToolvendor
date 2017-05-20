/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.controller;

import com.beecode.toolvendor.model.Visit;
import com.beecode.toolvendor.model.User;
import com.beecode.toolvendor.service.VisitServiceImpl;
import com.beecode.toolvendor.service.SecurityServiceImpl;
import com.beecode.toolvendor.service.VisitPictureServiceImpl;
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
public class VisitController extends AppPreferences {
    
    // ------------------------------- OBJECTS ----------------------------------------
    Map<String,Object> result = new HashMap<String,Object>();
    
    // ------------------------------- SERVICES ---------------------------------------
    VisitServiceImpl service;
    SecurityServiceImpl security;
    VisitPictureServiceImpl pictureserv;
    
    //-------------------Retrieve All Visit--------------------------------------------------------
    
    @RequestMapping(value = "/visit", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getAllVisit(@RequestHeader(value="Access-Token") String accessToken) {
        result = new HashMap<String,Object>();
        service = new VisitServiceImpl();
        security = new SecurityServiceImpl();
        
        System.out.println("Fetching Header Access Token " + accessToken);
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
             //---- Obtiene todos los customer registrados en el sistema -----
            System.out.println("List all visit by user " + session.getId());
            List list = service.getAllByUser(session.getId());
            if ( list==null ) {
                result.put("success", Boolean.FALSE);
                result.put("message", MESSAGE_HTTP_IS_EMPTY);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            } else {
                result.put("success", Boolean.TRUE);
                result.put("result", list);
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        }
        
    }
    
    //-------------------Retrieve Single Visit--------------------------------------------------------
     
    @RequestMapping(value = "/visit/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getVisit(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") Integer id) {
        result = new HashMap<String,Object>();
        service = new VisitServiceImpl();
        security = new SecurityServiceImpl();
        
        System.out.println("Fetching Header Access Token " + accessToken);
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            //Use headers to get the information about all the request headers
            System.out.println("Visit with id " + id);
            Visit object = service.findById(id, session.getCompany().getId());
            if ( object!=null ) {
                result.put("success", Boolean.TRUE);
                result.put("result", object);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                result.put("success", Boolean.FALSE);
                result.put("message", MESSAGE_HTTP_ID_FAILED_NOT_ACCESS);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
        }
    }
    
    //-------------------Create a Visit --------------------------------------------------------
    @RequestMapping(value = "/visit", method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> createVisit(@RequestHeader(value="Access-Token") String accessToken, @RequestBody Visit visit,  UriComponentsBuilder ucBuilder) {
        result = new HashMap<String,Object>();
        service = new VisitServiceImpl();
        security = new SecurityServiceImpl();
        
        System.out.println("Fetching Header Access Token " + accessToken);
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            // Se forza a guardar el registro relacionado con el Token
            visit.setCompanyId(session.getCompany().getId());
            //----------------------------- crea un nuevo registro -------------------------------
            String message = service.save(visit, session.getCompany());
            if ( message.isEmpty() ) {
                result.put("success", Boolean.TRUE);
                result.put("message", MESSAGE_HTTP_SAVE_OK);
                return new ResponseEntity<>(result, HttpStatus.CREATED);
            } else {
                result.put("success", Boolean.FALSE);
                result.put("message", message);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
        }
        
    }
     
    // ------------------- Update Visit --------------------------------------------------------
    @RequestMapping(value = "/visit/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String,Object>> checkinVisit(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") Integer id, @RequestBody Visit visit) {
        result = new HashMap<String,Object>();
        service = new VisitServiceImpl();
        security = new SecurityServiceImpl();
        
        System.out.println("Fetching Header Access Token " + accessToken);
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            System.out.println("Updating visit " + id);
            //------ se verifica que el Id existe y pertenece a la misma empresa ----
            if ( service.findId(id, session.getCompany().getId()) ) {
                //------ Se forza a guardar el registro relacionado con el Token
                visit.setCompanyId(session.getCompany().getId());
                //------ se actualiza el registro en la base de datos ----
                String message = service.update(visit, session.getCompany());
                if ( message.isEmpty() ) {
                    Visit object = service.findById(id, session.getCompany().getId());
                    if ( object==null ) {
                        result.put("success", Boolean.FALSE);
                        result.put("message", MESSAGE_HTTP_ID_FAILED);
                        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
                    } else {
                        result.put("success", Boolean.TRUE);
                        result.put("message", MESSAGE_HTTP_UPDATE_OK);
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
                result.put("message", MESSAGE_HTTP_ID_FAILED);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
        }
        
    }
    
    // ------------------- Send Service Page Visit --------------------------------------------------------
    @RequestMapping(value = "/visit/sendservicepage/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> sendservicepage(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") Integer id) {
        result = new HashMap<String,Object>();
        service = new VisitServiceImpl();
        security = new SecurityServiceImpl();
        
        System.out.println("Fetching Header Access Token " + accessToken);
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            System.out.println("Updating visit " + id);
            //------ se verifica que el Id existe y pertenece a la misma empresa ----
            if ( service.findId(id, session.getCompany().getId()) ) {
                String message = service.sendEmailServicePage(id, session.getCompany().getId());
                
                if ( message.isEmpty() ) {
                    result.put("success", Boolean.TRUE);
                    result.put("message", MESSAGE_HTTP_SEND_SERVICEPAGE_OK);
                    return new ResponseEntity<>(result, HttpStatus.OK);
                    
                } else {
                    result.put("success", Boolean.FALSE);
                    result.put("message", message);
                    return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
                }
            } else {
                result.put("success", Boolean.FALSE);
                result.put("message", MESSAGE_HTTP_ID_FAILED);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
        }
        
    }
 
    //------------------- Delete a Visit --------------------------------------------------------
    
    @RequestMapping(value = "/visit/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String,Object>> deleteVisit(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") Integer id) {
        result = new HashMap<String,Object>();
        service = new VisitServiceImpl();
        security = new SecurityServiceImpl();
        
        System.out.println("Fetching Header Access Token " + accessToken);
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            System.out.println("Fetching & Deleting Visit with id " + id);
            //------ se obtiene el registro de la busqueda ----
            Visit object = service.findById(id, session.getCompany().getId());
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
    
    //-------------------Retrieve All VisitPicture--------------------------------------------------------
    
    @RequestMapping(value = "/visit/{id}/pictures", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getAllVisitPicture(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") Integer id) {
        result = new HashMap<String,Object>();
        service = new VisitServiceImpl();
        security = new SecurityServiceImpl();
        pictureserv = new VisitPictureServiceImpl();
        
        System.out.println("Fetching Header Access Token " + accessToken);
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            System.out.println("List all visitpicture with visitId " + id);
            // se verifica que el id de la visita se valido
            Visit object = service.findById(id, session.getCompany().getId());
            if ( object!=null ) {
                //---- Obtiene todas las pictures de la visita -----
                List list = pictureserv.getAllByVisit(id);
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
