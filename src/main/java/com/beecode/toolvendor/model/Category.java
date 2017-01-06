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
 * Category generated by hbm2java
 */
@Entity
@Table(name="category"
    , catalog= HibernateUtil.CATALOG
)
public class Category  implements java.io.Serializable {


     private Integer id;
     private Integer companyId;
     private String category;

    public Category() {
    }

    public Category(Integer companyId, String category) {
       this.companyId = companyId;
       this.category = category;
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

    
    @Column(name="category", length=45)
    public String getCategory() {
        return this.category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }




}


