/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.controller;

import com.beecode.toolvendor.security.SecurityUtil;
import com.beecode.toolvendor.service.AmazonS3Service;
import com.beecode.toolvendor.util.ImageValidator;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
 
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author luisvespa
 */
@WebServlet("/upload")
@MultipartConfig(location = "/var/lib/openshift/5865718d89f5cf3e95000074/app-root/data")
public class FileUploadServlet extends HttpServlet {
 
    private static final long serialVersionUID = 1L;
 
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
 
        getServletContext().getRequestDispatcher("/WEB-INF/form.jsp").forward(request, response);
    }
 
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        Collection<Part> parts = request.getParts();
        
        ImageValidator imageValidator = new ImageValidator();
        
        //out.write("{ result: " + parts.size() + "</h2>");
 
        // create a client connection based on credentials
        AmazonS3Service s3service = new AmazonS3Service();
        s3service.getBucket("toolvendor-files-bucket");
        s3service.createFolder("toolvendor-files-bucket", "products");
        
       for (Part part : parts) {
            // printEachPart(part, out);
            String imageName = getFileName(part);
            /*if ( !imageValidator.validate(imageName) ) {
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("success", false);
                data.put("message", "La extensión de la imagen es invalida. solo puede subir archivos (jpg|png|gif|bmp)");
                part.write(new Gson().toJson(data));
            } else if (part.getSize()>1024) {
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("success", false);
                data.put("message", "El tamaño del archivo que intenta subir es muy grande, El maximo permitido es de 1024KB");
                part.write(new Gson().toJson(data));
            } else {*/
                // se genera un HASH para el nombre de la imagen ...
                String token = SecurityUtil.encodeHexSHA1(imageName)+ ".png";
                // String name = StringUtil.generateTokenString(16) + ".png";
                String fileName = "products/" + token;
                // se crea la respuesta json con los datos de la imagen subida
                printJSONPart(part, out, fileName);
                //String fileName = "products/" + getFileName(part);
                // se guarda la imagen en el servidor
                part.write(token);
                //part.write(imageName);
                s3service.uploadFile("toolvendor-files-bucket", fileName, 
                        new File(System.getenv("OPENSHIFT_DATA_DIR") + token));
            //}
            
        }
        
    }
    
    private void printJSONPart(Part part, PrintWriter pw, String name) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("success", true);
        data.put("filename", name);
        data.put("url", "https://s3.amazonaws.com/toolvendor-files-bucket/" + name);
        data.put("filesize", part.getSize());
        pw.write(new Gson().toJson(data));
    }
 
    private void printEachPart(Part part, PrintWriter pw) {
        StringBuffer sb = new StringBuffer();
        sb.append("<p>");
        sb.append("Name : " + part.getName());
        sb.append("<br>");
        sb.append("Content Type : " + part.getContentType());
        sb.append("<br>");
        sb.append("was uploaded to https://s3.amazonaws.com/toolvendor-files-bucket/products/" + part.getName());
        sb.append("<br>");
        sb.append("Size : " + part.getSize());
        sb.append("<br>");
        for (String header : part.getHeaderNames()) {
            sb.append(header + " : " + part.getHeader(header));
            sb.append("<br>");
        }
        sb.append("</p>");
        pw.write(sb.toString());
 
    }
 
    private String getFileName(Part part) {
        String partHeader = part.getHeader("content-disposition");
        for (String cd : partHeader.split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
