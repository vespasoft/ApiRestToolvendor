/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.security;

import com.beecode.toolvendor.util.StringUtil;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author luisvespa
 */
public class SecurityUtil {

    public SecurityUtil() {
    }
    
    public String encodeHexSHA512(String password) {
        MessageDigest md = null;
        try {
            //SHA-512
            md= MessageDigest.getInstance("SHA-512");
            md.update(password.getBytes());
            byte[] mb = md.digest();
            return String.valueOf(Hex.encodeHex(mb));
        } catch (NoSuchAlgorithmException e) {
            //Error
            System.out.println(e.getMessage());
            return "";
        }
    }
    
    public String encodeHexSHA1(String password) {
        MessageDigest md = null;
        try {
            //SHA-1
            md= MessageDigest.getInstance("SHA-1");
            md.update(password.getBytes());
            byte[] mb = md.digest();
            return String.valueOf(Hex.encodeHex(mb));
        } catch (NoSuchAlgorithmException e) {
            //Error
            System.out.println(e.getMessage());
            return "";
        }
    }
    
    public String encodeHexMD5(String password) {
        MessageDigest md = null;
        try {
            //MD5
            md= MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] mb = md.digest();
            return String.valueOf(Hex.encodeHex(mb));
        } catch (NoSuchAlgorithmException e) {
            //Error
            System.out.println(e.getMessage());
            return "";
        }
    }
    
}
