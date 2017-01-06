package com.beecode.toolvendor.model;
// Generated Dec 14, 2016 3:53:36 PM by Hibernate Tools 4.3.1

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
 * Call generated by hbm2java
 */
@Entity
@Table(name="call"
    ,catalog= HibernateUtil.CATALOG
)
public class Call  implements java.io.Serializable {

    private Integer id;
    private Integer userId;
    private Date createdAt;
    private String phone;
    private Date callBeginTime;
    private Date callEndTime;

    public Call() {
    }
	
    public Call(Integer userId, Date createdAt, String phone, Date callBeginTime, Date callEndTime) {
       this.userId = userId;
       this.createdAt = createdAt;
       this.phone = phone;
       this.callBeginTime = callBeginTime;
       this.callEndTime = callEndTime;
    }
   
    @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    @Column(name="user_id", nullable=false)
    public Integer getUserId() {
        return this.userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at", length=19)
    public Date getCreatedAt() {
        return this.createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    
    @Column(name="phone", length=45)
    public String getPhone() {
        return this.phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Temporal(TemporalType.TIME)
    @Column(name="call_begin_time", length=8)
    public Date getCallBeginTime() {
        return this.callBeginTime;
    }
    
    public void setCallBeginTime(Date callBeginTime) {
        this.callBeginTime = callBeginTime;
    }

    @Temporal(TemporalType.TIME)
    @Column(name="call_end_time", length=8)
    public Date getCallEndTime() {
        return this.callEndTime;
    }
    
    public void setCallEndTime(Date callEndTime) {
        this.callEndTime = callEndTime;
    }




}


