/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;


import com.beecode.toolvendor.dao.ProductDAO;
import com.beecode.toolvendor.interfaces.ProductService;
import com.beecode.toolvendor.model.Product;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author luisvespa
 */
public class ProductServiceImpl implements ProductService  {

    private static final AtomicLong counter = new AtomicLong();
     
    //------------------------------- DAO ---------------------------------------
    private final ProductDAO dao = new ProductDAO();
    
    //----------------------------- SERVICES ------------------------------------
    private final BrandServiceImpl brandserv = new BrandServiceImpl();
    private final CategoryServiceImpl categoryserv = new CategoryServiceImpl();
    private final FamilyServiceImpl familyserv = new FamilyServiceImpl();
    private final PresentationServiceImpl presentationserv = new PresentationServiceImpl();
    private final ProductTypeServiceImpl producttypeserv = new ProductTypeServiceImpl();

    public ProductServiceImpl() {
    }
    
    //----------------------------- SAVE PRODUCT --------------------------------
    @Override
    public String save(Product prod) {
        Product currentProduct = null;
        String message="";
        try {
            if ( prod==null ) {
                message="Se espero un objeto user en formato JSON";
            } else if ( prod.getName()==null ) {
                message="El campo name no puede ser nullo";
            } else if ( prod.getBarcode()==null ) {
                message="El campo barcode no puede ser nullo";
            } else if ( prod.getBrand().getId()==0 ) {
                message="El campo BrandId no puede ser nullo";
            } else if ( prod.getCategory().getId()==0 ) {
                message="El campo CategoryId no puede ser nullo";
            } else if ( prod.getFamily().getId()==0 ) {
                message="El campo FamilyId no puede estar vacio";
            } else if ( prod.getPresentation().getId()==0 ) {
                message="El campo PresentationId() no puede ser igual a 0";
            } else if ( prod.getProductType().getId()==0 ) {
                message="El campo ProductTypeId no puede ser igual a 0";
            } else if ( findName(prod.getName(), prod.getCompanyId()) ) {
                message="Ya existe un registro con el mismo nombre";
            } else if ( findBarcode(prod.getBarcode(), prod.getCompanyId()) ) {
                message="Ya existe un registro con el mismo Barcode";    
            } else if ( findId(prod.getId(), prod.getCompanyId()) ) {
                message="Existe un registro con este Id.";
            } else if ( !brandserv.findId(prod.getBrand().getId(), prod.getCompanyId()) ) {
                message="No existe un registro con este brandId.";
            } else if ( !categoryserv.findId(prod.getCategory().getId(), prod.getCompanyId()) ) {
                message="No existe un registro con este categoryId.";
            } else if ( !familyserv.findId(prod.getFamily().getId(), prod.getCompanyId()) ) {
                message="No existe un registro con este familyId.";
            } else if ( !producttypeserv.findId(prod.getProductType().getId(), prod.getCompanyId()) ) {
                message="No existe un registro con este producttypeId.";
            } else if ( !presentationserv.findId(prod.getPresentation().getId(), prod.getCompanyId()) ) {
                message="No existe un registro con este presentationId.";
            } else {
                //--- AtCreated fecha de creación del registro
                prod.setCreatedAt(new Date() );
                dao.add(prod);
                //--- obtiene el product registrado con toda su info para la respuesta ---
                currentProduct = dao.findByName(prod.getName(), prod.getCompanyId());
                if ( currentProduct==null) {
                    message="El registro no se pudo guardar, ocurrio un error inesperado.";
                }
            }
        } catch ( Exception e ) {
            System.out.println("Error in product save: " + e.getMessage());
        }
        
        return message;
    }

