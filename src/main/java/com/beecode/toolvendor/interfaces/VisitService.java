/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.interfaces;

import com.beecode.toolvendor.model.Company;
import com.beecode.toolvendor.model.Visit;
import java.util.List;

/**
 *
 * @author luisvespa
 */
public interface VisitService {
    
    public String save(Visit obj, Company company);
    
    public String update(Visit obj, Company company);
    
    public boolean delete(int id);
    
    public Visit findById(int id, int companyId);
    
    public boolean findId(int id, int companyId);
    
    public List getAllByCompany(Integer companyId);
    
    public List getAllByUser(Integer userId);
    
    public List getAllByCustomer(Integer customerId);
    
}
