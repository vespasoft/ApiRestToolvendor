/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.util;

import java.util.Random;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author luisvespa
 */
public class StringUtil {
    
    /*
     * converts a Java object into JSON representation
     */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static JsonNode asStringJson(final String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonFactory factory = mapper.getJsonFactory(); // since 2.1 use mapper.getFactory() instead
            JsonParser jp = factory.createJsonParser("{\"k1\":\"v1\"}");
            return mapper.readTree(jp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static String generateTokenString (int length)  {
            String token="";
            char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
            StringBuilder sb = new StringBuilder();
            Random random = new Random();
            for (int i = 0; i < length; i++) {
                    char c = chars[random.nextInt(chars.length)];
                    sb.append(c);
            }
            token = sb.toString();
            System.out.println(token);
            return token;
    }
    
}
