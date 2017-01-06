/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.interfaces;

import com.beecode.toolvendor.model.Groups;
import java.util.List;

/**
 *
 * @author luisvespa
 */
public interface GroupsService {
    
    public String save(Groups group);
    
    public String update(Groups group);
    
    public boolean delete(int id);
    
    public Groups findById(int id, int companyId);
    
    public Groups findByName(String name, int companyId);
    
    public boolean findId(int id, int companyId);
    
    public boolean findName(String name, int companyId);
    
    public List getAllByCompany(Integer companyId);
    
    public List getAllByUser(Integer userId);
    
}
