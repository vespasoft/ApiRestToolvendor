/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;

import com.beecode.toolvendor.dao.CompanyDAO;
import com.beecode.toolvendor.interfaces.CompanyService;
import com.beecode.toolvendor.model.Brand;
import com.beecode.toolvendor.model.Category;
import com.beecode.toolvendor.model.Cellar;
import com.beecode.toolvendor.model.Company;
import com.beecode.toolvendor.model.Family;
import com.beecode.toolvendor.model.OrderType;
import com.beecode.toolvendor.model.Presentation;
import com.beecode.toolvendor.model.ProductType;
import com.beecode.toolvendor.model.User;
import com.beecode.toolvendor.model.UserType;
import com.beecode.toolvendor.model.VisitType;
import com.beecode.toolvendor.thread.ConfigureCompanyDefaultThread;
import com.beecode.toolvendor.thread.SendEmailWellcomeThread;
import com.beecode.toolvendor.util.AppPreferences;
import com.beecode.toolvendor.util.StringUtil;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author luisvespa
 */
public class CompanyServiceImpl extends AppPreferences implements CompanyService {
    //----------------------------- OBJECTS ---------------------------------
    private static final AtomicLong counter = new AtomicLong();
    
    //----------------------------- DAO ---------------------------------
    private CompanyDAO dao;
    
    //----------------------------- SERVICES ---------------------------------
    

    public CompanyServiceImpl() {
    }
    
    //----------------------- SAVE COMPANY ---------------------------------
    @Override
    public String save(Company company) {
        dao = new CompanyDAO();
        UserTypeServiceImpl usertypeserv = new UserTypeServiceImpl();
        CountryServiceImpl countryserv = new CountryServiceImpl();
        CityServiceImpl cityserv = new CityServiceImpl();
        UserServiceImpl userserv = new UserServiceImpl();
    
        Date createAt = new Date();
        Company currentCompany = null;
        String message="";
        try {
            if ( company==null ) {
                message="Se espero un objeto company en formato JSON";
            } else if ( company.getCompany()==null ) {
                message="El campo company no puede estar vacio";
            } else if ( company.getEmail()==null ) {
                message="El campo email no puede estar vacio";
            } else if ( company.getContactName()==null ) {
                message="El campo contactName no puede estar vacio";
            } else if ( company.getPhone()==null ) {
                message="El campo phone no puede estar vacio";
            } else if ( company.getCountry()==null ) {
                message="El campo countryId no puede estar vacio";
            } else if ( company.getAddress().length()==0 ) {
                message="El campo address no puede estar vacio";
            } else if ( company.getPhone().length()==0 ) {
                message="El campo phone no puede estar vacio";
            } else if ( findCompanyName(company.getCompany()) ) {
                message="Ya existe una empresa con el mismo nombre";
            } else if ( userserv.findEmail(company.getEmail()) ) {
                message="Ya existe una empresa o usuario utilizando el mismo email";
            } else if ( !countryserv.findId(company.getCountry().getId()) ) {
                message="No existe ningun pais con este Id.";
            } else if ( !cityserv.findId(company.getCity().getId()) ) {
                message="No existe ninguna ciudad con este Id.";    
            } else {
                //--- AtCreated fecha de creación del registro
                company.setCreatedAt( createAt );
                dao.add(company);
                // se actualiza la data mapeada contra la de la BD
                dao = new CompanyDAO();
                //--- obtiene el usuario registrado con toda su info para la respuesta ---
                currentCompany = dao.findByEmail(company.getEmail());
                if ( currentCompany==null) {
                    message="No se pudo crear la empresa, ocurrio un error inesperado.";
                } else {
                    // ejecuta un thread (hilo) en 2do plano donde se envia el correo.
                    ConfigureCompanyDefaultThread cc = new ConfigureCompanyDefaultThread(currentCompany);
                    cc.start();
                    // si se guarda correctamente la empresa se procede a crear el tipo de usuario y el super usuario
                    UserType obj = new UserType("SUPER ADMIN", "A", currentCompany.getId());
                    usertypeserv.save(obj);
                    // se obtiene el tipo de usuario creado a la nueva empresa para agregar un usuario SUPER ADMIN
                    UserType currentUsertype = usertypeserv.findByName("SUPER ADMIN", currentCompany.getId());
                    if (currentUsertype!=null) {
                        // se crea el objeto para el usuario administrador de la compañia.
                        User user = new User();
                        user.setName(currentCompany.getContactName());
                        user.setEmail(currentCompany.getEmail());
                        user.setPhone(currentCompany.getPhone());
                        user.setPassword(StringUtil.generateTokenString(TOKEN_PASSWORD_LENGTH));
                        user.setPhoto("user.png");
                        user.setCreatedAt( createAt );
                        user.setEnabled(Boolean.TRUE);
                        user.setUsertype(currentUsertype);
                        user.setCompanyId(currentCompany.getId());
                        user.setCountry(currentCompany.getCountry());
                        user.setCity(currentCompany.getCity());
                        // se crea el usuario administrador en la base de datos
                        message = userserv.save(user);
                        // si se crea el usuario satisfactoriamente se debe enviar un correo de bienvenida
                        if ( message.isEmpty() ) {
                            // ejecuta un thread (hilo) en 2do plano donde se envia el correo.
                            SendEmailWellcomeThread se = new SendEmailWellcomeThread(user);
                            se.start();
                        }
                    } else {
                        message="No se pudo crear el tipo de usuario de la empresa, ocurrio un error inesperado.";
                    }
                }
            }
        } catch ( Exception e ) {
            System.out.println("Error in company save: " + e.getMessage());
        }
        return message;
    }
 
