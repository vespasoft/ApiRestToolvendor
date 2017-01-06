/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.interfaces;

import com.beecode.toolvendor.model.Stock;
import java.util.List;

/**
 *
 * @author luisvespa
 */
public interface StockService {
    
    public String save(Stock obj, Integer companyId);
    
    public String update(Stock obj, Integer companyId);
    
    public boolean delete(int id);
    
    public Stock findById(int id);
    
    public boolean findId(int id);
    
    public List getAllByProduct(Integer productId);
    
}
