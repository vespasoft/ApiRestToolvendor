package com.beecode.toolvendor.model;
// Generated Dec 14, 2016 3:53:36 PM by Hibernate Tools 4.3.1


import com.beecode.toolvendor.util.HibernateUtil;
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
 * Customer generated by hbm2java
 */
@Entity
@Table(name="customer"
    , catalog= HibernateUtil.CATALOG
)
public class Customer  implements java.io.Serializable {


     private Integer id;
     private City city;
     private Integer companyId;
     private Country country;
     private User user;
     private Date createdAt;
     private Date lastUpdate;
     private String companyName;
     private String contactName;
     private String contactPhone;
     private String contactEmail;
     private Boolean active;
     private String building;
     private String street;
     private String postalCode;
     private String reference;
     private Float latitud;
     private Float longitude;

    public Customer() {
    }

    public Customer(Integer companyId, User user) {
        this.companyId = companyId;
        this.user = user;
    }
	
    public Customer(Date createdAt, Date lastUpdate, String companyName, String contactName, String contactPhone, String contactEmail, Boolean active, String building, String street, String postalCode, String reference, Float latitud, Float longitude, Integer companyId, User user) {
       this.createdAt = createdAt;
       this.lastUpdate = lastUpdate;
       this.companyName = companyName;
       this.contactName = contactName;
       this.contactPhone = contactPhone;
       this.contactEmail = contactEmail;
       this.active = active;
       this.building = building;
       this.street = street;
       this.postalCode = postalCode;
       this.reference = reference;
       this.latitud = latitud;
       this.longitude = longitude;
       this.companyId = companyId;
       this.user = user;
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
    @JoinColumn(name="city_id", nullable=false)
    public City getCity() {
        return this.city;
    }
    
    public void setCity(City city) {
        this.city = city;
    }
    
@Column(name="company_id", nullable=false)
    public Integer getCompanyId() {
        return this.companyId;
    }
    
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

@ManyToOne(optional = false)
    @JoinColumn(name="country_id", nullable=false)
    public Country getCountry() {
        return this.country;
    }
    
    public void setCountry(Country country) {
        this.country = country;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name="user_id", nullable=false)
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
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

    
    @Column(name="company_name")
    public String getCompanyName() {
        return this.companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    
    @Column(name="contact_name", length=100)
    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    
    @Column(name="contact_phone", length=45)
    public String getContactPhone() {
        return this.contactPhone;
    }

    
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    
    @Column(name="contact_email", length=255)
    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
    
    @Column(name="active")
    public Boolean getActive() {
        return this.active;
    }
    
    public void setActive(Boolean active) {
        this.active = active;
    }

    
    @Column(name="building", length=45)
    public String getBuilding() {
        return this.building;
    }
    
    public void setBuilding(String building) {
        this.building = building;
    }

    
    @Column(name="street", length=250)
    public String getStreet() {
        return this.street;
    }
    
    public void setStreet(String street) {
        this.street = street;
    }

    
    @Column(name="postal_code", length=45)
    public String getPostalCode() {
        return this.postalCode;
    }
    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    
    @Column(name="reference", length=100)
    public String getReference() {
        return this.reference;
    }
    
    public void setReference(String reference) {
        this.reference = reference;
    }

    
    @Column(name="latitud", precision=12, scale=0)
    public Float getLatitud() {
        return this.latitud;
    }
    
    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    
    @Column(name="longitude", precision=12, scale=0)
    public Float getLongitude() {
        return this.longitude;
    }
    
    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }


}


