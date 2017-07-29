/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;

import com.beecode.toolvendor.dao.VisitDAO;
import com.beecode.toolvendor.interfaces.VisitService;
import com.beecode.toolvendor.model.BackLog;
import com.beecode.toolvendor.model.Company;
import com.beecode.toolvendor.model.Customer;
import com.beecode.toolvendor.model.User;
import com.beecode.toolvendor.model.Visit;
import com.beecode.toolvendor.model.VisitPicture;
import com.beecode.toolvendor.thread.SendEmailScheduleVisitThread;
import com.beecode.toolvendor.thread.SendEmailVisitThread;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author luisvespa
 */
public class VisitServiceImpl implements VisitService {

    private static final AtomicLong counter = new AtomicLong();
    
    //----------------------------- DAO ------------------------------------------
    private VisitDAO dao;
    //----------------------------- SERVICES -------------------------------------
    private UserServiceImpl userserv;
    private CustomerServiceImpl cstmrserv;
    private VisitTypeServiceImpl visittypeserv;
    private VisitPictureServiceImpl pictureserv;
    private PDFServiceImpl pdfserv;
    private BackLogServiceImpl backlog;
    
    public VisitServiceImpl() {
        backlog = new BackLogServiceImpl();
        dao = new VisitDAO();
        userserv = new UserServiceImpl();
        cstmrserv = new CustomerServiceImpl();
        visittypeserv = new VisitTypeServiceImpl();
        pictureserv = new VisitPictureServiceImpl();
        pdfserv = new PDFServiceImpl();
    }
    
    //----------------------- Agregar nuevo registro ---------------------------------
    @Override
    public String save(Visit visit, Company company) {
        String message="";
        try {    
            if ( visit==null ) {
                message="Se espero un objeto visit en formato JSON";
            } else if ( visit.getCustomer()==null ) {
                message="El campo Customer no puede ser nullo";
            } else if ( visit.getScheduledDate()==null ) {
                message="El campo ScheduledDate no puede ser nullo";
            } else if ( visit.getUserId()==null ) {
                message="El campo UserId no puede ser nullo";
            } else if ( visit.getVisitType()==null ) {
                message="El campo VisitTypeId no puede ser nullo";
            } else if ( visit.getUserId()==0 ) {
                message="El campo UserId no puede ser nullo";
            } else if ( visit.getCustomer().getId()==0 ) {
                message="El campo CustomerId no puede ser nullo";
            } else if ( visit.getVisitType().getId()==0 ) {
                message="El campo VisitTypeId no puede estar vacio";
            } else if ( visit.getReason().length()==0 ) {
                message="El campo Reason es obligatorio";
            } else if ( !userserv.findId(visit.getUserId(), company) ) {
                message="No existe un registro con este UserId.";
            } else if ( !cstmrserv.findId(visit.getCustomer().getId(), visit.getCompanyId()) ) {
                message="No existe un registro con este CustomerId.";   
            } else if ( !visittypeserv.findId(visit.getVisitType().getId(), visit.getCompanyId()) ) {
                message="No existe un registro con este VisitTypeId.";
            } else {
                //--- Created At fecha de creación del registro
                Timestamp timestamp = new Timestamp(new Date().getTime());
                visit.setCreatedAt( timestamp );            

                // Logica que define el estatus de la visita
                if ( visit.getCheckin()==null && visit.getCheckout()==null) visit.setStatus("pending");
                else if ( visit.getCheckin()!=null && visit.getCheckout()==null) visit.setStatus("checkin");
                else if ( visit.getCheckin()!=null && visit.getCheckout()!=null) visit.setStatus("checkout");

                dao.add(visit);
                User user = userserv.findById(visit.getUserId(), company);
                // ejecuta un thread (hilo) en 2do plano donde se envia el correo.
                SendEmailScheduleVisitThread se = new SendEmailScheduleVisitThread(user, visit);
                se.start();
            }
        } catch ( Exception e ) {
            backlog.save(new BackLog(VisitServiceImpl.class.getSimpleName(), 
                                    "save",
                                    e.getMessage()));
            System.out.print("Error save: " + e.getMessage());
        }
        
        return message;
    }
    
