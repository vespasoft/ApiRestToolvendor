/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.interfaces;

import com.beecode.toolvendor.model.Modules;
import java.util.List;

/**
 *
 * @author luisvespa
 */
public interface ModulesService {
    
    public String save(Modules obj);
    
    public String update(Modules obj);
    
    public boolean delete(int id);
    
    public Modules findById(int id);
    
    public Modules findByName(String name);
    
    public boolean findId(int id);
    
    public boolean findName(String name);
    
    public List getAll();
    
}
