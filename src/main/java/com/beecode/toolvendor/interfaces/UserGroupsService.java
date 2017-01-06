/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.interfaces;

import com.beecode.toolvendor.model.UserGroups;
import java.util.List;

/**
 *
 * @author luisvespa
 */
public interface UserGroupsService {
    
    public String save(UserGroups group, Integer companyId);
    
    public String update(UserGroups group, Integer companyId);
    
    public boolean delete(int id);
    
    public UserGroups findById(int id);
    
    public boolean findId(int id);
    
    public boolean findUserGroups(int userId, int groupId);
    
    public List getAllByUser(Integer userId);
    
}
