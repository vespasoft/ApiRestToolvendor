/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.interfaces;

import com.beecode.toolvendor.model.Country;
import java.util.List;

/**
 *
 * @author luisvespa
 */
public interface CountryService {
    
    public String save(Country country);
    
    public String update(Country country);
    
    public boolean delete(int id);
    
    public Country findById(int id);
    
    public Country findByName(String name);
    
    public boolean findId(int id);
    
    public boolean findName(String name);
    
    public List getAll();
    
}
