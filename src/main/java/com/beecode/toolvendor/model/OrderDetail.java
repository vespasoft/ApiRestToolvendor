package com.beecode.toolvendor.model;
// Generated Dec 14, 2016 3:53:36 PM by Hibernate Tools 4.3.1


import com.beecode.toolvendor.util.HibernateUtil;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * OrderDetail generated by hbm2java
 */
@Entity
@Table(name="order_detail"
    , catalog= HibernateUtil.CATALOG
)
public class OrderDetail  implements java.io.Serializable {


     private Integer id;
     private Order order;
     private Product product;
     private Integer cant;
     private Double price;

    public OrderDetail() {
    }

	
    public OrderDetail(Order order, Product product, Integer cant, Double price) {
       this.order = order;
       this.product = product;
       this.cant = cant;
       this.price = price;
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
    @JoinColumn(name="order_id", nullable=false)
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

@ManyToOne(optional = false)
    @JoinColumn(name="product_id", nullable=false)
    public Product getProduct() {
        return this.product;
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }

    
    @Column(name="cant")
    public Integer getCant() {
        return this.cant;
    }
    
    public void setCant(Integer cant) {
        this.cant = cant;
    }

    
    @Column(name="price", precision=22, scale=0)
    public Double getPrice() {
        return this.price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }




}


