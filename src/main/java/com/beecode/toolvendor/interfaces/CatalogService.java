/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.interfaces;

import com.beecode.toolvendor.model.Catalog;
import java.util.List;

/**
 *
 * @author luisvespa
 */
public interface CatalogService {
    
    public String save(Catalog obj);
    
    public String update(Catalog obj);
    
    public boolean delete(int id);
    
    public Catalog findById(int id);
    
    public boolean findId(int id);
    
    public List getAllByProduct(Integer productId);
    
}
