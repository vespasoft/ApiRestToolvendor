/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.security;

import com.beecode.toolvendor.interfaces.AES;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author luisvespa
 */
public class AESCrypt implements AES {

    public static SecureRandom sr = new SecureRandom();
    
    //private String clave = "FooBar1234567890"; // 128 bit
    private byte[] iv = new byte[16];   // Vector de inicialización

    public AESCrypt() {
        // para utilizar los metodos de sifrado AES es necesario inicializar la clase
        sr.nextBytes(iv);
    }
    
    /**
    * Metodo para encriptar un texto
    * @param String texto
    * @return String texto encriptado
    */
    public String encrypt(String plainText) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            SecretKeySpec sks = new SecretKeySpec(ENCRYP_PASS.getBytes("UTF-8"), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, sks, new IvParameterSpec(iv));

            byte[] encriptado = cipher.doFinal(plainText.getBytes());
            return DatatypeConverter.printBase64Binary(encriptado);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
    * Metodo para desencriptar un texto
    * @param texto Texto encriptado
    * @return String texto desencriptado
    */
    public String decrypt(String cryptedText) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            SecretKeySpec sks = new SecretKeySpec(ENCRYP_PASS.getBytes("UTF-8"), "AES");
            cipher.init(Cipher.DECRYPT_MODE, sks, new IvParameterSpec(iv));

            byte[] dec = cipher.doFinal(DatatypeConverter.parseBase64Binary(cryptedText));
            return new String(dec);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    

}