    //--------------------- UPDATE COMPANY --------------------------
    @Override
    public String update(Company company) {
        dao = new CompanyDAO();
        Company currentCompany = null;
        String message="";
        try {
            if ( company==null ) {
                message="Se espero un objeto company en formato JSON";
            } else if ( company.getId()==null ) {
                message="El campo CompanyId no puede estar vacio";
            } else if ( company.getId()==0 ) {
                message="El campo CompanyId no puede estar vacio";
            } else {
                //--- obtiene el registro con toda su info para luego editar ---
                currentCompany = dao.findById(company.getId());
                if (currentCompany!=null) {
                    //--- se reemplaza solo los campos obtenidos y que no vengan null desde el front
                    if (company.getId()!=null) currentCompany.setId(company.getId());
                    if (company.getCompany()!=null) currentCompany.setCompany(company.getCompany());
                    if (company.getContactName()!=null) currentCompany.setContactName(company.getContactName());
                    if (company.getPhone()!=null) currentCompany.setPhone(company.getPhone());
                    if (company.getAddress()!=null) currentCompany.setAddress(company.getAddress());
                    if (company.getBuilding()!=null) currentCompany.setBuilding(company.getBuilding());
                    if (company.getCountry()!=null) currentCompany.setCountry(company.getCountry());
                    currentCompany.setActive(company.getActive());
                    if (company.getPostalCode()!=null) currentCompany.setPostalCode(company.getPostalCode());
                    //--- LastUpdate fecha de actualizacion del registro
                    company.setLastUpdate(new Date());
                    //--- se ejecuta el update en la capa de datos ---
                    dao.update(currentCompany);
                } else {
                    message="No se encontro un registro asociado para este id";
                }

            }
        } catch ( Exception e ) {
            System.out.println("Error in company update: " + e.getMessage());
        }
        
        return message;
    }
 
    //--------------------- DELETE COMPANY --------------------------
    @Override
    public boolean delete(int id) {
        dao = new CompanyDAO();
        boolean result = false;
        try {
            Company company = dao.findById(id);
            if (company != null) {
                int i = dao.delete(id);
                result = i==1;
            }
        } catch ( Exception e ) {
            System.out.println("Error in company delete: " + e.getMessage());
        }
        
        return result;
    }
    
