package com.beecode.toolvendor.model;
// Generated Dec 14, 2016 3:53:36 PM by Hibernate Tools 4.3.1


import com.beecode.toolvendor.util.HibernateUtil;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Visit generated by hbm2java
 */
@Entity
@Table(name="visit"
    ,catalog= HibernateUtil.CATALOG
)
public class Visit  implements java.io.Serializable {


     private Integer id;
     private Integer customerId;
     private Integer userId;
     private Integer companyId;
     private VisitType visitType;
     private Timestamp createdAt;
     private Timestamp lastUpdate;
     private Timestamp scheduledDate;
     private Timestamp checkin;
     private Timestamp checkout;
     private String firm;
     private String comment;
     private String reason;

    public Visit() {
    }

    public Visit(Integer customerId, Integer userId, Integer companyId, VisitType visitType, Timestamp createdAt, Timestamp scheduledDate, String reason) {
       this.customerId = customerId;
       this.userId = userId;
       this.companyId = companyId;
       this.visitType = visitType;
       this.createdAt = createdAt;
       this.scheduledDate = scheduledDate;
       this.reason = reason;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name="customer_id", nullable=false)
    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    @Column(name="user_id", nullable=false)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Column(name="company_id", nullable=false)
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
    
@ManyToOne(optional = false)
    @JoinColumn(name="visit_type_id", nullable=false)
    public VisitType getVisitType() {
        return this.visitType;
    }
    
    public void setVisitType(VisitType visitType) {
        this.visitType = visitType;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at", length=19)
    public Timestamp getCreatedAt() {
        return this.createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="last_update", length=19)
    public Timestamp getLastUpdate() {
        return this.lastUpdate;
    }
    
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="scheduled_date", length=19)
    public Timestamp getScheduledDate() {
        return this.scheduledDate;
    }
    
    public void setScheduledDate(Timestamp scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="checkin", length=19)
    public Timestamp getCheckin() {
        return this.checkin;
    }
    
    public void setCheckin(Timestamp checkin) {
        this.checkin = checkin;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="checkout", length=19)
    public Timestamp getCheckout() {
        return this.checkout;
    }
    
    public void setCheckout(Timestamp checkout) {
        this.checkout = checkout;
    }

    
    @Column(name="firm")
    public String getFirm() {
        return this.firm;
    }
    
    public void setFirm(String firm) {
        this.firm = firm;
    }

    
    @Column(name="comment", length=250)
    public String getComment() {
        return this.comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }

    
    @Column(name="reason", length=100)
    public String getReason() {
        return this.reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }



}


