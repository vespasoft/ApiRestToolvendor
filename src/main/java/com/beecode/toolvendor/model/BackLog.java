/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.model;

import com.beecode.toolvendor.util.HibernateUtil;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author luisvespa
 */
@Entity
@Table(name="back_logs"
    , catalog= HibernateUtil.CATALOG
)
public class BackLog implements java.io.Serializable {
    
    private Integer id;
    private Date createdAt;
    private String class_name;
    private String void_name;
    private String error_message;
    private Integer companyId;
    private Integer userId;
    private Integer modules_id;

    public BackLog() {
    }

    public BackLog(String class_name, String void_name, String message) {
        this.class_name = class_name;
        this.void_name = void_name;
        this.error_message = message;
        //this.companyId = companyId;
        //this.userId = userId;
        //this.modules_id = modules_id;
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at", length=19)
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Column(name="class_name")
    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }
    
    @Column(name="void_name")
    public String getVoid_name() {
        return void_name;
    }

    public void setVoid_name(String void_name) {
        this.void_name = void_name;
    }

    @Column(name="error_message")
    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    @Column(name="country_id")
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Column(name="user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Column(name="module_id")
    public Integer getModules_id() {
        return modules_id;
    }

    public void setModules_id(Integer modules_id) {
        this.modules_id = modules_id;
    }
    
    
    
}
