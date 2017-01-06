/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.interfaces;

import com.beecode.toolvendor.model.ProductType;
import java.util.List;

/**
 *
 * @author luisvespa
 */
public interface ProductTypeService {
    
    public String save(ProductType obj);
    
    public String update(ProductType obj);
    
    public boolean delete(int id);
    
    public ProductType findById(int id, int companyId);
    
    public ProductType findByName(String name, int companyId);
    
    public boolean findId(int id, int companyId);
    
    public boolean findName(String name, int companyId);
    
    public List getAllByCompany(Integer companyId);
    
}
