/*
 * 
 *   To change this license header, choose License Headers in Project Properties.
 *  To change this template file, choose Tools | Templates
 * 
 */
package com.beecode.toolvendor.interfaces;

import com.beecode.toolvendor.model.VisitType;
import java.util.List;

/**
 *
 * @author luisvespa
 */
public interface VisitTypeService {
    
    public String save(VisitType obj);
    
    public String update(VisitType obj);
    
    public boolean delete(int id);
    
    public VisitType findById(int id, int companyId);
    
    public VisitType findByName(String name, int companyId);
    
    public boolean findId(int id, int companyId);
    
    public boolean findName(String name, int companyId);
    
    public List getAllByCompany(Integer companyId);
    
}
