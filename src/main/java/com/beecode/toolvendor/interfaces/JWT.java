/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.interfaces;

import com.beecode.toolvendor.model.User;

/**
 *
 * @author luisvespa
 */
public interface JWT {
    public static final String SERVICE_KEY = "luisana&mariela";
    public static final String AUTH_TOKEN = "auth_token";
    
    public String createJWT(User user);
    
    public User parseJWT(String token);
    
}
