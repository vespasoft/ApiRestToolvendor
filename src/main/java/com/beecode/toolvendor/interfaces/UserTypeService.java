/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.interfaces;

import com.beecode.toolvendor.model.UserType;
import java.util.List;

/**
 *
 * @author luisvespa
 */
public interface UserTypeService {
    
    public String save(UserType country);
    
    public String update(UserType country);
    
    public boolean delete(int id);
    
    public UserType findById(int id, int companyId);
    
    public UserType findByName(String name, int companyId);
    
    public boolean findId(int id, int companyId);
    
    public boolean findName(String name, int companyId);
    
    public List getAllByCompany(Integer companyId);
    
}
