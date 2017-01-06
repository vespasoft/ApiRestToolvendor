/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.interfaces;

import com.beecode.toolvendor.model.Contact;
import java.util.List;

/**
 *
 * @author luisvespa
 */
public interface ContactService {
    
    public String save(Contact contact, Integer companyId);
    
    public String update(Contact contact, Integer companyId);
    
    public boolean delete(int id);
    
    public Contact findById(int id);
    
    public Contact findByName(String name);
    
    public boolean findId(int id);
    
    public boolean findName(String name);
    
    public List getAllByUser(Integer userId);
    
}
