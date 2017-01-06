/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.interfaces;

import com.beecode.toolvendor.model.Cellar;
import java.util.List;

/**
 *
 * @author luisvespa
 */
public interface CellarService {
    
    public String save(Cellar obj);
    
    public String update(Cellar obj);
    
    public boolean delete(int id);
    
    public Cellar findById(int id, int companyId);
    
    public Cellar findByName(String name, int companyId);
    
    public boolean findId(int id, int companyId);
    
    public boolean findName(String name, int companyId);
    
    public List getAllByCompany(Integer companyId);
    
}
