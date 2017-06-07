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

    public BackLog() {
    }

    public BackLog(String class_name, String void_name, String message) {
        this.class_name = class_name;
        this.void_name = void_name;
        this.error_message = message;
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
    
}
