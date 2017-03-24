/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;

import com.beecode.toolvendor.dao.CallDAO;
import com.beecode.toolvendor.dao.CompanyDAO;
import com.beecode.toolvendor.dao.ContactDAO;
import com.beecode.toolvendor.dao.CustomerDAO;
import com.beecode.toolvendor.dao.ProductDAO;
import com.beecode.toolvendor.dao.UserDAO;
import com.beecode.toolvendor.interfaces.SecurityService;
import com.beecode.toolvendor.model.Call;
import com.beecode.toolvendor.model.Company;
import com.beecode.toolvendor.model.Contact;
import com.beecode.toolvendor.model.Customer;
import com.beecode.toolvendor.model.Product;
import com.beecode.toolvendor.model.User;
import com.beecode.toolvendor.model.UserType;
import com.beecode.toolvendor.security.JWTUtil;
import java.util.Objects;

/**
 *
 * @author luisvespa
 */
public class SecurityServiceImpl extends JWTUtil implements SecurityService {

    UserDAO udao;
    CompanyDAO cdao;
    CustomerDAO cstmrdao;
    ProductDAO productdao;
    ContactDAO contactdao;
    CallDAO calldao;
    User user = null;
    Company company = null;

    public SecurityServiceImpl() {
        udao = new UserDAO();
        cdao = new CompanyDAO();
        cstmrdao = new CustomerDAO();
        productdao = new ProductDAO();
        contactdao = new ContactDAO();
        calldao = new CallDAO();
    }
    
    /* 
        Method: authentication
        Description: este metodo solicitad el email y password del usuario, con 
        este verifica que el usuario exista en la BD, este habilitado y tenga los permisos necesarios para 
        ejecutar la autenticación, retornando un objeto User con los datos del usuario y el TokenId que necesitará 
        para ejecutar las peticiones futuras.
        @param: tokenId
        @out: return a User object
    */
    @Override
    public User authentication(String email, String password) {
        // Este método consulta los datos del usuario verificando coincidencias de email y password
        return udao.authentication(email, password);
    }
    
    /* 
        Method: inicialized
        Description: este metodo verifica un TokenId, con 
        esto verifica que el usuario exista en la BD, este habilitado y tenga los permisos necesarios para 
        entrar al sistema, retornando un objeto User con los datos del usuario.
        @param: tokenId
        @out: return a User object
    */
    @Override
    public User inicialized(String TokenId) {
        try {
            udao = new UserDAO();
            cdao = new CompanyDAO();
            if ( !TokenId.isEmpty() ) {
                // Obtenemos el objeto User del usuario relacionado con el TokenId
                user = parseJWT(TokenId);
                // Obtenemos el objeto Company del usuario
                company = cdao.findById(user.getCompanyId());
            } else {
                user = null;
                company = null;
            }
            
        } catch (Exception e)  {
            System.out.println("Error inicialized token " + e.getMessage());
        }
        return user;
    }
    
    public Company hasAccessCompany(Integer companyId) {
        
        // Se obtiene los datos del customer si pertenece a la compañia del usuario logueado
        cdao = new CompanyDAO();
        Company result = cdao.findById(companyId);
        if ( result != null ) {
            // se verifica que el usuario autenticado tenga permisos ADMIN..
            if ( isAdmin() && equalsCompany(result.getId()) ) {
                return result;
            } else {
                return null;
            }
        } else {
            return null;
        }
        
    }
    
    public User hasAccessUser(Integer userId) {
        // Se obtiene los datos del customer si pertenece a la compañia del usuario logueado
        udao = new UserDAO();
        User result = udao.findById(userId, user.getCompanyId());
        if ( result != null ) {
            // se verifica que el usuario autenticado tenga permisos..
            if ( isAdmin() || equalsUser(result.getId()) ) {
                return result;
            } else {
                return null;
            }
        } else {
            return null;
        }
        
    }
    
    public Customer hasAccessCustomer(Integer cstmrId) {
        // Se obtiene los datos del customer si pertenece a la compañia del usuario logueado
        cstmrdao = new CustomerDAO();
        Customer result = cstmrdao.findById(cstmrId, user.getCompanyId());
        if ( result != null ) {
            // se verifica que el usuario autenticado tenga permisos..
            if ( isAdmin() || equalsUser(result.getUserId()) ) {
                return result;
            } else {
                return null;
            }
        } else {
            return null;
        }
        
    }
    
    public Product hasAccessProduct(Integer productId) {
        // Se obtiene los datos del customer si pertenece a la compañia del usuario logueado
        productdao = new ProductDAO();
        Product result = productdao.findById(productId, user.getCompanyId());
        if ( result != null ) {
            // se verifica que el usuario autenticado tenga permisos..
            if ( equalsCompany(result.getCompanyId()) ) {
                return result;
            } else {
                return null;
            }
        } else {
            return null;
        }
        
    }
    
    public Contact hasAccessContact(Integer contactId) {
        // Se obtiene los datos del contacto si pertenece al usuario logueado
        contactdao = new ContactDAO();
        Contact result = contactdao.findById(contactId);
        if ( result != null ) {
            // se verifica que el usuario autenticado tenga permisos..
            if ( isAdmin() || equalsUser(result.getUserId()) ) {
                return result;
            } else {
                return null;
            }
        } else {
            return null;
        }
        
    }
    
    public Call hasAccessCall(Integer callId) {
        // Se obtiene los datos del contacto si pertenece al usuario logueado
        calldao = new CallDAO();
        Call result = calldao.findById(callId);
        if ( result != null ) {
            // se verifica que el usuario autenticado tenga permisos..
            if ( isAdmin() || equalsUser(result.getUserId()) ) {
                return result;
            } else {
                return null;
            }
        } else {
            return null;
        }
        
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public Company getCompany() {
        return company;
    }

    @Override
    public UserType getUserType() {
        if (user == null)
            return null;
        else 
            return user.getUsertype();
    }

    @Override
    public boolean isAdmin() {
        if (user == null)
            return false;
        else 
            return user.getUsertype().getType().equalsIgnoreCase(USER_ADMIN);
    }
    
    @Override
    public boolean equalsUser(Integer userId) {
        return Objects.equals(user.getId(), userId);
    }
    
    @Override
    public boolean equalsCompany(Integer companyId) {
        return Objects.equals(user.getCompanyId(), companyId);
    }
    
    
}
