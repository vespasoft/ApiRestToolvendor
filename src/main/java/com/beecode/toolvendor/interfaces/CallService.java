/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.interfaces;

import com.beecode.toolvendor.model.Call;
import java.util.List;

/**
 *
 * @author luisvespa
 */
public interface CallService {
    
    public String save(Call call);
    
    public String update(Call call);
    
    public boolean delete(int id);
    
    public Call findById(int id);
    
    public boolean findId(int id);
    
    public List getAllByUser(Integer userId);
    
}
