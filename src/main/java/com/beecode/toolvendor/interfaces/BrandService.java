/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.interfaces;

import com.beecode.toolvendor.model.Brand;
import java.util.List;

/**
 *
 * @author luisvespa
 */
public interface BrandService {
    
    public String save(Brand obj);
    
    public String update(Brand obj);
    
    public boolean delete(int id);
    
    public Brand findById(int id, int companyId);
    
    public Brand findByName(String name, int companyId);
    
    public boolean findId(int id, int companyId);
    
    public boolean findName(String name, int companyId);
    
    public List getAllByCompany(Integer companyId);
    
}
