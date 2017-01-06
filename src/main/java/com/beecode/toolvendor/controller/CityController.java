/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.controller;

import com.beecode.toolvendor.model.City;
import com.beecode.toolvendor.model.User;
import com.beecode.toolvendor.service.CityServiceImpl;
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
public class CityController {
    
    // ------------------------------- SERVICES ----------------------------------------
    SecurityServiceImpl security = new SecurityServiceImpl();
    CityServiceImpl service = new CityServiceImpl();
    
    
    //-------------------Retrieve Single City--------------------------------------------------------
     
    @RequestMapping(value = "/city/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getCity(@PathVariable("id") int id) {
        Map<String,Object> result = new HashMap<String,Object>();
        
        System.out.println("Fetching City with id " + id);
        
        City obj = service.findById(id);
        if ( obj!=null ) {
            // Obtenemos el listado de customer de una compañia
            result.put("success", Boolean.TRUE);
            result.put("result", obj);
            return new ResponseEntity<>(result, HttpStatus.OK);          // 200
        } else {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_HTTP_ID_FAILED);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);   // 400
        }
    }
    
    //-------------------Create a City--------------------------------------------------------
    @RequestMapping(value = "/city", method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> createCity(@RequestHeader(value="Access-Token") String accessToken, @RequestBody City city,  UriComponentsBuilder ucBuilder) {
        Map<String,Object> result = new HashMap<String,Object>();
        
        System.out.println("Fetching Header Access Token " + accessToken);
        User session = security.parseJWT(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            //----------------------------- crea un nuevo registro -------------------------------
            String message = service.save(city);
            if ( message.isEmpty() ) {
                City object = service.findByName(city.getCity());
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
     
    // ------------------- Update a City --------------------------------------------------------
    @RequestMapping(value = "/city/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String,Object>> updateCity(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id, @RequestBody City city) {
        Map<String,Object> result = new HashMap<String,Object>();
        System.out.println("Updating City " + id);
        
        System.out.println("Fetching Header Access Token " + accessToken);
        User session = security.parseJWT(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            System.out.println("Updating user " + id);
            //------ se actualiza el registro en la base de datos ----
            String message = service.update(city);
            if ( message.isEmpty() ) {
                City object = service.findByName(city.getCity());
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
 
    //------------------- Delete a City --------------------------------------------------------
    
    @RequestMapping(value = "/city/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String,Object>> deleteCity(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id) {
        Map<String,Object> result = new HashMap<String,Object>();
        
        System.out.println("Fetching Header Access Token " + accessToken);
        User session = security.parseJWT(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            System.out.println("Fetching & Deleting City with id " + id);
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
        }
        
    }
    
}
