package com.beecode.toolvendor.model;
// Generated Dec 14, 2016 3:53:36 PM by Hibernate Tools 4.3.1


import com.beecode.toolvendor.util.HibernateUtil;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ProductType generated by hbm2java
 */
@Entity
@Table(name="product_type"
    , catalog= HibernateUtil.CATALOG
)
public class ProductType implements java.io.Serializable {

     private Integer id;
     private Integer companyId;
     private String description;
     private Boolean inventary;
     private Boolean freePrice;

    public ProductType() {
    }

	
    public ProductType(Integer companyId, String description, Boolean inventary, Boolean freePrice) {
       this.companyId = companyId;
       this.description = description;
       this.inventary = inventary;
       this.freePrice = freePrice;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

@Column(name="company_id", nullable=false)
    public Integer getCompanyId() {
        return this.companyId;
    }
    
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    
    @Column(name="description", length=45)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    
    @Column(name="inventary")
    public Boolean getInventary() {
        return this.inventary;
    }
    
    public void setInventary(Boolean inventary) {
        this.inventary = inventary;
    }

    
    @Column(name="free_price")
    public Boolean getFreePrice() {
        return this.freePrice;
    }
    
    public void setFreePrice(Boolean freePrice) {
        this.freePrice = freePrice;
    }



}

