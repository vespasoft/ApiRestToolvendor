/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;


import com.beecode.toolvendor.dao.CustomerDAO;
import com.beecode.toolvendor.interfaces.CustomerService;
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
    private final UserServiceImpl userserv = new UserServiceImpl();
    private final CityServiceImpl cityserv = new CityServiceImpl();
    
    //------------------------------- DAO ------------------------------------
    private final CustomerDAO dao = new CustomerDAO();

    public CustomerServiceImpl() {
    }
    
    //----------------------------- SAVE CUSTOMER --------------------------------
    @Override
    public String save(Customer cstmr) {
        Customer currentCustomer = null;
        String message="";
        try {
            if ( cstmr==null ) {
                message="Se espero un objeto user en formato JSON";
            } else if ( cstmr.getCompanyName()==null ) {
                message="El campo nombre no puede ser nullo";
            } else if ( cstmr.getEmail()==null ) {
                message="El campo email no puede ser nullo";
            } else if ( cstmr.getFirstName()==null ) {
                message="El campo FirstName no puede ser nullo";
            } else if ( cstmr.getLastName()==null ) {
                message="El campo LasttName no puede ser nullo";
            } else if ( cstmr.getEmail().length()==0 ) {
                message="El campo email no puede estar vacio";
            } else if ( cstmr.getCity().getId()==0 ) {
                message="El campo CityId no puede ser igual a 0";
            } else if ( cstmr.getUserId()==0 ) {
                message="El campo UserId no puede ser igual a 0";
            } else if ( findEmail(cstmr.getEmail()) ) {
                message="Ya existe un cliente con el mismo email";
            } else if ( !userserv.findId(cstmr.getUserId(), cstmr.getCompanyId()) ) {
                message="No existe ningun usuario con este Id.";
            } else if ( !cityserv.findId(cstmr.getCity().getId()) ) {
                message="No existe un registro con este cityId.";        
            } else {
                //--- AtCreated fecha de creación del registro
                cstmr.setCreatedAt(new Date() );
                if ( !dao.add(cstmr) ) {
                    message="El registro no se pudo guardar, ocurrio un error inesperado.";
                }
                
            }
        } catch ( Exception e ) {
            System.out.println("Error in customer save: " + e.getMessage());
        }
        return message;
    }

    //----------------------------- UPDATE CUSTOMER --------------------------------
    @Override
    public String update(Customer cstmr) {
        Customer currentCustomer = null;
        String message="";
        try {
            if ( cstmr==null ) {
                message="Se espero un objeto user en formato JSON";
            } else if ( cstmr.getId()==null ) {
                message="El campo Id no puede ser nullo";
            } else if ( cstmr.getId()==0 ) {
                message="El campo Id no puede ser 0";
            } else if ( cstmr.getUserId()!=null && !userserv.findId(cstmr.getUserId(), cstmr.getCompanyId()) ) {
                message="No existe un registro con este userId.";
            } else if ( cstmr.getCity()!=null && !cityserv.findId(cstmr.getCity().getId()) ) {
                message="No existe un registro con este cityId.";    
            } else {
                //--- obtiene el registro con toda su info para luego editar ---
                currentCustomer = findById(cstmr.getId(), cstmr.getCompanyId());
                if (currentCustomer!=null) {
                    //--- se reemplaza solo los campos obtenidos y que no vengan null desde el front
                    if (cstmr.getId()!=0) currentCustomer.setId(cstmr.getId());
                    if (cstmr.getFirstName()!=null) currentCustomer.setFirstName(cstmr.getFirstName());
                    if (cstmr.getLastName()!=null) currentCustomer.setLastName(cstmr.getLastName());
                    if (cstmr.getEmail()!=null) currentCustomer.setEmail(cstmr.getEmail());
                    if (cstmr.getActive()!=null) currentCustomer.setActive(cstmr.getActive());
                    if (cstmr.getLatitud()!=null) currentCustomer.setLatitud(cstmr.getLatitud());
                    if (cstmr.getLongitude()!=null) currentCustomer.setLongitude(cstmr.getLongitude());
                    if (cstmr.getCity()!=null) currentCustomer.setCity(cstmr.getCity());
                    if (cstmr.getUserId()!=null) currentCustomer.setUserId(cstmr.getUserId());
                    if (cstmr.getBuilding()!=null) currentCustomer.setBuilding(cstmr.getBuilding());
                    if (cstmr.getStreet()!=null) currentCustomer.setStreet(cstmr.getStreet());
                    if (cstmr.getPostalCode()!=null) currentCustomer.setPostalCode(cstmr.getPostalCode());
                    if (cstmr.getReference()!=null) currentCustomer.setReference(cstmr.getReference());
                    //--- LastUpdate fecha de actualizacion del registro
                    currentCustomer.setLastUpdate(new Date());
                    //--- se ejecuta el update en la capa de datos ---
                    dao.update(currentCustomer);
                    if ( !dao.update(cstmr) )
                        message="El registro no se pudo actualizar, ocurrio un error inesperado.";
                } else {
                    message="No se encontro un registro asociado para este id";
                }

            }
        } catch ( Exception e ) {
            System.out.println("Error in customer update: " + e.getMessage());
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
            System.out.println("Error in customer delete: " + e.getMessage());
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
    public boolean findEmail(String email) {
        // se consulta en la BD si el email del usuario existe y es valido
        return dao.findByEmail(email)!=null;
    }
    
    //--------------------- FIND BY ID OBJECT CUSTOMER --------------------------
    @Override
    public Customer findById(int id, int companyId) {
        Customer result = null;
        try {
            // Se busca en la bd los datos del customer por Id.
            result = dao.findById(id, companyId);
        } catch ( Exception e ) {
            System.out.println("Error in customer findById: " + e.getMessage());
        }
        return result;
    }
    
    //--------------------- FIND BY EMAIL OBJECT CUSTOMER --------------------------
    @Override
    public Customer findByEmail(String email) {
        Customer result = null;
        try {
            // Se busca en la bd los datos del customer por Email.
            result = dao.findByEmail(email);
        } catch ( Exception e ) {
            System.out.println("Error in customer findByEmail: " + e.getMessage());
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
            System.out.println("Error in customer getAllByCompany: " + e.getMessage());
        }
        return list;
    }

    //--------------------- GET ALL CUSTOMER BY USER --------------------------
    @Override
    public List getAllByUser(Integer userId) {
        List<User> list = null;
        try {
            // Se consulta en la bd los customer registrados para un usuario.
            list = dao.getAllByUser(userId);
        } catch ( Exception e ) {
            System.out.println("Error in customer getAllByUser: " + e.getMessage());
        }
        return list;
    }
    
}
