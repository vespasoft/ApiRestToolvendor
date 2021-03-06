/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;


import com.beecode.toolvendor.dao.CustomerDAO;
import com.beecode.toolvendor.interfaces.CustomerService;
import com.beecode.toolvendor.model.BackLog;
import com.beecode.toolvendor.model.Company;
import com.beecode.toolvendor.model.Customer;
import com.beecode.toolvendor.model.User;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author luisvespa
 */
public class CustomerServiceImpl implements CustomerService {
    //----------------------------- OBJECTS ----------------------------------
    private static final AtomicLong counter = new AtomicLong();
    
    //----------------------------- SERVICES ---------------------------------
    private UserServiceImpl userserv = new UserServiceImpl();
    private CityServiceImpl cityserv = new CityServiceImpl();
    private BackLogServiceImpl backlog;
    
    //------------------------------- DAO ------------------------------------
    private CustomerDAO dao;

    public CustomerServiceImpl() {
        dao = new CustomerDAO();
        backlog = new BackLogServiceImpl();
    }
    
    //----------------------------- SAVE CUSTOMER --------------------------------
    @Override
    public String save(Customer cstmr, Company company) {
        Customer currentCustomer = null;
        String message="";
        try {
            if ( cstmr==null ) {
                message="Se espero un objeto user en formato JSON";
            } else if ( cstmr.getCompanyName()==null ) {
                message="El campo nombre no puede ser nullo";
            } else if ( cstmr.getContactEmail()==null ) {
                message="El campo contactEmail no puede ser nullo";
            } else if ( cstmr.getContactName()==null ) {
                message="El campo contactName no puede ser nullo";
            } else if ( cstmr.getContactPhone()==null ) {
                message="El campo contactPhone no puede ser nullo";
            } else if ( cstmr.getContactEmail().length()==0 ) {
                message="El campo contactEmail no puede estar vacio";
            } else if ( cstmr.getCity().getId()==0 ) {
                message="El campo CityId no puede ser igual a 0";
            } else if ( cstmr.getUser().getId()==0 ) {
                message="El campo UserId no puede ser igual a 0";
            } else if ( findName(cstmr.getCompanyName(), company.getId()) ) {
                message="Ya existe un cliente con el mismo nombre";
            } else if ( findEmail(cstmr.getContactEmail(), company.getId()) ) {
                message="Ya existe un cliente con el mismo email";
            } else if ( !userserv.findId(cstmr.getUser().getId(), company) ) {
                message="No existe ningun usuario con este Id.";
            } else if ( !cityserv.findId(cstmr.getCity().getId()) ) {
                message="No existe una ciudad con este cityId.";        
            } else {
                //--- AtCreated fecha de creación del registro
                cstmr.setCreatedAt(new Date() );
                cstmr.setCountry(cstmr.getCity().getCountry());
                dao.add(cstmr);
                
            }
        } catch ( Exception e ) {
            backlog.save(new BackLog(CustomerServiceImpl.class.getSimpleName(), 
                                    "save",
                                    e.getMessage()));
        }
        return message;
    }

