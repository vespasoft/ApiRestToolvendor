/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.controller;

import com.beecode.toolvendor.model.User;
import com.beecode.toolvendor.model.UserType;
import com.beecode.toolvendor.service.SecurityServiceImpl;
import com.beecode.toolvendor.service.UserServiceImpl;
import com.beecode.toolvendor.service.UserTypeServiceImpl;
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
public class UserTypeController {
    // ------------------------------- OBJECTS ----------------------------------------
    Map<String,Object> result = new HashMap<String,Object>();
    
    // ------------------------------- SERVICES ----------------------------------------
    SecurityServiceImpl security = new SecurityServiceImpl();
    UserTypeServiceImpl service = new UserTypeServiceImpl();
    
    //-------------------Retrieve All Users--------------------------------------------------------
     
    @RequestMapping(value = "/usertype", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getAllUserType(@RequestHeader(value="Access-Token") String accessToken) {
        
        System.out.println("Fetching Header Access Token " + accessToken);
        result = new HashMap<String,Object>();
        security = new SecurityServiceImpl();
        service = new UserTypeServiceImpl();
        // Usamos la clase security para validar la permisología del usuario
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else  {
            System.out.println("Get all usertype by company with id " + session.getCompanyId());
            // Obtenemos el objeto Company del usuario autorizado
            if ( session.getCompanyId()!=0 ) {
                // Obtenemos el listado de customer de una compañia
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
    
    //-------------------Retrieve Single User--------------------------------------------------------
     
    @RequestMapping(value = "/usertype/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getType(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id) {
        
        System.out.println("Fetching Header Access Token " + accessToken);
        result = new HashMap<String,Object>();
        security = new SecurityServiceImpl();
        service = new UserTypeServiceImpl();
        
        // Usamos la clase security para validar la permisología del usuario
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            //Use headers to get the information about all the request headers
            System.out.println("Fetching UserType with id " + id);
            
            //------ se obtiene el registro de la busqueda ----
            UserType object = service.findById(id, session.getCompanyId());
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
    
    //-------------------Create a UserType--------------------------------------------------------
    @RequestMapping(value = "/usertype", method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> createType(@RequestHeader(value="Access-Token") String accessToken, @RequestBody UserType usertype,  UriComponentsBuilder ucBuilder) {
        result = new HashMap<String,Object>();
        security = new SecurityServiceImpl();
        service = new UserTypeServiceImpl();
        
        System.out.println("Fetching Header Access Token " + accessToken);
        // Usamos la clase security para validar la permisología del usuario
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            // Se forza a guardar el registro relacionado con el Token
            usertype.setCompanyId(session.getCompanyId());
            //----------------------------- crea un nuevo registro -------------------------------
            String message = service.save(usertype);
            if ( message.isEmpty() ) {
                UserType object = service.findByName(usertype.getDescription(), session.getCompanyId());
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
     
    // ------------------- Update a UserType --------------------------------------------------------
    @RequestMapping(value = "/usertype/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String,Object>> updateType(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id, @RequestBody UserType usertype) {
        
        System.out.println("Fetching Header Access Token " + accessToken);
        result = new HashMap<String,Object>();
        security = new SecurityServiceImpl();
        service = new UserTypeServiceImpl();
        
        // Usamos la clase security para validar la permisología del usuario
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            System.out.println("Updating Usertype " + id);
            //------ se verifica que el Id existe y pertenece a la misma empresa ----
            if ( service.findId(id, session.getCompanyId()) ) {
                // Se forza a guardar el registro relacionado con el Token
                usertype.setCompanyId(session.getCompanyId());
                //------ se actualiza el registro en la base de datos ----
                String message = service.update(usertype);
                if ( message.isEmpty() ) {
                    UserType object = service.findById(id, session.getCompanyId());
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
    
    @RequestMapping(value = "/usertype/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String,Object>> deleteUser(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id) {
        
        result = new HashMap<String,Object>();
        security = new SecurityServiceImpl();
        service = new UserTypeServiceImpl();
        
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
            UserType object = service.findById(id, session.getCompanyId());
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
