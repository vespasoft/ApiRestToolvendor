/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.controller;

import com.beecode.toolvendor.model.Customer;
import com.beecode.toolvendor.model.User;
import com.beecode.toolvendor.service.CallServiceImpl;
import com.beecode.toolvendor.service.CompanyServiceImpl;
import com.beecode.toolvendor.service.CustomerServiceImpl;
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
public class CustomerController extends AppPreferences {
    // ------------------------------- OBJECTS ----------------------------------------
    Map<String,Object> result = new HashMap<String,Object>();
    
    // ------------------------------- SERVICES ---------------------------------------
    CustomerServiceImpl service = new CustomerServiceImpl();
    SecurityServiceImpl security = new SecurityServiceImpl();
    CompanyServiceImpl companyserv = new CompanyServiceImpl();
    CallServiceImpl callserv = new CallServiceImpl();
    
    //-------------------Retrieve All Customer-----------------------------------------
    @RequestMapping(value = "/customer", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getAllCustomer(@RequestHeader(value="Access-Token") String accessToken) {
        result = new HashMap<String,Object>();
        service = new CustomerServiceImpl();
        System.out.println("Fetching Header Access Token " + accessToken);
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            //---- Obtiene todos los customer registrados de un usuario -----
            System.out.println("List all customer by user " + session.getId());
            List list = service.getAllByUser(session.getId());
            if ( list==null ) {
                result.put("success", Boolean.FALSE);
                result.put("message", MESSAGE_HTTP_IS_EMPTY);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                result.put("success", Boolean.TRUE);
                result.put("result", list);
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            
        }
    }
    
    //------------------- Retrieve Single Customer -------------------------------------------------
     
    @RequestMapping(value = "/customer/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getCustomer(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id) {
        result = new HashMap<String,Object>();
        service = new CustomerServiceImpl();
        System.out.println("Fetching Header Access Token " + accessToken);
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            System.out.println("Customer with id " + id);
            Customer object = security.hasAccessCustomer(id);
            if ( object!=null ) {
                result.put("success", Boolean.TRUE);
                result.put("result", object);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                result.put("success", Boolean.FALSE);
                result.put("message", MESSAGE_USER_NOT_ACCESS);
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        }
    }
    
    //-------------------Create a Customer -----------------------------------------------------
    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> createCustomer(@RequestHeader(value="Access-Token") String accessToken, @RequestBody Customer cstmr,  UriComponentsBuilder ucBuilder) {
        result = new HashMap<String,Object>();
        service = new CustomerServiceImpl();
        System.out.println("Fetching Header Access Token " + accessToken);
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            // Se forza a guardar el registro relacionado con el Token
            cstmr.setCompanyId(session.getCompanyId());
            //----------------------------- crea un nuevo registro -------------------------------
            String message = service.save(cstmr);
            if ( message.isEmpty() ) {
                service = new CustomerServiceImpl();
                Customer object = service.findByEmail(cstmr.getContactEmail());
                if ( object==null ) {
                    result.put("success", Boolean.FALSE);
                    result.put("message", MESSAGE_HTTP_SAVE_FAILED);
                    return new ResponseEntity<>(result, HttpStatus.OK);
                } else {
                    result.put("success", Boolean.TRUE);
                    result.put("message", MESSAGE_HTTP_SAVE_OK);
                    result.put("result", object);
                    return new ResponseEntity<>(result, HttpStatus.CREATED);
                }
            } else {
                result.put("success", Boolean.FALSE);
                result.put("message", message);
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        }
        
    }
     
    // ------------------- Update a Customer --------------------------------------------------------
    @RequestMapping(value = "/customer/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String,Object>> updateCustomer(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id, @RequestBody Customer cstmr) {
        result = new HashMap<String,Object>();
        service = new CustomerServiceImpl();
        System.out.println("Fetching Header Access Token " + accessToken);
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            System.out.println("Updating customer " + id);
            //------ se verifica que el Id existe y pertenece a la misma empresa ----
            if ( service.findId(id, session.getCompanyId()) ) {
                // Se forza a guardar el registro relacionado con el Token
                cstmr.setCompanyId(session.getCompanyId());
                //------ se actualiza el registro en la base de datos ----
                String message = service.update(cstmr);
                if ( message.isEmpty() ) {
                    Customer object = service.findById(id, session.getCompanyId());
                    if ( object==null ) {
                        result.put("success", Boolean.FALSE);
                        result.put("message", MESSAGE_HTTP_ID_FAILED);
                        return new ResponseEntity<>(result, HttpStatus.OK);
                    } else {
                        result.put("success", Boolean.TRUE);
                        result.put("message", AppPreferences.MESSAGE_HTTP_UPDATE_OK);
                        result.put("result", object);
                        return new ResponseEntity<>(result, HttpStatus.OK);
                    }
                } else {
                    result.put("success", Boolean.FALSE);
                    result.put("message", message);
                    return new ResponseEntity<>(result, HttpStatus.OK);
                }
            } else {
                result.put("success", Boolean.FALSE);
                result.put("message", MESSAGE_HTTP_ID_FAILED);
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        }
        
    }
 
    //------------------- Delete a Customer --------------------------------------------------------
    
    @RequestMapping(value = "/customer/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String,Object>> deleteCustomer(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id) {
        result = new HashMap<String,Object>();
        service = new CustomerServiceImpl();
        System.out.println("Fetching Header Access Token " + accessToken);
        User session = security.inicialized(accessToken);
        if ( session==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            System.out.println("Fetching & Deleting Customer with id " + id);
            //------ se obtiene el registro de la busqueda ----
            Customer object = security.hasAccessCustomer(id);
            if ( object!=null ) {
                //------ se elimina el registro en la base de datos ----
                boolean success = service.delete(id);
                if ( success ) {
                    result.put("success", Boolean.TRUE);
                    result.put("message", MESSAGE_HTTP_DELETE_OK);
                    return new ResponseEntity<>(result, HttpStatus.OK);
                } else {
                    result.put("success", Boolean.FALSE);
                    result.put("message", MESSAGE_HTTP_DELETE_FAILED);
                    return new ResponseEntity<>(result, HttpStatus.OK);
                }
            } else {
                result.put("success", Boolean.FALSE);
                result.put("message", MESSAGE_USER_NOT_ACCESS);
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        }
        
    }
    
}