    //----------------------------- UPDATE CUSTOMER --------------------------------
    @Override
    public String update(Customer cstmr, Company company) {
        Customer currentCustomer = null;
        String message="";
        try {
            if ( cstmr==null ) {
                message="Se espero un objeto user en formato JSON";
            } else if ( cstmr.getId()==null ) {
                message="El campo Id no puede ser nullo";
            } else if ( cstmr.getId()==0 ) {
                message="El campo Id no puede ser 0";
            } else if ( cstmr.getUser().getId()!=null && !userserv.findId(cstmr.getUser().getId(), company) ) {
                message="No existe un registro con este userId.";
            } else if ( cstmr.getCity()!=null && !cityserv.findId(cstmr.getCity().getId()) ) {
                message="No existe un registro con este cityId.";    
            } else {
                //--- obtiene el registro con toda su info para luego editar ---
                currentCustomer = findById(cstmr.getId(), cstmr.getCompanyId());
                if (currentCustomer!=null) {
                    //--- se reemplaza solo los campos obtenidos y que no vengan null desde el front
                    if (cstmr.getId()!=0) currentCustomer.setId(cstmr.getId());
                    if (cstmr.getCompanyName()!=null) currentCustomer.setCompanyName(cstmr.getCompanyName());
                    if (cstmr.getContactName()!=null) currentCustomer.setContactName(cstmr.getContactName());
                    if (cstmr.getContactPhone()!=null) currentCustomer.setContactPhone(cstmr.getContactPhone());
                    if (cstmr.getContactEmail()!=null) currentCustomer.setContactEmail(cstmr.getContactEmail());
                    if (cstmr.getActive()!=null) currentCustomer.setActive(cstmr.getActive());
                    if (cstmr.getLatitud()!=null) currentCustomer.setLatitud(cstmr.getLatitud());
                    if (cstmr.getLongitude()!=null) currentCustomer.setLongitude(cstmr.getLongitude());
                    if (cstmr.getCity()!=null) currentCustomer.setCity(cstmr.getCity());
                    if (cstmr.getUser().getId()!=null) currentCustomer.setUser(cstmr.getUser());
                    if (cstmr.getBuilding()!=null) currentCustomer.setBuilding(cstmr.getBuilding());
                    if (cstmr.getStreet()!=null) currentCustomer.setStreet(cstmr.getStreet());
                    if (cstmr.getPostalCode()!=null) currentCustomer.setPostalCode(cstmr.getPostalCode());
                    if (cstmr.getReference()!=null) currentCustomer.setReference(cstmr.getReference());
                    //--- LastUpdate fecha de actualizacion del registro
                    currentCustomer.setLastUpdate(new Date());
                    //--- se ejecuta el update en la capa de datos ---
                    dao.update(currentCustomer);
                } else {
                    message="No se encontro un registro asociado para este id";
                }

            }
        } catch ( Exception e ) {
            backlog.save(new BackLog(CustomerServiceImpl.class.getSimpleName(), 
                                    "update",
                                    e.getMessage()));
        }
        return message;
    }

    //----------------------------- DELETE USER ----------------------------------
    @Override
    public boolean delete(int id) {
        boolean result = false;
        try {
            int i = dao.delete(id);
            result = i==1;
            
        } catch ( Exception e ) {
            backlog.save(new BackLog(CustomerServiceImpl.class.getSimpleName(), 
                                    "delete",
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
    
    //--------------------- FIND BY EMAIL BOOLEAN --------------------------
    @Override
    public boolean findEmail(String email, int companyId) {
        // se consulta en la BD si el email del usuario existe y es valido
        return dao.findByEmail(email, companyId)!=null;
    }
    
    //--------------------- FIND BY NAME BOOLEAN --------------------------
    @Override
    public boolean findName(String name, int companyId) {
        // se consulta en la BD si el email del usuario existe y es valido
        return dao.findByCompanyName(name, companyId)!=null;
    }
    
    
    //--------------------- FIND BY ID OBJECT CUSTOMER --------------------------
    @Override
    public Customer findById(int id, int companyId) {
        Customer result = null;
        try {
            // Se busca en la bd los datos del customer por Id.
            result = dao.findById(id, companyId);
        } catch ( Exception e ) {
            backlog.save(new BackLog(CustomerServiceImpl.class.getSimpleName(), 
                                    "findById",
                                    e.getMessage()));
        }
        return result;
    }
    
    //--------------------- FIND BY EMAIL OBJECT CUSTOMER --------------------------
    @Override
    public Customer findByEmail(String email, int companyId) {
        Customer result = null;
        try {
            // Se busca en la bd los datos del customer por Email.
            result = dao.findByEmail(email, companyId);
        } catch ( Exception e ) {
            backlog.save(new BackLog(CustomerServiceImpl.class.getSimpleName(), 
                                    "findByEmail",
                                    e.getMessage()));
        }
        return result;
    }

    //--------------------- GET ALL CUSTOMER BY COMPANY --------------------------
    @Override
    public List getAllByCompany(Integer companyId) {
        List<User> list = null;
        try {
            // Se consulta en la bd los customer registrados para un usuario.
            list = dao.getAllByCompany(companyId);
        } catch ( Exception e ) {
            backlog.save(new BackLog(CustomerServiceImpl.class.getSimpleName(), 
                                    "getAllByCompany",
                                    e.getMessage()));
        }
        return list;
    }

    //--------------------- GET ALL CUSTOMER BY USER --------------------------
    @Override
    public List getAllByUser(User user) {
        List<User> list = null;
        try {
            // Se consulta en la bd los customer registrados para un usuario.
            list = dao.getAllByUser(user);
        } catch ( Exception e ) {
            backlog.save(new BackLog(CustomerServiceImpl.class.getSimpleName(), 
                                    "getAllByUser",
                                    e.getMessage()));
        }
        return list;
    }
    
}
