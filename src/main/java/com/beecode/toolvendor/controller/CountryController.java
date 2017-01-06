/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.controller;

import com.beecode.toolvendor.model.Country;
import com.beecode.toolvendor.model.User;
import com.beecode.toolvendor.service.CityServiceImpl;
import com.beecode.toolvendor.service.CountryServiceImpl;
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
public class CountryController {
    
    // ------------------------------- SERVICES ----------------------------------------
    SecurityServiceImpl security = new SecurityServiceImpl();
    CountryServiceImpl service = new CountryServiceImpl();
    CityServiceImpl cityserv = new CityServiceImpl();
    
    //-------------------Retrieve All Country-------------------------------------------
    @RequestMapping(value = "/country", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getAllCountry() {
        Map<String,Object> result = new HashMap<String,Object>();
        
        System.out.println("Get all country... ");
        //---- obtiene listado de registros ----
        List list = service.getAll();
        // Verificamos que la lista no esta vacia
        if ( !list.isEmpty() ) {
            result.put("success", Boolean.TRUE);
            result.put("result", list);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_HTTP_IS_EMPTY);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }
    
    //-------------------Retrieve Single Country--------------------------------------------------------
     
    @RequestMapping(value = "/country/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getCountry(@PathVariable("id") int id) {
        Map<String,Object> result = new HashMap<String,Object>();
        
        System.out.println("Fetching Country with id " + id);
        Country obj = service.findById(id);
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
    
    //-------------------Retrieve All Cities by Country--------------------------------------------------------
    
    @RequestMapping(value = "/country/{id}/city", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getAllCityByCountry(@PathVariable("id") int id) {
        Map<String,Object> result = new HashMap<String,Object>();
        
        System.out.println("Fetching All Cities by Country with id " + id);
        Country obj = service.findById(id);
        if ( obj!=null ) {
            List list = cityserv.getAllByCountry(obj);
            // Verificamos que la lista no esta vacia
            if ( list!=null ) {
                result.put("success", Boolean.TRUE);
                result.put("result", list);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                result.put("success", Boolean.FALSE);
                result.put("message", AppPreferences.MESSAGE_HTTP_IS_EMPTY);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
        } else {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_HTTP_ID_FAILED);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }
    
    //-------------------Create a Country--------------------------------------------------------
    @RequestMapping(value = "/country", method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> createCountry(@RequestHeader(value="Access-Token") String accessToken, @RequestBody Country country,  UriComponentsBuilder ucBuilder) {
        Map<String,Object> result = new HashMap<String,Object>();
        
        //----------------------------- crea un nuevo registro -------------------------------
        System.out.println("Fetching Header Access Token " + accessToken);
        User session = security.parseJWT(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            //------------------------- crea un nuevo registro -------------------------------
            String message = service.save(country);
            if ( message.isEmpty() ) {
                Country object = service.findByName(country.getName());
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
     
    // ------------------- Update a Country --------------------------------------------------------
    @RequestMapping(value = "/country/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String,Object>> updateCountry(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id, @RequestBody Country country) {
        
        Map<String,Object> result = new HashMap<String,Object>();
        System.out.println("Fetching Header Access Token " + accessToken);
        User session = security.parseJWT(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            System.out.println("Updating Country " + id);
            //------ se actualiza el registro en la base de datos ----
            String message = service.update(country);
            if ( message.isEmpty() ) {
                Country object = service.findByName(country.getName());
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
 
    //------------------- Delete a Country --------------------------------------------------------
    
    @RequestMapping(value = "/country/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String,Object>> deleteCountry(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id) {
        
        Map<String,Object> result = new HashMap<String,Object>();
        System.out.println("Fetching Header Access Token " + accessToken);
        User session = security.parseJWT(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            System.out.println("Fetching & Deleting Country with id " + id);
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
