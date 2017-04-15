/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.interfaces;

import com.beecode.toolvendor.model.ConfigEmail;
import java.util.List;

/**
 *
 * @author luisvespa
 */
public interface ConfigEmailService {
    
    public String save(ConfigEmail obj);
    
    public String update(ConfigEmail obj);
    
    public boolean delete(int id);
    
    public ConfigEmail findByCompanyId(int companyId);
    
    public boolean findCompanyId(int companyId);
    
}
