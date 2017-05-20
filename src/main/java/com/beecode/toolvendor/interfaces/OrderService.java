/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.interfaces;

import com.beecode.toolvendor.model.Company;
import com.beecode.toolvendor.model.Order;
import java.util.List;

/**
 *
 * @author luisvespa
 */
public interface OrderService {
    
    public String save(Order obj, Company company);
    
    public String update(Order obj, Company company);
    
    public boolean delete(int id);
    
    public Order findById(int id, Company company);
    
    public boolean findId(int id, Company company);
    
    public List getAllByCompany(Integer companyId);
    
    public List getAllByUser(Integer userId);
    
    public List getAllByCustomer(Integer customerId);
    
}
