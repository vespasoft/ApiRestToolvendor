/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.interfaces;

import com.beecode.toolvendor.model.Product;
import java.util.List;

/**
 *
 * @author luisvespa
 */
public interface ProductService {
    
    public String save(Product prod);
    
    public String update(Product prod);
    
    public boolean delete(Integer id);
    
    public Product findById(Integer id, int companyId);
    
    public Product findByName(String name, int companyId);
    
    public Product findByBarcode(String barcode, int companyId);
    
    public boolean findId(Integer id, int companyId);
    
    public boolean findName(String name, int companyId);
    
    public boolean findBarcode(String barcode, int companyId);
    
    public List getAllByCompany(Integer companyId);
    
}
