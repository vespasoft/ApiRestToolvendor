/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.interfaces;

import com.beecode.toolvendor.model.Company;
import com.beecode.toolvendor.model.Groups;
import com.beecode.toolvendor.model.Permission;
import com.beecode.toolvendor.model.User;
import java.util.List;

/**
 *
 * @author luisvespa
 */
public interface PermissionService {
    
    public String save(Permission permission, Integer companyId);
    
    public String update(Permission permission, Integer companyId);
    
    public boolean delete(int id);
    
    public Permission findById(int id);
    
    public Permission findByGroupModule(int groupId, int ModuleId);
    
    public boolean findId(int id);
    
    public boolean findGroupModule(int groupId, int ModuleId);
    
    public List getAllByGroup(Integer groupId);
    
}
