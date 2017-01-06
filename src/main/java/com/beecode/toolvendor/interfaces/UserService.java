/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.interfaces;

import com.beecode.toolvendor.model.User;
import java.util.List;

/**
 *
 * @author luisvespa
 */
public interface UserService {
    
    public String save(User user);
    
    public String update(User user);
    
    public boolean delete(int id);
    
    public User findById(int id);
    
    public User findById(int id, int companyId);
    
    public User findByEmail(String email);
    
    public User findByAuth(String email, String password);
    
    public boolean findId(int id);
    
    public boolean findId(int id, int companyId);
    
    public boolean findEmail(String email);
    
    public List getAllByCompany(Integer companyId);
    
}
