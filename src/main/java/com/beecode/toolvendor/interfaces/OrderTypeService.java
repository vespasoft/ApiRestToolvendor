/*
 * 
 *   To change this license header, choose License Headers in Project Properties.
 *  To change this template file, choose Tools | Templates
 * 
 */
package com.beecode.toolvendor.interfaces;

import com.beecode.toolvendor.model.OrderType;
import java.util.List;

/**
 *
 * @author luisvespa
 */
public interface OrderTypeService {
    
    public String save(OrderType obj);
    
    public String update(OrderType obj);
    
    public boolean delete(int id);
    
    public OrderType findById(int id);
    
    public OrderType findByName(String name);
    
    public boolean findId(int id);
    
    public boolean findName(String name);
    
    public List getAll();
    
}
