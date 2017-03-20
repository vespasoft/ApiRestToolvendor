/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;

import com.beecode.toolvendor.dao.VisitPictureDAO;
import com.beecode.toolvendor.interfaces.VisitPictureService;
import com.beecode.toolvendor.model.VisitPicture;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author luisvespa
 */
public class VisitPictureServiceImpl implements VisitPictureService {

    private static final AtomicLong counter = new AtomicLong();
     
    //----------------------------- SERVICES -------------------------------------
    private VisitServiceImpl visitserv;
    
    //----------------------------- DAO ------------------------------------------
    private VisitPictureDAO dao;

    public VisitPictureServiceImpl() {
    }
    
    //----------------------- Agregar nuevo registro ---------------------------------
    @Override
    public String save(VisitPicture obj, Integer companyId) {
        dao = new VisitPictureDAO();
        visitserv = new VisitServiceImpl();
        VisitPicture current = null;
        String message="";
        if ( obj==null ) {
            message="Se espero un objeto VisitPicture en formato JSON";
        } else if ( obj.getVisit()==null ) {
            message="El campo VisitId no puede ser null";
        } else if ( obj.getPicture()==null ) {
            message="El campo Picture no puede ser null";
        } else if ( obj.getVisit().getId()==0 ) {
            message="El campo VisitId no puede ser igual a 0";
        } else if ( obj.getPicture().length()==0 ) {
            message="Debe indicar un nombre para la imagen con la extension .jpg o .png";    
        } else if ( obj.getVisit().getId()!=null && !visitserv.findId(obj.getVisit().getId(), companyId) ) {
            message="El campo visitId no existe o es invalido.";    
        } else if ( findPicture(obj.getPicture()) ) {
            message="Ya existe una imagen usando este nombre en el servidor";
        } else {
            
            //--- AtCreated fecha de creación del registro
            dao.add(obj);
            
            //--- obtiene el registro guardado con toda su info para la respuesta ---
            /*current = dao.findByPicture(obj.getPicture());
            if ( current==null) {
                message="El registro no se pudo guardar, ocurrio un error inesperado.";
            }*/

        }
        //-------------- si ocurrio un error la variable contiene el mensaje de error ---------------
        
        return message;
    }
 
    //------------------- Actualizar los datos de un registro existente --------------------------
    @Override
    public String update(VisitPicture obj, Integer companyId) {
        dao = new VisitPictureDAO();
        VisitPicture current = null;
        String message="";
        
        if ( obj==null ) {
            message="Se espero un objeto Group en formato JSON";
        } else if ( obj.getId()==0 ) {
            message="El campo Id no puede estar vacio";
        } else if ( obj.getVisit().getId()!=null && !findId(obj.getVisit().getId()) ) {
            message="No existe un registro con este visitId.";
        } else {
            //--- obtiene el registro con toda su info para luego editar --- 
            current = dao.findById( obj.getId() );
            if (current!=null) {
                //--- se reemplaza solo los campos obtenidos y que no vengan null desde el front
                if (obj.getPicture()!=null) current.setPicture(obj.getPicture());
                if (obj.getComment()!=null) current.setComment(obj.getComment());
                
                //--- se ejecuta el update en la capa de datos ---
                dao.update(current);
            } else {
                message="No se encontro un registro asociado para este id";
            }
        }
        
        return message;
    }
 
    //----- eliminar un registro por id -----
    @Override
    public boolean delete(int id) {
        dao = new VisitPictureDAO();
        boolean result = false;
        try {
            int i = dao.delete(id);
            result = i==1;
        } catch ( Exception e ) {
            System.out.println("Error in Stock delete: " + e.getMessage());
        }
        
        return result;
    }

    //----- encontrar un registro por id -----
    @Override
    public VisitPicture findById(int id) {
        dao = new VisitPictureDAO();
        VisitPicture result = null;
        try {
            // Se busca en la bd una imagen por Id.
            result = dao.findById(id);
        } catch ( Exception e ) {
            System.out.println("Error in VisitPicture findById: " + e.getMessage());
        }
        
        return result;
    }
    
    @Override
    public VisitPicture findByPicture(String picture) {
        dao = new VisitPictureDAO();
        VisitPicture result = null;
        try {
            // Se busca en la bd una imagen por nombre.
            result = dao.findByPicture(picture);
        } catch ( Exception e ) {
            System.out.println("Error in VisitPicture findById: " + e.getMessage());
        }
        
        return result;
    }

    //-----  encontrar un registro por id  -----
    @Override
    public boolean findId(int id) {
        dao = new VisitPictureDAO();
         // se consulta en la BD si el id del usuario existe y es valido
        return dao.findById(id)!=null;
    }
    
    @Override
    public boolean findPicture(String picture) {
        dao = new VisitPictureDAO();
         // se consulta en la BD si el id del usuario existe y es valido
        return dao.findByPicture(picture)!=null;
    }

    //----- retorna un listado registro por productId -----
    @Override
    public List getAllByVisit(Integer visitId) {
        dao = new VisitPictureDAO();
        List<VisitPicture> list = null;
        try {
            // Se consulta en la bd las marcas registradas de una compañia.
            list = dao.getAllByVisit(visitId);
        } catch ( Exception e ) {
            System.out.println("Error in Stock getAllByProduct: " + e.getMessage());
        }
        return list;
    }
    
}
