/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.interfaces;

import com.beecode.toolvendor.model.Customer;
import java.util.List;

/**
 *
 * @author luisvespa
 */
public interface CustomerService {
    
    public String save(Customer cstmr);
    
    public String update(Customer cstmr);
    
    public boolean delete(int id);
    
    public Customer findById(int id, int companyId);
    
    public Customer findByEmail(String email, int companyId);
    
    public boolean findId(int id, int companyId);
    
    public boolean findEmail(String email, int companyId);
    
    public boolean findName(String name, int companyId);
    
    public List getAllByCompany(Integer companyId);
    
    public List getAllByUser(Integer userId);
    
}
