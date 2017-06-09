/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.security;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author luisvespa
 */
public class AESCryptTest {
    
    public AESCryptTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of encrypt method, of class AESCrypt.
     */
    @Test
    public void testEncrypt() {
        System.out.println("encrypt");
        String plainText = "ANTONELLA VESPA";
        AESCrypt instance = new AESCrypt();
        String cryptedText = instance.encrypt(plainText);
        System.out.println("encrypt "+ cryptedText);
        String decryptText = instance.decrypt(cryptedText);
        System.out.println("decryptText "+ decryptText);
    }
    
}
