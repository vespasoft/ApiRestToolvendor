/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;


import com.beecode.toolvendor.dao.VisitDAO;
import com.beecode.toolvendor.interfaces.VisitService;
import com.beecode.toolvendor.model.Customer;
import com.beecode.toolvendor.model.User;
import com.beecode.toolvendor.model.Visit;
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
    private VisitDAO dao = new VisitDAO();
    //----------------------------- SERVICES -------------------------------------
    private UserServiceImpl userserv;
    private CustomerServiceImpl cstmrserv;
    private VisitTypeServiceImpl visittypeserv;
    
    public VisitServiceImpl() {
    }
    
    //----------------------- Agregar nuevo registro ---------------------------------
    @Override
    public String save(Visit visit) {
        dao = new VisitDAO();
        userserv = new UserServiceImpl();
        cstmrserv = new CustomerServiceImpl();
        visittypeserv = new VisitTypeServiceImpl();
        Visit currentVisit = null;
        String message="";
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
        } else if ( !userserv.findId(visit.getUserId(), visit.getCompanyId()) ) {
            message="No existe un registro con este UserId.";
        } else if ( !cstmrserv.findId(visit.getCustomer().getId(), visit.getCompanyId()) ) {
            message="No existe un registro con este CustomerId.";   
        } else if ( !visittypeserv.findId(visit.getVisitType().getId(), visit.getCompanyId()) ) {
            message="No existe un registro con este VisitTypeId.";
        } else {
            //--- Created At fecha de creación del registro
            Timestamp timestamp = new Timestamp(new Date().getTime());
            visit.setCreatedAt( timestamp );
            
            dao.add(visit);
            User user = userserv.findById(visit.getUserId(), visit.getCompanyId());
            // ejecuta un thread (hilo) en 2do plano donde se envia el correo.
            SendEmailScheduleVisitThread se = new SendEmailScheduleVisitThread(user, visit);
            se.start();
        }
        
        return message;
    }
    
    //----------------------- Actualizar los datos de un registro existente --------------------------
    @Override
    public String update(Visit visit) {
        dao = new VisitDAO();
        userserv = new UserServiceImpl();
        cstmrserv = new CustomerServiceImpl();
        visittypeserv = new VisitTypeServiceImpl();
        Visit currentVisit = null;
        String message="";
        if ( visit==null ) {
            message="Se espero un objeto visit en formato JSON";
        } else if ( visit.getId()==null ) {
            message="El campo Id no puede ser nullo";
        } else if ( visit.getId()==0 ) {
            message="El campo Id no puede ser 0";
        } else if ( visit.getUserId()!=null && !userserv.findId(visit.getUserId(), visit.getCompanyId()) ) {
            message="No existe un registro con este UserId.";
        } else if ( visit.getCustomer()!=null && !cstmrserv.findId(visit.getCustomer().getId(), visit.getCompanyId()) ) {
            message="No existe un registro con este CustomerId.";
        } else if ( visit.getVisitType()!=null && !visittypeserv.findId(visit.getVisitType().getId(), visit.getCompanyId()) ) {
            message="No existe un registro con este visitucttypeId.";
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
                if (visit.getCustomer().getId()!=null) currentVisit.setCustomer(visit.getCustomer());
                if (visit.getVisitType()!=null) currentVisit.setVisitType(visit.getVisitType());
                if (visit.getComment()!=null) currentVisit.setComment(visit.getComment());
                if (visit.getReason()!=null) currentVisit.setReason(visit.getReason());
                if (visit.getFirm()!=null) currentVisit.setFirm(visit.getFirm());
                //if (visit.getVisitPictures()!=null) currentVisit.setVisitPictures(visit.getVisitPictures());
                //--- LastUpdate fecha de actualizacion del registro
                Timestamp timestamp = new Timestamp(new Date().getTime());
                currentVisit.setLastUpdate(timestamp);
                //--- se ejecuta el update en la capa de datos ---
                dao.update(currentVisit);
            } else {
                message="No se encontro un registro asociado para este id";
            }
        }
        
        return message;
    }
    
    //--------------------- Send email service page --------------------------
    public String sendEmailServicePage(int id, int companyId) {
        dao = new VisitDAO();
        userserv = new UserServiceImpl();
        cstmrserv = new CustomerServiceImpl();
        visittypeserv = new VisitTypeServiceImpl();
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
            System.out.println("Error in visit sendEmailServicePage: " + e.getMessage());
        }
        
        return message;
    }
    
    //----------------------------- DELETE USER ----------------------------------
    @Override
    public boolean delete(int id) {
        dao = new VisitDAO();
        boolean result = false;
        try {
            int i = dao.delete(id);
            result = i==1;
        } catch ( Exception e ) {
            System.out.println("Error in visit delete: " + e.getMessage());
        }
        
        return result;
    }

    //--------------------- FIND BY ID OBJECT USER --------------------------
    @Override
    public Visit findById(int id, int companyId) {
        dao = new VisitDAO();
        Visit result = null;
        try {
            // Se busca en la bd los datos del usuario por Id.
            result = dao.findById(id, companyId);
        } catch ( Exception e ) {
            System.out.println("Error in visit findById: " + e.getMessage());
        }
        
        return result;
    }
    
    //--------------------- FIND BY ID BOOLEAN --------------------------
    @Override
    public boolean findId(int id, int companyId) {
        dao = new VisitDAO();
        // se consulta en la BD si el id del usuario existe y es valido
        return dao.findById(id, companyId)!=null;
    }
 
    
    //--------------------- GET ALL COMPANY LIST --------------------------
    @Override
    public List getAllByCompany(Integer companyId) {
        dao = new VisitDAO();
        List<Visit> list = null;
        try {
            // Se consulta en la bd los usuarios registrados de una compañia.
            list = dao.getAllByCompany(companyId);
        } catch ( Exception e ) {
            System.out.println("Error in visit getAllByCompany: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List getAllByUser(Integer userId) {
        dao = new VisitDAO();
        List<Visit> list = null;
        try {
            // Se consulta en la bd los usuarios registrados de una compañia.
            list = dao.getAllByUser(userId);
        } catch ( Exception e ) {
            System.out.println("Error in visit getAllByUser: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List getAllByCustomer(Integer customerId) {
        dao = new VisitDAO();
        List<Visit> list = null;
        try {
            // Se consulta en la bd los usuarios registrados de una compañia.
            list = dao.getAllByCustomer(customerId);
        } catch ( Exception e ) {
            System.out.println("Error in visit getAllByCustomer: " + e.getMessage());
        }
        return list;
    }
    
}
