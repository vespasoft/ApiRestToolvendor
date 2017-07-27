/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luisvespa
 */
public class PDFServiceImpl {
    
    /*
        This method create a pdf sample document 
    */
    public void CreateSampleDocument() {
        
        try {
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            
            PdfWriter writer = PdfWriter.getInstance(document,
                    new FileOutputStream(System.getenv("OPENSHIFT_DATA_DIR") + "ITextTest.pdf"));
            
            document.open();
            
            Anchor anchorTarget = new Anchor("First page of the document.");
            anchorTarget.setName("BackToTop");
            Paragraph paragraph1 = new Paragraph();

            paragraph1.setSpacingBefore(50);

            paragraph1.add(anchorTarget);
            document.add(paragraph1);

            document.add(new Paragraph("Some more text on the first page with different color and font type.", 

            FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD, new CMYKColor(0, 255, 0, 0))));
            
            document.close();
            
            // create a client connection based on credentials
            AmazonS3Service s3service = new AmazonS3Service();
            s3service.getBucket("toolvendor-files-bucket");
            s3service.createFolder("toolvendor-files-bucket", "products");
            
            String fileName = "files/ITextTest.pdf";
            s3service.uploadFile("toolvendor-files-bucket", fileName, 
                        new File(System.getenv("OPENSHIFT_DATA_DIR") + "ITextTest.pdf"));
            
        } catch (DocumentException | FileNotFoundException ex) {
            Logger.getLogger(PDFServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
