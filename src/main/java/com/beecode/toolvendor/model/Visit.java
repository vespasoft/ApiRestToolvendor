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
     private Customer customer;
     private Integer userId;
     private Integer companyId;
     private VisitType visitType;
     private Date createdAt;
     private Date lastUpdate;
     private Date scheduledDate;
     private Date checkin;
     private Date checkout;
     private String firm;
     private String comment;
     private String reason;
     private String status;

    public Visit() {
    }

    public Visit(Customer customer, Integer userId, Integer companyId, VisitType visitType, Timestamp createdAt, Timestamp scheduledDate, String reason) {
       this.customer = customer;
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

    @ManyToOne(optional = false)
    @JoinColumn(name="customer_id", nullable=false)
    public Customer getCustomer() {
        return this.customer;
    }
    
    public void setCustomer(Customer cstmr) {
        this.customer = cstmr;
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
    public Date getCreatedAt() {
        return this.createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="last_update", length=19)
    public Date getLastUpdate() {
        return this.lastUpdate;
    }
    
    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="scheduled_date", length=19)
    public Date getScheduledDate() {
        return this.scheduledDate;
    }
    
    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="checkin", length=19)
    public Date getCheckin() {
        return this.checkin;
    }
    
    public void setCheckin(Date checkin) {
        this.checkin = checkin;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="checkout", length=19)
    public Date getCheckout() {
        return this.checkout;
    }
    
    public void setCheckout(Date checkout) {
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
    
    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        if ( getCheckin()==null && getCheckout()==null) return "pendiente";
        else if ( getCheckin()!=null && getCheckout()==null) return "checkin";
        else if ( getCheckin()!=null && getCheckout()!=null) return "checkout";
        else return "";
    }

}


