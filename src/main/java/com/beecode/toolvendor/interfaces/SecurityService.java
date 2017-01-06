/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.interfaces;

import com.beecode.toolvendor.model.Company;
import com.beecode.toolvendor.model.User;
import com.beecode.toolvendor.model.UserType;

/**
 *
 * @author luisvespa
 */
public interface SecurityService {
    
    public static final String USER_ADMIN = "A";
    public static final String USER_MOVIL = "M";
    
    public User authentication(String email, String password);
    
    public User inicialized(String TokenId);
    
    public User getUser();
    
    public Company getCompany();
    
    public UserType getUserType();
    
    public boolean isAdmin();
    
    public boolean equalsUser(Integer userId);
    
    public boolean equalsCompany(Integer companyId);
    
    
}
