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
 * Catalog generated by hbm2java
 */
@Entity
@Table(name="catalog"
    ,catalog= HibernateUtil.CATALOG
)
public class Catalog  implements java.io.Serializable {


     private Integer id;
     private Integer productId;
     private String photo;
     private String description;

    public Catalog() {
    }

	
    public Catalog(Integer productId, String photo, String description) {
       this.productId = productId;
       this.photo = photo;
       this.description = description;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name="product_id", nullable=false)
    public Integer getProductId() {
        return this.productId;
    }
    
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    
    @Column(name="photo")
    public String getPhoto() {
        return this.photo;
    }
    
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    
    @Column(name="description")
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }




}


