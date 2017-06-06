/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.interfaces;

import com.beecode.toolvendor.model.BackLog;
import java.util.List;

/**
 *
 * @author luisvespa
 */
public interface BackLogService {
    
    public boolean save(BackLog obj);
    
    public boolean update(BackLog obj);
    
    public boolean delete(int id);
    
    public BackLog findById(int id);
    
    public boolean findId(int id);
    
    public List getAllByCompany(Integer companyId);
    
    public List getAllByUser(Integer userId);
    
}
