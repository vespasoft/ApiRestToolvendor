/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.security;

import com.beecode.toolvendor.dao.UserDAO;
import com.beecode.toolvendor.interfaces.JWT;
import com.beecode.toolvendor.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author luisvespa
 */
public class JWTUtil implements JWT {
    
    UserDAO udao = new UserDAO();

    public JWTUtil() {
        /*AESCrypt aes = new AESCrypt();
        String encryp = aes.encrypt("TOOLVENDOR APP API");
        System.out.println("ENCRIPTADO: " + encryp);
        String decryp = aes.decrypt(encryp);
        System.out.println("DESENCRIPTADO: " + decryp);*/
    }

    @Override
    public String createJWT(User user) {
        try {
            if ( user!=null) {
                //The JWT signature algorithm we will be using to sign the token
                SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

                long nowMillis = System.currentTimeMillis();
                Date now = new Date(nowMillis);

                //We will sign our JWT with our ApiKey secret
                byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SERVICE_KEY);
                Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

                //Let's set the JWT Claims
                JwtBuilder builder = Jwts.builder().setId(user.getId().toString())
                                            .setIssuedAt(now)
                                            .setSubject(user.getCompany().getId().toString())
                                            .setIssuer(user.getName())
                                            .signWith(signatureAlgorithm, signingKey);
                
                //if it has been specified, let's add the expiration
                //if (ttlMillis >= 0) {
                //long expMillis = nowMillis + ttlMillis;
                //    Date exp = new Date(expMillis);
                //    builder.setExpiration(exp);
                //}

                //Builds the JWT and serializes it to a compact, URL-safe string
                return builder.compact();
            } else {
                return "";
            }
            
        } catch ( Exception e) {
            System.out.println("Error creating Token: " + e.getMessage());
            return "";
        }
        
    }
    
    /* 
        Method: parseJWT
        Description: este metodo hace la reversa del Json Web Token y obtiene el ID del usuario, y con 
        este verifica que el usuario exista en la BD, este habilitado y tenga los permisos necesarios para 
        ejecutar la petición.
        @param: tokenId
        @out: return a User object
    */
    @Override
    public User parseJWT(String token) {
        User result = null;
        try {
            System.out.println(" Header Access Token " + token);
            if ( token.length()>0 ) {
                //This line will throw an exception if it is not a signed JWS (as expected)
                Claims claims = Jwts.parser()         
                   .setSigningKey(DatatypeConverter.parseBase64Binary(SERVICE_KEY))
                   .parseClaimsJws(token).getBody();
                System.out.println("TOKEN JWT PARSED *********************** ");
                System.out.println("ID: " + claims.getId());
                System.out.println("Subject: " + claims.getSubject());
                System.out.println("Issuer: " + claims.getIssuer());
                System.out.println("Expiration: " + claims.getExpiration());
                String userId = claims.getId();
                String companyId = claims.getSubject();
                System.out.println(" User con Acceso " + userId);
                if ( userId.length()>0 )
                   result = udao.findById(Integer.valueOf(userId), Integer.valueOf(companyId));
            }
        } catch ( Exception e) {
            System.out.println(" Error parse JWT " + e.getMessage());
        }
        
        return result;
    }
    
}
