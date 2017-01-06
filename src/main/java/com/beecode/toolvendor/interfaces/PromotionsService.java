/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.interfaces;

import com.beecode.toolvendor.model.Promotions;
import java.util.List;

/**
 *
 * @author luisvespa
 */
public interface PromotionsService {
    
    public String save(Promotions obj, Integer companyId);
    
    public String update(Promotions obj, Integer companyId);
    
    public boolean delete(int id);
    
    public Promotions findById(int id);
    
    public boolean findId(int id);
    
    public List getAllByCompany(Integer companyId);
    
}
