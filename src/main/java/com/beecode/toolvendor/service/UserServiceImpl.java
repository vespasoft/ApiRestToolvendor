/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;

import com.beecode.toolvendor.dao.UserDAO;
import com.beecode.toolvendor.interfaces.UserService;
import com.beecode.toolvendor.model.User;
import com.beecode.toolvendor.thread.SendEmailForgotThread;
import com.beecode.toolvendor.thread.SendEmailWellcomeThread;
import static com.beecode.toolvendor.util.AppPreferences.TOKEN_PASSWORD_LENGTH;
import com.beecode.toolvendor.util.StringUtil;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author luisvespa
 */
public class UserServiceImpl implements UserService {
    //----------------------------- OBJECTS ----------------------------------
    private static final AtomicLong counter = new AtomicLong();
    
    //----------------------------- DAO --------------------------------------
    private UserDAO dao;
    
    //----------------------------- SERVICES ---------------------------------
    private CityServiceImpl cityserv;
    private CompanyServiceImpl companyserv;
    private UserTypeServiceImpl usertypeserv;
    

    public UserServiceImpl() {
        dao = new UserDAO();
        cityserv = new CityServiceImpl();
        companyserv = new CompanyServiceImpl();
        usertypeserv = new UserTypeServiceImpl();
    }
    
    //--------------------------- SAVE USER -----------------------------------
    @Override
    public String save(User user) {
        User currentUser = null;
        String message="";
        try {
            // se cablea la compañia siempre sera companyId = 1
            // user.setCompanyId(companyserv.findById(1).getId()); 
            if ( user==null ) {
                message="Se espero un objeto user en formato JSON";
            } else if ( user.getName()==null ) {
                message="El campo nombre no puede estar vacio";
            } else if ( user.getEmail()==null ) {
                message="El campo email no puede estar vacio";
            } else if ( user.getPhone()==null ) {
                message="El campo phone no puede estar vacio";
            } else if ( user.getUsertype()==null ) {
                message="El campo userTypeId no puede estar vacio";
            } else if ( user.getName().length()==0 ) {
                message="El campo nombre no puede estar vacio";
            } else if ( user.getEmail().length()==0 ) {
                message="El campo email no puede estar vacio";
            } else if ( user.getPhone().length()==0 ) {
                message="El campo phone no puede estar vacio";
            } else if ( findEmail(user.getEmail()) ) {
                message="Ya existe un usuario con el mismo email";
            } else if ( !usertypeserv.findId(user.getUsertype().getId(), user.getCompanyId()) ) {
                message="No existe ningun tipo de usuario con este Id.";    
            } else if ( !companyserv.findId(user.getCompanyId()) ) {
                message="No existe ninguna compañia con este Id.";    
            } else if ( !cityserv.findId(user.getCity().getId()) ) {
                message="No existe ninguna ciudad con este Id.";
            } else {
                //--- AtCreated fecha de creación del registro
                user.setCreatedAt(new Date());
                user.setPassword(StringUtil.generateTokenString(TOKEN_PASSWORD_LENGTH));
                user.setPhoto("");
                user.setEnabled(Boolean.TRUE);
                user.setCountry(user.getCity().getCountry());
                // si se agrega satisfactoriamente el usuario
                dao.add(user);
                // se instancia la clase controladora de correos
                EmailServiceImpl emailserv = new EmailServiceImpl();
                // se envia el correo de bienvenida al usuario
                emailserv.SendEmailWellcome(user);
                // ejecuta un thread (hilo) en 2do plano donde se envia el correo.
                // SendEmailWellcomeThread se = new SendEmailWellcomeThread(currentUser);
                // se.start();
            }
        } catch ( Exception e ) {
            System.out.println("Error in user save: " + e.getMessage());
        }
        
        return message;
    }