    //----------------------- Actualizar los datos de un registro existente --------------------------
    @Override
    public String update(Visit visit, Company company) {
        Visit currentVisit = null;
        String message="";
        try {
            if ( visit==null ) {
                message="Se espero un objeto visit en formato JSON";
            } else if ( visit.getId()==null ) {
                message="El campo Id no puede ser nullo";
            } else if ( visit.getId()==0 ) {
                message="El campo Id no puede ser 0";
            } else if ( visit.getUserId()!=null && !userserv.findId(visit.getUserId(), company) ) {
                message="No existe un registro con este UserId.";
            } else {
                //--- obtiene el registro con toda su info para luego editar ---
                currentVisit = dao.findById(visit.getId(), visit.getCompanyId());
                if (currentVisit!=null) {
                    //--- se reemplaza solo los campos obtenidos y que no vengan null desde el front
                    if (visit.getId()!=null) currentVisit.setId(visit.getId());
                    if (visit.getScheduledDate()!=null) currentVisit.setScheduledDate(visit.getScheduledDate());
                    if (visit.getCheckin()!=null) currentVisit.setCheckin(visit.getCheckin());
                    if (visit.getCheckout()!=null) currentVisit.setCheckout(visit.getCheckout());
                    if (visit.getUserId()!=null) currentVisit.setUserId(visit.getUserId());
                    if (visit.getCustomer()!=null) currentVisit.setCustomer(visit.getCustomer());
                    if (visit.getVisitType()!=null) currentVisit.setVisitType(visit.getVisitType());
                    if (visit.getComment()!=null) currentVisit.setComment(visit.getComment());
                    if (visit.getReason()!=null) currentVisit.setReason(visit.getReason());
                    if (visit.getFirm()!=null) currentVisit.setFirm(visit.getFirm());
                    if (visit.getReason_nullification()!=null) currentVisit.setReason_nullification(visit.getReason_nullification());

                    // Logica que define el estatus de la visita
                    if ( visit.getCheckin()==null && visit.getCheckout()==null) currentVisit.setStatus("pending");
                    else if ( visit.getCheckin()!=null && visit.getCheckout()==null) currentVisit.setStatus("checkin");
                    else if ( visit.getCheckin()!=null && visit.getCheckout()!=null) currentVisit.setStatus("checkout");
                    
                    //--- LastUpdate fecha de actualizacion del registro
                    Timestamp timestamp = new Timestamp(new Date().getTime());
                    currentVisit.setLastUpdate(timestamp);
                    //--- se ejecuta el update en la capa de datos ---
                    dao.update(currentVisit);
                    
                    if (currentVisit.getStatus().equalsIgnoreCase("checkout")) {
                        // se obtienes los datos necesarios para crear el pdf de la visita
                        User vendor = userserv.findById(currentVisit.getUserId());
                        //List<VisitPicture> pictures = pictureserv.getAllByVisit(currentVisit.getId());
                        // se crear el archivo pdf de la visita y se sube al repo S3
                        pdfserv.CreateVisitDocument(currentVisit, vendor);
                        // ejecuta un thread en 2do plano donde se envia el correo al Cliente.
                        SendEmailVisitThread se = new SendEmailVisitThread(vendor.getCompany().getEmail(), currentVisit);
                        se.start();
                        // ejecuta un thread en 2do plano donde se envia el correo al Administrador.
                        SendEmailVisitThread se2 = new SendEmailVisitThread(vendor.getCompany().getEmail(), currentVisit);
                        se2.start();
                    }
                } else {
                    message="No se encontro un registro asociado para este id";
                }
            }
        } catch ( Exception e ) {
            System.out.print("Error update: " + e.getMessage());
            backlog.save(new BackLog(VisitServiceImpl.class.getSimpleName(), 
                                    "update",
                                    e.getMessage()));
        }
        
        return message;
    }
    
