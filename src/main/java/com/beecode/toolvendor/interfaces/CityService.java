/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.interfaces;

import com.beecode.toolvendor.model.City;
import com.beecode.toolvendor.model.Country;
import java.util.List;

/**
 *
 * @author luisvespa
 */
public interface CityService {
    
    public String save(City city);
    
    public String update(City city);
    
    public boolean delete(int id);
    
    public City findById(int id);
    
    public City findByName(String name);
    
    public boolean findId(int id);
    
    public boolean findName(String name);
    
    public List getAllByCountry(Country country);
    
}
