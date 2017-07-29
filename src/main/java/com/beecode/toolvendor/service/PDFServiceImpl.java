/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;

import com.beecode.toolvendor.model.User;
import com.beecode.toolvendor.model.Visit;
import com.beecode.toolvendor.model.VisitPicture;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
            
            // CHAPTER
            Paragraph title1 = new Paragraph("Chapter 1", 
            FontFactory.getFont(FontFactory.HELVETICA, 
            18, Font.BOLDITALIC, new CMYKColor(0, 255, 255,17)));
            Chapter chapter1 = new Chapter(title1, 1);
            chapter1.setNumberDepth(0);
            
            // SECTION
            Paragraph title11 = new Paragraph("This is Section 1 in Chapter 1", 
            FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, 
            new CMYKColor(0, 255, 255,17)));
            Section section1 = chapter1.addSection(title11);
            Paragraph someSectionText = new Paragraph("This text comes as part of section 1 of chapter 1.");
            section1.add(someSectionText);
            someSectionText = new Paragraph("Following is a 3 X 2 table.");
            section1.add(someSectionText);
            
            // TABLE 
            PdfPTable t = new PdfPTable(3);
            t.setSpacingBefore(25);
            t.setSpacingAfter(25);
            PdfPCell c1 = new PdfPCell(new Phrase("Header1"));  
            t.addCell(c1);
            PdfPCell c2 = new PdfPCell(new Phrase("Header2"));
            t.addCell(c2);
            PdfPCell c3 = new PdfPCell(new Phrase("Header3"));
            t.addCell(c3);
            Image image2;
            Image image3;
            try {
                image2 = Image.getInstance("https://s3.amazonaws.com/toolvendor-files-bucket/products/121123_1_8826_glm25181.jpg");
                image2.scaleAbsolute(120f, 120f);
                t.addCell(image2);
                t.addCell("1.2");
                image3 = Image.getInstance("https://s3.amazonaws.com/toolvendor-files-bucket/products/42e2ea226a2ef01117a087beafde2223363cd978.png");
                image3.scaleAbsolute(120f, 120f);
                t.addCell(image3);
            } catch (BadElementException | IOException ex) {
                Logger.getLogger(PDFServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            section1.add(t);
            
            // List Object
            List l = new List(true, false, 10);
            l.add(new ListItem("First item of list"));
            l.add(new ListItem("Second item of list"));
            section1.add(l);

            // Image Object
            try {
                Image image = Image.getInstance("https://s3.amazonaws.com/toolvendor-files-bucket/products/e94bfb16ad3cefb62d1e0e83f355133fc4322b7e.png");
                image.scaleAbsolute(120f, 120f);
                section1.add(image);
            } catch (BadElementException | IOException ex) {
                Logger.getLogger(PDFServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            document.add(chapter1);
            
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
    
    /*
        This method create a pdf sample document 
    */
    public void CreateVisitDocument(Visit visit, User vendor) {
        VisitPictureServiceImpl pictureserv = new VisitPictureServiceImpl();
        java.util.List<VisitPicture> pictures = pictureserv.getAllByVisit(visit.getId());
        try {
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            
            PdfWriter writer = PdfWriter.getInstance(document,
                    new FileOutputStream(System.getenv("OPENSHIFT_DATA_DIR") + "visitdoc.pdf"));
            
            document.open();
            
            // HEADER
            Paragraph title1 = new Paragraph(vendor.getCompany().getCompany(), 
            FontFactory.getFont(FontFactory.HELVETICA, 
            18, Font.BOLDITALIC, new CMYKColor(0, 255, 255,17)));
            Chapter chapter1 = new Chapter(title1, 1);
            chapter1.setNumberDepth(0);
            
            
            // SECTION 
            Paragraph title11 = new Paragraph("Comprobante de visita", 
            FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, 
            new CMYKColor(0, 255, 255,17)));
            Section section1 = chapter1.addSection(title11);
            
            
            Paragraph someSectionText = new Paragraph("Detalle de la visita");
            section1.add(someSectionText);
            
            // TABLE 
            PdfPTable tt = new PdfPTable(2);
            tt.setSpacingBefore(25);
            tt.setSpacingAfter(25);
            PdfPCell cc1 = new PdfPCell(new Phrase("Datos del Cliente"));  
            tt.addCell(cc1);
            PdfPCell cc2 = new PdfPCell(new Phrase("Datos Visita"));
            tt.addCell(cc2);
            // row 1
            // datos del cliente
            List dcliente = new List(true, false, 10);
            dcliente.add(new ListItem("Cliente: " + visit.getCustomer().getCompanyName()));
            dcliente.add(new ListItem("Contact: " + visit.getCustomer().getContactName()));
            dcliente.add(new ListItem("Phone: " + visit.getCustomer().getContactPhone()));
            tt.addCell(dcliente.toString());
            
            List dvisit = new List(true, false, 10);
            dcliente.add(new ListItem("Scheduled Date: " + visit.getScheduledDate()));
            dcliente.add(new ListItem("Checkin: " + visit.getCheckin()));
            dcliente.add(new ListItem("Checkout: " + visit.getCheckin()));
            dcliente.add(new ListItem("Visit Type: " + visit.getVisitType().getName()));
            dcliente.add(new ListItem("Reason: " + visit.getReason()));
            tt.addCell(dvisit.toString());
            
            section1.add(tt);
            
            someSectionText = new Paragraph("Comment: " + visit.getComment());
            section1.add(someSectionText);
            
            someSectionText = new Paragraph("Evidencias fotograficas");
            section1.add(someSectionText);
            
            // fotos 
            for (int i = 0; i < pictures.size(); i++) {
                try {
                    Image image = Image.getInstance(pictures.get(i).getPicture());
                    image.scaleAbsolute(120f, 120f);
                    section1.add(image);
                } catch (BadElementException | IOException ex) {
                    Logger.getLogger(PDFServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            // List Object
            List l = new List(true, false, 10);
            l.add(new ListItem("Vendedor: " + vendor.getName()));
            l.add(new ListItem("Phone: " + vendor.getPhone()));
            l.add(new ListItem("Email: " + vendor.getEmail()));
            section1.add(l);
            
            // Firma del vendedor
            try {
                Image image = Image.getInstance(visit.getFirm());
                image.scaleAbsolute(120f, 120f);
                section1.add(image);
            } catch (BadElementException | IOException ex) {
                Logger.getLogger(PDFServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

            document.add(chapter1);
            
            document.close();
            
            // create a client connection based on credentials
            // Este codigo subia el PDF al repositorio s3, pero ya no es necesario 
            // ya que se adjunta en el email que se enviar al cliente y al administrador
            /*AmazonS3Service s3service = new AmazonS3Service();
            s3service.getBucket("toolvendor-files-bucket");
            s3service.createFolder("toolvendor-files-bucket", "files");
            
            // url complete https://s3.amazonaws.com/toolvendor-files-bucket/files/
            String fileName = "files/" + visit.getId() + ".pdf";
            s3service.uploadFile("toolvendor-files-bucket", fileName, 
                        new File(System.getenv("OPENSHIFT_DATA_DIR") +  "visitdoc.pdf"));*/
            
        } catch (DocumentException | FileNotFoundException ex) {
            Logger.getLogger(PDFServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