    //----------------------------- UPDATE USER --------------------------------
    @Override
    public String update(User user) {
        User currentUser = null;
        String message="";
        try {
            // se cablea la compañia siempre sera companyId = 1
            // user.setCompanyId(companyserv.findById(1).getId()); 
            if ( user==null ) {
                message="Se espero un objeto user en formato JSON";
            } else if ( user.getId()==0 ) {
                message="El campo UserId no puede estar vacio";
            } else {
                //--- obtiene el registro con toda su info para luego editar ---
                currentUser = dao.findById(user.getId(), user.getCompanyId());
                if (currentUser!=null) {
                    //--- se reemplaza solo los campos obtenidos y que no vengan null desde el front
                    if (user.getId()!=null) currentUser.setId(user.getId());
                    if (user.getName()!=null) currentUser.setName(user.getName());
                    if (user.getPassword()!=null) currentUser.setPassword(user.getPassword());
                    if (user.getPhone()!=null) currentUser.setPhone(user.getPhone());
                    if (user.getEnabled()!=null) currentUser.setEnabled(user.getEnabled());
                    if (user.getUsertype()!=null) currentUser.setUsertype(user.getUsertype());
                    if (user.getCity()!=null) currentUser.setCity(user.getCity());
                    if (user.getCompanyId()!=null) currentUser.setCompanyId(user.getCompanyId());
                    if (user.getLatitud()!=null) currentUser.setLatitud(user.getLatitud());
                    if (user.getLongitude()!=null) currentUser.setLongitude(user.getLongitude());
                    if (user.getPhoto()!=null) currentUser.setPhoto(user.getPhoto());
                    //--- LastUpdate fecha de actualizacion del registro
                    user.setLastUpdate(new Date());
                    //--- se ejecuta el update en la capa de datos ---
                    dao.update(currentUser);
                } else {
                    message="No se encontro un registro asociado para este id";
                }

            }
        } catch ( Exception e ) {
            System.out.println("Error in user update: " + e.getMessage());
        }
        
        return message;
    }
    
    //----------------------------- FORGOT ----------------------------------
    public String forgot(User user) {
        String message="";
        if ( user==null ) {
            message="An object user with email is required.";
        } else if ( user.getEmail()==null ) {
            message="Email is required.";
        } else {
            user = findByEmail(user.getEmail());
            if( ( user==null) ) {
                message="The email is not valid or not exits.";
            } else  {
                user.setPassword(StringUtil.generateTokenString(TOKEN_PASSWORD_LENGTH));
                //--- se ejecuta el update en la capa de datos ---
                dao.update(user);
                // ejecuta un thread (hilo) en 2do plano donde se envia el correo.
                SendEmailForgotThread se = new SendEmailForgotThread(user);
                se.start();
                //EmailServiceImpl instance = new EmailServiceImpl();
                //instance.SendEmailForgot(user);
            }
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
            System.out.println("Error in user delete: " + e.getMessage());
        }
        
        return result;
    }

    //--------------------- FIND BY ID OBJECT USER --------------------------
    @Override
    public User findById(int id) {
        User user = null;
        try {
            // Se busca en la bd los datos del usuario por Id.
            user = dao.findById(id);
        } catch ( Exception e ) {
            System.out.println("Error in user findById: " + e.getMessage());
        }
        
        return user;
    }
    
    //--------------------- FIND BY ID AND COMPANY OBJECT USER --------------------------
    @Override
    public User findById(int id, int companyId) {
        User user = null;
        try {
            // Se busca en la bd los datos del usuario por Id.
            user = dao.findById(id, companyId);
        } catch ( Exception e ) {
            System.out.println("Error in user findById: " + e.getMessage());
        }
        
        return user;
    }
    
    //--------------------- FIND BY EMAIL OBJECT USER --------------------------
    @Override
    public User findByEmail(String email) {
        User user = null;
        try {
            // Se busca en la bd los datos del usuario por Email.
            user = dao.findByEmail(email);
        } catch ( Exception e ) {
            System.out.println("Error in user findByEmail: " + e.getMessage());
        }
        
        return user;
    }

    //--------------------- FIND BY AUTHENTICATION OBJECT USER -----------------
    @Override
    public User findByAuth(String email, String password) {
        User user = null;
        try {
            // Este método consulta los datos del usuario verificando coincidencias de email y password
            user = dao.authentication(email, password);
        } catch ( Exception e ) {
            System.out.println("Error in user findByAuth: " + e.getMessage());
        }
        return user;
    }
    
    //--------------------- FIND BY ID BOOLEAN ---------------------------------
    @Override
    public boolean findId(int id) {
        // se consulta en la BD si el id del usuario existe y es valido
        return dao.findById(id)!=null;
    }
    
    //--------------------- FIND BY ID BOOLEAN ---------------------------------
    @Override
    public boolean findId(int id, int companyId) {
        // se consulta en la BD si el id del usuario existe y es valido
        return dao.findById(id, companyId)!=null;
    }

    //--------------------- FIND BY EMAIL BOOLEAN ------------------------------
    @Override
    public boolean findEmail(String email) {
        // se consulta en la BD si el email del usuario existe y es valido
        return dao.findByEmail(email)!=null;
    }

    //--------------------- GET ALL COMPANY LIST --------------------------
    @Override
    public List getAllByCompany(Integer companyId) {
        List<User> list = null;
        try {
            // Se consulta en la bd los usuarios registrados de una compañia.
            list = dao.getAllByCompanyV2(companyId);
            
        } catch ( Exception e ) {
            System.out.println("Error in user getAllByCompany: " + e.getMessage());
        }
        return list;
    }
    
}
