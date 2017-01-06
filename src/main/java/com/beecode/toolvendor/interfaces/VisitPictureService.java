/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.interfaces;

import com.beecode.toolvendor.model.VisitPicture;
import java.util.List;

/**
 *
 * @author luisvespa
 */
public interface VisitPictureService {
    
    public String save(VisitPicture obj, Integer companyId);
    
    public String update(VisitPicture obj, Integer companyId);
    
    public boolean delete(int id);
    
    public VisitPicture findById(int id);
    
    public VisitPicture findByPicture(String picture);
    
    public boolean findId(int id);
    
    public boolean findPicture(String picture);
    
    public List getAllByVisit(Integer visitId);
    
}