    //--------------------- FIND BY ID OBJECT COMPANY --------------------------
    @Override
    public Company findById(int id){
        dao = new CompanyDAO();
        Company result = null;
        try {
            // Se consulta en la bd los datos de la company por Id.
            result = dao.findById(id);
        } catch ( Exception e ) {
            System.out.println("Error in company findById: " + e.getMessage());
        }
        
        
        return result;
    }
    
    //--------------------- FIND BY ID OBJECT COMPANY --------------------------
    public Company findByEmail(String email){
        dao = new CompanyDAO();
        Company result = null;
        try {
            // Se consulta en la bd los datos de la company por Email.
            result = dao.findByEmail(email);
        } catch ( Exception e ) {
            System.out.println("Error in company findByEmail: " + e.getMessage());
        }
        
        
        return result;
    }

    //--------------------- FIND BY ID BOOLEAN --------------------------
    @Override
    public boolean findId(int id) {
        // se consulta en la BD si el id de la company existe y es valido
        dao = new CompanyDAO();
        return dao.findById(id)!=null;
    }

    //--------------------- FIND BY COMPANYNAME BOOLEAN --------------------------
    @Override
    public boolean findCompanyName(String name) {
        // se consulta en la BD if the name of company existe y es valido
        dao = new CompanyDAO();
        return dao.findByStringField("company", name)!=null;
    }

    //--------------------- FIND BY EMAIL BOOLEAN --------------------------
    @Override
    public boolean findEmail(String email) {
        // se consulta en la BD if the email of company existe y es valido
        dao = new CompanyDAO();
        return dao.findByEmail(email)!=null;
    }

    //--------------------- GET ALL COMPANY --------------------------
    @Override
    public List getAll() {
        dao = new CompanyDAO();
        List<Company> list = null;
        try {
            // Se consulta en la bd todas las compañias registradas.
            list = dao.getAll();
        } catch ( Exception e ) {
            System.out.println("Error in company getAll: " + e.getMessage());
        }
        return list;
    }
    
    /*
    Method: configureCompanyDefault
    Param: Integer companyId
    Description: En este metodo se agregan los registros por default que tendran las tablas libres de una compañia
    cuando resien crea una cuenta como por ejemplo, brand, visittype, groups, category, family, producttype, etc...
    */
    public void configureCompanyDefault(Integer companyId) {
        // agregar marca default
        BrandServiceImpl brandserv = new BrandServiceImpl();
        Brand brand = new Brand(companyId, "Ninguna");
        brandserv.save(brand);
        
        // agregar categoria default
        CategoryServiceImpl categoryserv = new CategoryServiceImpl();
        Category category = new Category(companyId, "Ninguna");
        categoryserv.save(category);
        
        // agregar deposito default
        CellarServiceImpl cellarserv = new CellarServiceImpl();
        Cellar cellar = new Cellar(companyId, "Deposito Unico", "Ninguna");
        cellarserv.save(cellar);
        
        // agregar familia default
        FamilyServiceImpl familyserv = new FamilyServiceImpl();
        Family family = new Family(companyId, "Ninguna", true);
        familyserv.save(family);
        
        // agregar presentacion default
        PresentationServiceImpl presentationserv = new PresentationServiceImpl();
        Presentation presentation = new Presentation(companyId, "Ninguna");
        presentationserv.save(presentation);
        
        ProductTypeServiceImpl producttypeserv = new ProductTypeServiceImpl();
        producttypeserv.save(new ProductType(companyId, "Producto", false, false));
        producttypeserv.save(new ProductType(companyId, "Servicio", false, false));
        
        // agregar tipos de visitas default
        VisitTypeServiceImpl visittypeserv = new VisitTypeServiceImpl();
        visittypeserv.save(new VisitType(companyId, "Demostración"));
        visittypeserv.save(new VisitType(companyId, "Soporte Tecnico"));
        visittypeserv.save(new VisitType(companyId, "Toma de pedido"));
        
        OrderTypeServiceImpl ordertypeserv = new OrderTypeServiceImpl();
        ordertypeserv.save(new OrderType("Cotización"));
        ordertypeserv.save(new OrderType("Pedido"));
        
    }
    
}
