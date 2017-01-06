/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.controller;

import com.beecode.toolvendor.model.User;
import com.beecode.toolvendor.service.CallServiceImpl;
import com.beecode.toolvendor.service.CompanyServiceImpl;
import com.beecode.toolvendor.service.ContactServiceImpl;
import com.beecode.toolvendor.service.CustomerServiceImpl;
import com.beecode.toolvendor.service.GroupsServiceImpl;
import com.beecode.toolvendor.service.SecurityServiceImpl;
import com.beecode.toolvendor.service.UserServiceImpl;
import com.beecode.toolvendor.service.VisitServiceImpl;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author luisvespa
 */
@Controller
public class UserController extends AppPreferences {
    // ------------------------------- OBJECTS ----------------------------------------
    Map<String,Object> result = new HashMap<String,Object>();
    
    // ------------------------------- SERVICES ----------------------------------------
    UserServiceImpl service = new UserServiceImpl();
    SecurityServiceImpl security = new SecurityServiceImpl();
    CustomerServiceImpl cstmrserv = new CustomerServiceImpl();
    CompanyServiceImpl companyserv = new CompanyServiceImpl();
    
    
    //-------------------Retrieve All Customer By User--------------------------------------------------------
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getAllUser(@RequestHeader(value="Access-Token") String accessToken) {
        
        System.out.println("Fetching Header Access Token " + accessToken);
        result = new HashMap<String, Object>();
        service = new UserServiceImpl();
        security = new SecurityServiceImpl();
        // Usamos la clase security para validar la permisología del usuario
        User session = security.inicialized(accessToken);
        if ( session==null || accessToken.isEmpty() ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else  {
            System.out.println("Get all user by company with id " + session.getCompanyId());
            // se verifica que el usuario autenticado tenga permisos..
            if ( security.isAdmin() ) {
                // Obtenemos el listado de usuarios de la compañia
                List list = service.getAllByCompany(session.getCompanyId());
                result.put("success", Boolean.TRUE);
                result.put("result", list);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                result.put("success", Boolean.FALSE);
                result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
        }
    }
    
    //-------------------Retrieve Single User--------------------------------------------------------
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getUser(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id) {
        
        System.out.println("Fetching Header Access Token " + accessToken);
        result = new HashMap<String,Object>();
        service = new UserServiceImpl();
        security = new SecurityServiceImpl();
        
        User session = security.inicialized(accessToken);
        if ( session==null || accessToken.isEmpty() ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            // Use headers to get the information about all the request headers
            System.out.println("User with id " + session.getId());
            // se verifica que el usuario autenticado tenga permisos..
            User object = security.hasAccessUser(id);
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
    
    //-------------------Authentication User--------------------------------------------------------
    @RequestMapping(value = "/user/auth", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String,Object>> auth(@RequestBody User login, UriComponentsBuilder ucBuilder) {
        result = new HashMap<String,Object>();
        service = new UserServiceImpl();
        security = new SecurityServiceImpl();
        String message="";
        User user = null;
        
        // Se consulta en la bd los grupos asignados a este usuario.
        System.out.println("Login: " + login);
        
        if ( login==null ) {
            message="Username and Password is required.";
        } else if ( login.getEmail()==null ) {
            message="Username is required.";
        } else if ( login.getPassword()==null ) {
            message="Password is required.";    
        } else {
            user = security.authentication(login.getEmail(), login.getPassword());
            
            if( ( user==null) ) {
                message="The username or password is invalid.";
            } else 
                message="";
        }
            
        //-------------- si ocurrio un error la variable contiene el mensaje de error ---------------
        if ( message.length()>0 ) {
            result.put("success", Boolean.FALSE);
            result.put("message", message);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else  {
            System.out.println("User encontrado: " + user.getName());
            String token = security.createJWT(user);
            User uid = security.parseJWT(token);
            result.put("success", Boolean.TRUE);
            result.put("message", "Bienvenido al sistema toolvendor");
            result.put("tokenId", token);
            result.put("result", user);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        //HttpHeaders headers = new HttpHeaders();
        //headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(currentUser.getUserId()).toUri());
    }
    
    //-------------------Authentication User--------------------------------------------------------
    @RequestMapping(value = "/user/forgot", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String,Object>> forgot(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        result = new HashMap<String,Object>();
        service = new UserServiceImpl();
        security = new SecurityServiceImpl();
        String message="";
        
        /* se ejecuta el metodo forgot creado en la clase UserService
         * este metodo verifica que el email exista en la bd y si es correcto
         * cambia la clave del usuario por una temporal y envia un email al usuario
         * con la nueva clave
         */
        System.out.println("forgot: " + user);
        message = service.forgot(user);
        //-------------- si ocurrio un error la variable contiene el mensaje de error ---------------
        if ( message.length()>0 ) {
            result.put("success", Boolean.FALSE);
            result.put("message", message);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else  {
            System.out.println("User encontrado: " + user.getName());
            String token = security.createJWT(user);
            User uid = security.parseJWT(token);
            result.put("success", Boolean.TRUE);
            result.put("message", "Bienvenido al sistema toolvendor");
            result.put("tokenId", token);
            result.put("result", user);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        //HttpHeaders headers = new HttpHeaders();
        //headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(currentUser.getUserId()).toUri());
    }

    //-------------------Retrieve All Customer By User--------------------------------------------------------
    @RequestMapping(value = "/user/{id}/customer", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getAllCustomer(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id) {
        
        result = new HashMap<String,Object>();
        service = new UserServiceImpl();
        security = new SecurityServiceImpl();
        System.out.println("Fetching Header Access Token " + accessToken);
        // Usamos la clase security para validar la permisología del usuario
        User session = security.inicialized(accessToken);
        if ( session==null || accessToken.isEmpty() ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else  {
            System.out.println("Customer by User with id " + id);
            // se verifica que el usuario autenticado tenga permisos..
            User object = security.hasAccessUser(id);
            if ( object!=null ) {
                // Obtenemos el listado de customer de una compañia
                List list = cstmrserv.getAllByUser(object.getId());
                result.put("success", Boolean.TRUE);
                result.put("result", list);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                result.put("success", Boolean.FALSE);
                result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
        }
    }
    
    //-------------------Retrieve All Call By User--------------------------------------------------------
    @RequestMapping(value = "/user/{id}/calls", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getAllCall(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id) {
        CallServiceImpl callserv = new CallServiceImpl();
        System.out.println("Fetching Header Access Token " + accessToken);
        result = new HashMap<String,Object>();
        // Usamos la clase security para validar la permisología del usuario
        User session = security.inicialized(accessToken);
        if ( session==null || accessToken.isEmpty() ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else  {
            System.out.println("Calls by User with id " + id);
            // se verifica que el usuario autenticado tenga permisos..
            User object = security.hasAccessUser(id);
            if ( object!=null ) {
                // Obtenemos el listado de llamadas de un usuario
                List list = callserv.getAllByUser(object.getId());
                result.put("success", Boolean.TRUE);
                result.put("result", list);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                result.put("success", Boolean.FALSE);
                result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
            
        }
    }
    
    //-------------------Retrieve All Contact By User--------------------------------------------------------
    @RequestMapping(value = "/user/{id}/contact", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getAllContact(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id) {
        ContactServiceImpl contactserv = new ContactServiceImpl();
        System.out.println("Fetching Header Access Token " + accessToken);
        result = new HashMap<String,Object>();
        // Usamos la clase security para validar la permisología del usuario
        User session = security.inicialized(accessToken);
        if ( session==null || accessToken.isEmpty() ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else  {
            System.out.println("Contact by User with id " + id);
            // se verifica que el usuario autenticado tenga permisos..
            User object = security.hasAccessUser(id);
            if ( object!=null ) {
                // Obtenemos el listado de contactos de un usuario
                List list = contactserv.getAllByUser(object.getId());
                result.put("success", Boolean.TRUE);
                result.put("result", list);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                result.put("success", Boolean.FALSE);
                result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
            
        }
    }
    
    //-------------------Retrieve All Groups By User--------------------------------------------------------
    @RequestMapping(value = "/user/{id}/groups", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getAllGroups(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id) {
        GroupsServiceImpl groupsserv = new GroupsServiceImpl();
        System.out.println("Fetching Header Access Token " + accessToken);
        result = new HashMap<String,Object>();
        // Usamos la clase security para validar la permisología del usuario
        User session = security.inicialized(accessToken);
        if ( session==null || accessToken.isEmpty() ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else  {
            System.out.println("Groups by User with id " + id);
            // se verifica que el usuario autenticado tenga permisos..
            User object = security.hasAccessUser(id);
            if ( object!=null ) {
                // Obtenemos el listado de grupos de un usuario
                List list = groupsserv.getAllByUser(object.getId());
                result.put("success", Boolean.TRUE);
                result.put("result", list);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                result.put("success", Boolean.FALSE);
                result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
            
        }
    }
    
    //-------------------Retrieve All Zone By User--------------------------------------------------------
    @RequestMapping(value = "/user/{id}/zone", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getAllZone(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id) {
        ZoneServiceImpl zoneserv = new ZoneServiceImpl();
        System.out.println("Fetching Header Access Token " + accessToken);
        result = new HashMap<String,Object>();
        // Usamos la clase security para validar la permisología del usuario
        User session = security.inicialized(accessToken);
        if ( session==null || accessToken.isEmpty() ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else  {
            System.out.println("Zone by User with id " + id);
            // se verifica que el usuario autenticado tenga permisos..
            User object = security.hasAccessUser(id);
            if ( object!=null ) {
                // Obtenemos el listado de zonas de un usuario
                List list = zoneserv.getAllByUser(object.getId());
                result.put("success", Boolean.TRUE);
                result.put("result", list);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                result.put("success", Boolean.FALSE);
                result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
            
        }
    }
    
    //-------------------Retrieve All Visit By User--------------------------------------------------------
    @RequestMapping(value = "/user/{id}/visit", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getAllVisit(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id) {
        VisitServiceImpl visitserv = new VisitServiceImpl();
        System.out.println("Fetching Header Access Token " + accessToken);
        result = new HashMap<String,Object>();
        // Usamos la clase security para validar la permisología del usuario
        User session = security.inicialized(accessToken);
        if ( session==null || accessToken.isEmpty() ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else  {
            System.out.println("Visit by User with id " + id);
            // se verifica que el usuario autenticado tenga permisos..
            User object = security.hasAccessUser(id);
            if ( object!=null ) {
                // Obtenemos el listado de visitas de un usuario
                List list = visitserv.getAllByUser(object.getId());
                result.put("success", Boolean.TRUE);
                result.put("result", list);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                result.put("success", Boolean.FALSE);
                result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }        
        }
    }
    
    //-------------------Create a User--------------------------------------------------------
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String,Object>> createUser(@RequestHeader(value="Access-Token") String accessToken, @RequestBody User user,  UriComponentsBuilder ucBuilder) {
        
        System.out.println("Fetching Header Access Token " + accessToken);
        result = new HashMap<String,Object>();
        service = new UserServiceImpl();
        security = new SecurityServiceImpl();
        
        User session = security.inicialized(accessToken);
        if ( session==null || accessToken.isEmpty() ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            // Se forza a guardar el registro relacionado con el Token
            user.setCompanyId(session.getCompanyId());
            //----------------------------- crea un nuevo registro -------------------------------
            String message = service.save(user);
            if ( message.isEmpty() ) {
                service = new UserServiceImpl();
                User object = service.findByEmail(user.getEmail());
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
     
    // ------------------- Update a User --------------------------------------------------------
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Map<String,Object>> updateUser(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id, @RequestBody User user) {
        
        System.out.println("Fetching Header Access Token " + accessToken);
        result = new HashMap<String,Object>();
        service = new UserServiceImpl();
        security = new SecurityServiceImpl();
        
        User session = security.inicialized(accessToken);
        if ( session==null || accessToken.isEmpty() ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            System.out.println("Updating user " + id);
            // se verifica que el usuario autenticado tenga permisos..
            //------ se verifica que el Id existe y pertenece a la misma empresa ----
            User object = security.hasAccessUser(id);
            if ( object!=null ) {
                // Se forza a guardar el registro relacionado con el Token
                user.setCompanyId(session.getCompanyId());
                //------ se actualiza el registro en la base de datos ----
                String message = service.update(user);
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
 
    //------------------- Delete a User --------------------------------------------------------
    
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Map<String,Object>> deleteUser(@RequestHeader(value="Access-Token") String accessToken, @PathVariable("id") int id) {
        
        System.out.println("Fetching Header Access Token " + accessToken);
        result = new HashMap<String,Object>();
        service = new UserServiceImpl();
        security = new SecurityServiceImpl();
        
        User session = security.inicialized(accessToken);
        if ( session==null || accessToken.isEmpty() ) {
            result.put("success", Boolean.FALSE);
            result.put("message", AppPreferences.MESSAGE_USER_NOT_ACCESS);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            System.out.println("Fetching & Deleting User with id " + id);
            // se verifica que el usuario autenticado tenga permisos..
            //------ se obtiene el registro de la busqueda ----
            User object = security.hasAccessUser(id);
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
