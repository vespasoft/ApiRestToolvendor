/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.model;

import com.beecode.toolvendor.util.HibernateUtil;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author luisvespa
 */
@Entity
@Table(name="config_email"
    , catalog= HibernateUtil.CATALOG
)
public class ConfigEmail implements java.io.Serializable {
    
    private Integer id;
    private Integer companyId;
    private String emailWellcomeCompany;
    private String emailWellcomeUser;
    private String emailForgot;
    private String emailProgramVisit;
    private String emailOrderCustomer;

    public ConfigEmail() {
    }

    public ConfigEmail(Integer id, Integer companyId, String emailWellcomeCompany, String emailWellcomeUser, String emailForgot, String emailProgramVisit, String emailOrderCustomer) {
        this.id = id;
        this.companyId = companyId;
        this.emailWellcomeCompany = emailWellcomeCompany;
        this.emailWellcomeUser = emailWellcomeUser;
        this.emailForgot = emailForgot;
        this.emailProgramVisit = emailProgramVisit;
        this.emailOrderCustomer = emailOrderCustomer;
    }
    
    @Id
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name="company_id", nullable=false)
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Column(name="email_wellcome_company", length = 65535,columnDefinition="Text")
    public String getEmailWellcomeCompany() {
        return emailWellcomeCompany;
    }

    public void setEmailWellcomeCompany(String emailWellcomeCompany) {
        this.emailWellcomeCompany = emailWellcomeCompany;
    }

    @Column(name="email_wellcome_user", length = 65535,columnDefinition="Text")
    public String getEmailWellcomeUser() {
        return emailWellcomeUser;
    }

    public void setEmailWellcomeUser(String emailWellcomeUser) {
        this.emailWellcomeUser = emailWellcomeUser;
    }

    @Column(name="email_forgot", length = 65535,columnDefinition="Text")
    public String getEmailForgot() {
        return emailForgot;
    }

    public void setEmailForgot(String emailForgot) {
        this.emailForgot = emailForgot;
    }

    @Column(name="email_program_visit", length = 65535,columnDefinition="Text")
    public String getEmailProgramVisit() {
        return emailProgramVisit;
    }

    public void setEmailProgramVisit(String emailProgramVisit) {
        this.emailProgramVisit = emailProgramVisit;
    }

    @Column(name="email_order_customer", length = 65535,columnDefinition="Text")
    public String getEmailOrderCustomer() {
        return emailOrderCustomer;
    }

    public void setEmailOrderCustomer(String emailOrderCustomer) {
        this.emailOrderCustomer = emailOrderCustomer;
    }
    
}
