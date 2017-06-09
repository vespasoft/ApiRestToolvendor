/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.security;

import com.beecode.toolvendor.util.StringUtil;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author luisvespa
 */
public class SecurityUtil {
    
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    
    public static String encodeHexSHA512(String password) {
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
    
    public static String encodeHexSHA1(String password) {
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
    
    public static String encodeHexMD5(String password) {
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
    
    /*
        method: generateHash
        description: Este metodo generá un hash para representar una cadena de texto en un formato
        poco legible, este hash se considera no seguro. Para mas seguridad se recomiendo aplicar
        un metodo de cifrado sincrono o asincrono.
    */
    public static String generateHash(String value) {
        // elige por ejemplo entre los algoritmos disponibles
        // en este caso un 48 byte sha-384
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-384");
            byte[] digest = md.digest(value.getBytes("UTF-8"));
            return bytesToHex(digest);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(StringUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SecurityUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
    
}
