/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.interfaces;

import com.beecode.toolvendor.model.Zone;
import java.util.List;

/**
 *
 * @author luisvespa
 */
public interface ZoneService {
    
    public String save(Zone zone);
    
    public String update(Zone zone);
    
    public boolean delete(int id);
    
    public Zone findById(int id, int companyId);
    
    public Zone findByName(String name, int companyId);
    
    public boolean findId(int id, int companyId);
    
    public boolean findName(String name, int companyId);
    
    public List getAllByCompany(Integer companyId);
    
    public List getAllByUser(Integer userId);
    
}
