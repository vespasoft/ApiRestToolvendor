/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.controller;

import com.beecode.toolvendor.model.Company;
import com.beecode.toolvendor.model.User;
import com.beecode.toolvendor.service.SecurityServiceImpl;
import com.beecode.toolvendor.service.CompanyServiceImpl;
import com.beecode.toolvendor.service.CustomerServiceImpl;
import com.beecode.toolvendor.service.ProductServiceImpl;
import com.beecode.toolvendor.service.UserServiceImpl;
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
public class CompanyController {
    // ------------------------------- OBJECTS ----------------------------------------
    Map<String,Object> result = new HashMap<String,Object>();
    
    // ------------------------------- SERVICES ----------------------------------------
    CompanyServiceImpl service = new CompanyServiceImpl();
    SecurityServiceImpl security = new SecurityServiceImpl();
    UserServiceImpl userserv;
    CustomerServiceImpl cstmrserv;
    ProductServiceImpl prodserv;
    
    //-----------------------Retrieve All Company---------------------------------------
    @RequestMapping(value = "/company", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> listAllCompany(@RequestHeader(value="Access-Token") String accessToken) {
        result = new HashMap<String,Object>();
        service = new CompanyServiceImpl();
        security = new SecurityServiceImpl();
        System.out.println("Fetching Header Access Token " + accessToken);
        User user = security.parseJWT(accessToken);
        if ( user==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            //Use headers to get the information about all the request headers
            System.out.println("List Company by UserId " + user.getId());
            List<Company> list = service.getAll();
            if ( list==null ) {
                result.put("success", Boolean.FALSE);
                result.put("message", "No hay registros para mostrar");
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            } else {
                result.put("success", Boolean.TRUE);
                result.put("result", list);
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        }
    }
    
    //-------------------Retrieve Single Company------------------------------------
    @RequestMapping(value = "/company/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getCompany(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id) {
        result = new HashMap<String,Object>();
        service = new CompanyServiceImpl();
        security = new SecurityServiceImpl();
        System.out.println("Fetching Header Access Token " + accessToken);
        User user = security.inicialized(accessToken);
        if ( user==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            //Use headers to get the information about all the request headers
            System.out.println("Company with id " + user.getId());
            Company object = security.hasAccessCompany(id);
            if ( object!=null ) {
                result.put("success", Boolean.TRUE);
                result.put("result", object);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                result.put("success", Boolean.FALSE);
                result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
        }
    }
    
    //-------------------Create a Company--------------------------------------------------------
    @RequestMapping(value = "/company", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createCompany(@RequestBody Company company,  UriComponentsBuilder ucBuilder) {
        result = new HashMap<String,Object>();
        service = new CompanyServiceImpl();
        security = new SecurityServiceImpl();
        //----------------------------- crea un nuevo registro -------------------------------
        String message = service.save(company);
        if ( message.isEmpty() ) {
            Company object = service.findByEmail(company.getEmail());
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
        //HttpHeaders headers = new HttpHeaders();
        //headers.setLocation(ucBuilder.path("/company/{id}").buildAndExpand(currentCompany.getCompanyId()).toUri());
        
    }
     
    // ------------------- Update a Company --------------------------------------------------------
    @RequestMapping(value = "/company/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String,Object>> updateCompany(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id, @RequestBody Company company) {
        result = new HashMap<String,Object>();
        service = new CompanyServiceImpl();
        security = new SecurityServiceImpl();
        System.out.println("Fetching Header Access Token " + accessToken);
        User user = security.inicialized(accessToken);
        if ( user==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            // se verifica que el usuario autenticado tenga permisos..
            //------ se verifica que el Id existe y pertenece a la misma empresa ----
            Company object = security.hasAccessCompany(id);
            if ( object!=null ) {
                System.out.println("Updating Company " + id);
                //------ se actualiza el registro en la base de datos ----
                String message = service.update(company);
                if ( message.isEmpty() ) {
                    result.put("success", Boolean.TRUE);
                    result.put("message", AppPreferences.MESSAGE_HTTP_UPDATE_OK);
                    result.put("result", object);
                    return new ResponseEntity<>(result, HttpStatus.OK);

                } else {
                    result.put("success", Boolean.FALSE);
                    result.put("message", message);
                    return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
                }
            } else {
                result.put("success", Boolean.FALSE);
                result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
        }
        
        
    }
 
    //------------------- Delete a Company --------------------------------------------------------
    
    @RequestMapping(value = "/company/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String,Object>> deleteCompany(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id) {
        result = new HashMap<String,Object>();
        service = new CompanyServiceImpl();
        security = new SecurityServiceImpl();
        System.out.println("Fetching Header Access Token " + accessToken);
        User user = security.inicialized(accessToken);
        if ( user==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            // se verifica que el usuario autenticado tenga permisos...
            Company object = security.hasAccessCompany(id);
            if ( object!=null ) {
                System.out.println("Fetching & Deleting Company with id " + id);
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
    
    //-------------------Retrieve Relations User Company--------------------------------------------------------
    
    @RequestMapping(value = "/company/{id}/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getAllUser(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id) {
        result = new HashMap<String,Object>();
        service = new CompanyServiceImpl();
        security = new SecurityServiceImpl();
        userserv = new UserServiceImpl();
        System.out.println("Fetching Header Access Token " + accessToken);
        // Usamos la clase security para validar la permisología del usuario
        User user = security.inicialized(accessToken);
        if ( user==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else  {
            System.out.println("Users of Company with id " + id);
            // Hacemos la busqueda del objeto Company en la base de datos
            // se verifica que el usuario autenticado tenga permisos...
            Company object = security.hasAccessCompany(id);
            if ( object!=null ) {
                // Obtenemos el listado de usuarios de una compañia
                List list = userserv.getAllByCompany(object.getId());
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
    
    //-------------------Retrieve Relations Customer by Company--------------------------------------------------------
     
    @RequestMapping(value = "/company/{id}/customer", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getAllCustomer(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id) {
        result = new HashMap<String,Object>();
        service = new CompanyServiceImpl();
        security = new SecurityServiceImpl();
        cstmrserv = new CustomerServiceImpl();
        System.out.println("Fetching Header Access Token " + accessToken);
        // Usamos la clase security para validar la permisología del usuario
        User user = security.inicialized(accessToken);
        if ( user==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else  {
            System.out.println("Users of Company with id " + id);
            // Hacemos la busqueda del objeto Company en la base de datos
            // se verifica que el usuario autenticado tenga permisos...
            Company object = security.hasAccessCompany(id);
            if ( object!=null ) {
                // Obtenemos el listado de customer de una compañia
                List list = cstmrserv.getAllByCompany(object.getId());
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
    
    //-------------------Retrieve Relations Products by Company--------------------------------------------------------
     
    @RequestMapping(value = "/company/{id}/product", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getAllProduct(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id) {
        result = new HashMap<String,Object>();
        service = new CompanyServiceImpl();
        security = new SecurityServiceImpl();
        prodserv = new ProductServiceImpl();
        System.out.println("Fetching Header Access Token " + accessToken);
        // Usamos la clase security para validar la permisología del usuario
        User user = security.inicialized(accessToken);
        if ( user==null ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else  {
            System.out.println("Products of Company with id " + id);
            // Hacemos la busqueda del objeto Company en la base de datos
            // se verifica que el usuario autenticado tenga permisos...
            Company object = security.hasAccessCompany(id);
            if ( object!=null ) {
                // Obtenemos el listado de customer de una compañia
                List list = prodserv.getAllByCompany(object.getId());
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
    
}
