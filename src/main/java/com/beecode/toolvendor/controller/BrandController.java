/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.controller;

import com.beecode.toolvendor.model.Brand;
import com.beecode.toolvendor.model.User;
import com.beecode.toolvendor.service.BrandServiceImpl;
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
public class BrandController {
    // ------------------------------- OBJECTS ----------------------------------------
    Map<String,Object> result = new HashMap<String,Object>();
    
    // ------------------------------- SERVICES ----------------------------------------
    BrandServiceImpl service;
    SecurityServiceImpl security;
    
    //-------------------Retrieve All Brand--------------------------------------------------------
    @RequestMapping(value = "/brand", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getAllBrand(@RequestHeader(value="Access-Token") String accessToken) {
        result = new HashMap<String,Object>();
        service = new BrandServiceImpl();
        security = new SecurityServiceImpl();
        
        System.out.println("Fetching Header Access Token " + accessToken);
        // Usamos la clase security para validar la permisología del usuario
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else  {
            System.out.println("Get all brand by company with id " + session.getCompany().getId());
            // Obtenemos el objeto Company del usuario autorizado
            if ( session.getCompany().getId()!=0 ) {
                // Obtenemos el listado de brand de la compañia
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
    
    //-------------------Retrieve Single Brand--------------------------------------------------------
     
    @RequestMapping(value = "/brand/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getBrand(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id) {
        result = new HashMap<String,Object>();
        service = new BrandServiceImpl();
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
            System.out.println("Fetching Brand with id " + id);
            Brand object = service.findById(id, session.getCompany().getId());
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
    
    //-------------------Create a Brand--------------------------------------------------------
    @RequestMapping(value = "/brand", method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> createBrand(@RequestHeader(value="Access-Token") String accessToken, @RequestBody Brand brand,  UriComponentsBuilder ucBuilder) {
        result = new HashMap<String,Object>();
        service = new BrandServiceImpl();
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
            brand.setCompanyId(session.getCompany().getId());
            //----------------------------- crea un nuevo registro -------------------------------
            String message = service.save(brand);
            if ( message.isEmpty() ) {
                Brand object = service.findByName(brand.getBrand(), session.getCompany().getId());
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
     
    // ------------------- Update a Brand --------------------------------------------------------
    @RequestMapping(value = "/brand/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String,Object>> updateBrand(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id, @RequestBody Brand brand) {
        result = new HashMap<String,Object>();
        service = new BrandServiceImpl();
        security = new SecurityServiceImpl();
        
        System.out.println("Fetching Header Access Token " + accessToken);
        // Usamos la clase security para validar la permisología del usuario
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            System.out.println("Updating Brand " + id);
            //------ se verifica que el Id existe y pertenece a la misma empresa ----
            if ( service.findId(id, session.getCompany().getId()) ) {
                // Se forza a guardar el registro relacionado con el Token
                brand.setCompanyId(session.getCompany().getId());
                //------ se actualiza el registro en la base de datos ----
                String message = service.update(brand);
                if ( message.isEmpty() ) {
                    Brand object = service.findById(id, session.getCompany().getId());
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
 
    //------------------- Delete a Brand --------------------------------------------------------
    
    @RequestMapping(value = "/brand/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String,Object>> deleteBrand(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id) {
        result = new HashMap<String,Object>();
        service = new BrandServiceImpl();
        security = new SecurityServiceImpl();
        
        System.out.println("Fetching Header Access Token " + accessToken);
        // Usamos la clase security para validar la permisología del usuario
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            System.out.println("Fetching & Deleting Brand with id " + id);
            //------ se obtiene el registro de la busqueda ----
            Brand object = service.findById(id, session.getCompany().getId());
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
