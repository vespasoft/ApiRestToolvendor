/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.controller;

import com.beecode.toolvendor.model.Stock;
import com.beecode.toolvendor.model.User;
import com.beecode.toolvendor.service.SecurityServiceImpl;
import com.beecode.toolvendor.service.StockServiceImpl;
import com.beecode.toolvendor.util.AppPreferences;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
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
public class StockController {
    // ------------------------------- OBJECTS ----------------------------------------
    Map<String,Object> result = new HashMap<String,Object>();
    
    // ------------------------------- SERVICES ----------------------------------------
    StockServiceImpl service;
    SecurityServiceImpl security;
    
    //-------------------Retrieve All Stock By Product --------------------------------------------------------
    @RequestMapping(value = "/product/{productid}/stock", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getAllStock(
            @RequestHeader(value="Access-Token") String accessToken, 
            @PathVariable("productid") int productid) {
        
            result = new HashMap<String,Object>();
            service = new StockServiceImpl();
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
                    List list = service.getAllByProduct(productid);
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
    
    //-------------------Create a Stock--------------------------------------------------------
    @RequestMapping(value = "/stock", method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> createStock(
            @RequestHeader(value="Access-Token") String accessToken, 
            @RequestBody Stock stock,  
            UriComponentsBuilder ucBuilder) {
        
            result = new HashMap<String,Object>();
            service = new StockServiceImpl();
            security = new SecurityServiceImpl();

            System.out.println("Fetching Header Access Token " + accessToken);
            // Usamos la clase security para validar la permisología del usuario
            User session = security.inicialized(accessToken);
            if ( session==null ) {
                result.put("success", Boolean.FALSE);
                result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
                return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
            } else {
                //----------------------------- crea un nuevo registro -------------------------------
                String message = service.save(stock, session.getCompanyId());
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

    // ------------------- Update a Stock --------------------------------------------------------
    @RequestMapping(value = "/stock/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String,Object>> updateStock(
            @RequestHeader(value="Access-Token") String accessToken, 
            @PathVariable("id") int id, 
            @RequestBody Stock stock) {
        
            result = new HashMap<String,Object>();
            service = new StockServiceImpl();
            security = new SecurityServiceImpl();

            // Usamos la clase security para validar la permisología del usuario
            System.out.println("Fetching Header Access Token " + accessToken);
            User session = security.inicialized(accessToken);
            if ( session==null ) {
                result.put("success", Boolean.FALSE);
                result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
                return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
            } else {
                System.out.println("Updating Stock " + id);
                //------ se verifica que el Id existe y pertenece a la misma empresa ----
                if ( service.findId(id) ) {
                    //------ se actualiza el registro en la base de datos ----
                    String message = service.update(stock, session.getCompanyId());
                    if ( message.isEmpty() ) {
                        Stock object = service.findById(id);
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
 
    //------------------- Delete a Stock --------------------------------------------------------
    
    @RequestMapping(value = "/stock/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String,Object>> deleteStock(
            @RequestHeader(value="Access-Token") String accessToken, 
            @PathVariable("id") int id) {
        
            result = new HashMap<String,Object>();
            service = new StockServiceImpl();
            security = new SecurityServiceImpl();

            System.out.println("Fetching Header Access Token " + accessToken);
            // Usamos la clase security para validar la permisología del usuario
            User session = security.inicialized(accessToken);
            if ( session==null ) {
                result.put("success", Boolean.FALSE);
                result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
                return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
            } else {
                System.out.println("Fetching & Deleting Stock with id " + id);
                //------ se obtiene el registro de la busqueda ----
                Stock object = service.findById(id);
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