    //----------------------------- UPDATE PRODUCT --------------------------------
    @Override
    public String update(Product prod) {
        Product currentProduct = null;
        String message="";
        try {
            if ( prod==null ) {
                message="Se espero un objeto user en formato JSON";
            } else if ( prod.getId()==null ) {
                message="El campo Id no puede ser nullo";
            } else if ( prod.getBrand()!=null && !brandserv.findId(prod.getBrand().getId(), prod.getCompanyId()) ) {
                message="No existe un registro con este brandId.";
            } else if ( prod.getCategory()!=null && !categoryserv.findId(prod.getCategory().getId(), prod.getCompanyId()) ) {
                message="No existe un registro con este categoryId.";
            } else if ( prod.getFamily()!=null && !familyserv.findId(prod.getFamily().getId(), prod.getCompanyId()) ) {
                message="No existe un registro con este familyId.";
            } else if ( prod.getProductType()!=null && !producttypeserv.findId(prod.getProductType().getId(), prod.getCompanyId()) ) {
                message="No existe un registro con este producttypeId.";
            } else if ( prod.getPresentation()!=null && !presentationserv.findId(prod.getPresentation().getId(), prod.getCompanyId()) ) {
                message="No existe un registro con este presentationId.";
            } else {
                //--- obtiene el registro con toda su info para luego editar ---
                currentProduct = dao.findById(prod.getId(), prod.getCompanyId());
                if (currentProduct!=null) {
                    //--- se reemplaza solo los campos obtenidos y que no vengan null desde el front
                    if (prod.getId()!=null) currentProduct.setId(prod.getId());
                    if (prod.getName()!=null) currentProduct.setName(prod.getName());
                    if (prod.getDescription()!=null) currentProduct.setDescription(prod.getDescription());
                    if (prod.getBarcode()!=null) currentProduct.setBarcode(prod.getBarcode());
                    if (prod.getBrand()!=null) currentProduct.setBrand(prod.getBrand());
                    if (prod.getCategory()!=null) currentProduct.setCategory(prod.getCategory());
                    if (prod.getFamily()!=null) currentProduct.setFamily(prod.getFamily());
                    if (prod.getPresentation()!=null) currentProduct.setPresentation(prod.getPresentation());
                    if (prod.getProductType()!=null) currentProduct.setProductType(prod.getProductType());
                    if (prod.getDetail()!=null) currentProduct.setDetail(prod.getDetail());
                    if (prod.getPhoto()!=null) currentProduct.setPhoto(prod.getPhoto());
                    if (prod.getPrice()!=null) currentProduct.setPrice(prod.getPrice());
                    if (prod.getTax()!=null) currentProduct.setTax(prod.getTax());
                    //--- LastUpdate fecha de actualizacion del registro
                    currentProduct.setLastUpdate(new Date());
                    //--- se ejecuta el update en la capa de datos ---
                    dao.update(currentProduct);
                } else {
                    message="No se encontro un registro asociado para este id";
                }

            }
        } catch ( Exception e ) {
            System.out.println("Error in product update: " + e.getMessage());
        }
        
        
        return message;
    }

    //----------------------------- DELETE PRODUCT ----------------------------------
    @Override
    public boolean delete(Integer id) {
        boolean result = false;
        try {
            int i = dao.delete(id);
            result = i==1;
        } catch ( Exception e ) {
            System.out.println("Error in product delete: " + e.getMessage());
        }
        
        return result;
    }

    public boolean findId(Integer id) {
        // se consulta en la BD si el id del producto existe y es valido
        return dao.findById(id)!=null;
    }
    
    //--------------------- FIND BY ID BOOLEAN --------------------------
    @Override
    public boolean findId(Integer id, int companyId) {
        // se consulta en la BD si el id del producto existe y es valido
        return dao.findById(id, companyId)!=null;
    }
    
    //--------------------- FIND BY NAME BOOLEAN --------------------------
    @Override
    public boolean findName(String name, int companyId) {
        // se consulta en la BD si el nombre del producto existe y es valido
        return dao.findByName(name, companyId)!=null;
    }
    
    //--------------------- FIND BY NAME BOOLEAN --------------------------
    @Override
    public boolean findBarcode(String barcode, int companyId) {
        // se consulta en la BD si el barcode del producto existe y es valido
        return dao.findByBarcode(barcode, companyId)!=null;
    }
    
    //--------------------- FIND BY ID GET PRODUCT --------------------------------
    @Override
    public Product findById(Integer id, int companyId){
        Product result = null;
        try {
            // Se busca en la bd los datos del producto por Id.
            result = dao.findById(id, companyId);
        } catch ( Exception e ) {
            System.out.println("Error in product findById: " + e.getMessage());
        }
        return result;
    }
    
    //------------------------ FIND BY NAME GET PRODUCT -------------------------------
    @Override
    public Product findByName(String name, int companyId){
        Product result = null;
        try {
            // Se busca en la bd los datos del producto por Id.
            result = dao.findByName(name, companyId);
        } catch ( Exception e ) {
            System.out.println("Error in product findByName: " + e.getMessage());
        }
        return result;
    }
    
    //--------------------- FIND BY BARCODE GET PRODUCT --------------------------------
    @Override
    public Product findByBarcode(String barcode, int companyId){
        Product result = null;
        try {
            // Se busca en la bd los datos del producto por Id.
            result = dao.findByBarcode(barcode, companyId);
        } catch ( Exception e ) {
            System.out.println("Error in product findByBarcode: " + e.getMessage());
        }
        return result;
    }

    //--------------------- GETALL PRODUCT BY COMPANY --------------------------------
    @Override
    public List getAllByCompany(Integer companyId) {
        List<Product> list = null;
        try {
            // Se consulta en la bd los customer registrados para un usuario.
            list = dao.getAllByCompany(companyId);
        } catch ( Exception e ) {
            System.out.println("Error in product getAllByCompany: " + e.getMessage());
        }
        return list;
    }
    
}
