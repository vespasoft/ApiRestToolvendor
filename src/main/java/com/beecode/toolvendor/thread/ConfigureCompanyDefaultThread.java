/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.thread;

import com.beecode.toolvendor.model.Company;
import com.beecode.toolvendor.model.User;
import com.beecode.toolvendor.service.CompanyServiceImpl;
import com.beecode.toolvendor.service.EmailServiceImpl;

/**
 *
 * @author luisvespa
 */
public class ConfigureCompanyDefaultThread extends Thread {

    private Company company;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public ConfigureCompanyDefaultThread(Company company) {
        this.company = company;
    }
    
    @Override
    public void run() {
        // se instancia la clase controladora de la empresa
        CompanyServiceImpl serv = new CompanyServiceImpl();
        // se valida que el object company no sea nulo
        if ( company!=null )
            // se crean todas las tablas por default de la empresa
            serv.configureCompanyDefault(company.getId());
    }
    
    
    
}
