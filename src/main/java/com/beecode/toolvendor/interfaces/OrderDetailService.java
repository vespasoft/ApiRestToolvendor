/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.interfaces;

import com.beecode.toolvendor.model.OrderDetail;
import java.util.List;

/**
 *
 * @author luisvespa
 */
public interface OrderDetailService {
    
    public String save(OrderDetail obj, Integer companyId);
    
    public String update(OrderDetail obj, Integer companyId);
    
    public boolean delete(int id);
    
    public OrderDetail findById(int id);
    
    public boolean findId(int id);
    
    public List getAllByOrder(Integer orderId);
    
}
