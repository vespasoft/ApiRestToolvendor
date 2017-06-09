/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.interfaces;

/**
 *
 * @author luisvespa
 */
public interface AES {
    public static final String ENCRYP_PASS = "FooBar1234567890";
    public static final String VECTOR_INICIO = "Sx5kaVX+D-3hvGr!";
    
    public String encrypt(String plainText);
    
    public String decrypt(String cryptedText);
}