    //--------------------- Send email service page --------------------------
    /*public String sendEmailServicePage(int id, int companyId) {
        Visit visit = null;
        Customer cstmr = null;
        String message="";
        try {
            // Se busca en la bd los datos del usuario por Id.
            visit = dao.findById(id, companyId);
            if ( visit==null ) {
                message="El Id de la visita no existe o es invalido";
            } else {
                cstmr = cstmrserv.findById(visit.getCustomer().getId(), companyId);
                if ( cstmr==null ) {
                    message="El id del cliente no existe o es invalido";
                } else {
                    // ejecuta un thread (hilo) en 2do plano donde se envia el correo.
                    SendEmailVisitThread se = new SendEmailVisitThread(cstmr, visit);
                    se.start();
                }
                
            }
        } catch ( Exception e ) {
            System.out.print("Error sendEmailServicePage: " + e.getMessage());
            backlog.save(new BackLog(VisitServiceImpl.class.getSimpleName(), 
                                    "sendEmailServicePage",
                                    e.getMessage()));
        }
        
        return message;
    }*/
    
    //----------------------------- DELETE USER ----------------------------------
    @Override
    public boolean delete(int id) {
        boolean result = false;
        try {
            int i = dao.delete(id);
            result = i==1;
        } catch ( Exception e ) {
            System.out.print("Error delete: " + e.getMessage());
            backlog.save(new BackLog(VisitServiceImpl.class.getSimpleName(), 
                                    "delete",
                                    e.getMessage()));
        }
        
        return result;
    }

    //--------------------- FIND BY ID OBJECT USER --------------------------
    @Override
    public Visit findById(int id, int companyId) {
        Visit result = null;
        try {
            // Se busca en la bd los datos del usuario por Id.
            result = dao.findById(id, companyId);
        } catch ( Exception e ) {
            System.out.print("Error findById: " + e.getMessage());
            backlog.save(new BackLog(VisitServiceImpl.class.getSimpleName(), 
                                    "findById",
                                    e.getMessage()));
        }
        
        return result;
    }
    
    //--------------------- FIND BY ID BOOLEAN --------------------------
    @Override
    public boolean findId(int id, int companyId) {
        // se consulta en la BD si el id del usuario existe y es valido
        return dao.findById(id, companyId)!=null;
    }
 
    
    //--------------------- GET ALL COMPANY LIST --------------------------
    @Override
    public List getAllByCompany(Integer companyId) {
        List<Visit> list = null;
        try {
            // Se consulta en la bd los usuarios registrados de una compañia.
            list = dao.getAllByCompany(companyId);
        } catch ( Exception e ) {
            System.out.print("Error getAllByCompany: " + e.getMessage());
            backlog.save(new BackLog(VisitServiceImpl.class.getSimpleName(), 
                                    "getAllByCompany",
                                    e.getMessage()));
        }
        return list;
    }

    @Override
    public List getAllByUser(Integer userId) {
        List<Visit> list = null;
        try {
            // Se consulta en la bd los usuarios registrados de una compañia.
            list = dao.getAllByUser(userId);
        } catch ( Exception e ) {
            System.out.print("Error getAllByUser: " + e.getMessage());
            backlog.save(new BackLog(VisitServiceImpl.class.getSimpleName(), 
                                    "getAllByUser",
                                    e.getMessage()));
        }
        return list;
    }

    @Override
    public List getAllByCustomer(Integer customerId) {
        List<Visit> list = null;
        try {
            // Se consulta en la bd los usuarios registrados de una compañia.
            list = dao.getAllByCustomer(customerId);
        } catch ( Exception e ) {
            System.out.print("Error getAllByCustomer: " + e.getMessage());
            backlog.save(new BackLog(VisitServiceImpl.class.getSimpleName(), 
                                    "getAllByCustomer",
                                    e.getMessage()));
        }
        return list;
    }
    
}
