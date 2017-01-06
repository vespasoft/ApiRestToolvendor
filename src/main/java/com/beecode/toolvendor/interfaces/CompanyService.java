/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.interfaces;

import com.beecode.toolvendor.model.Company;
import com.beecode.toolvendor.model.User;
import java.util.List;

/**
 *
 * @author luisvespa
 */
public interface CompanyService {
    
    public String save(Company company);
    
    public String update(Company company);
    
    public boolean delete(int id);
    
    public Company findById(int id);
    
    public boolean findId(int id);
    
    public boolean findCompanyName(String name);
    
    public boolean findEmail(String email);
    
    public List getAll();
    
}
