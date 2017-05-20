/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.interfaces;

import com.beecode.toolvendor.model.Company;
import com.beecode.toolvendor.model.UserZone;
import java.util.List;

/**
 *
 * @author luisvespa
 */
public interface UserZoneService {
    
    public String save(UserZone zone, Company company);
    
    public String update(UserZone zone, Company company);
    
    public boolean delete(int id);
    
    public UserZone findById(int id);
    
    public boolean findId(int id);
    
    public boolean findUserZone(Integer userId, Integer zoneId);
    
    public List getAllByUser(Integer userId);
    
}
