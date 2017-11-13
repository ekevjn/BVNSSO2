package com.baoviet.login.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A BamsUser.
 */
@Entity
@Table(name = "bams_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "bamsuser")
public class BamsUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "uname", length = 50, nullable = false)
    private String uname;

    @NotNull
    @Size(max = 60)
    @Column(name = "passwordhash", length = 60, nullable = false)
    private String passwordhash;

    @Size(max = 50)
    @Column(name = "firstname", length = 50)
    private String firstname;

    @Size(max = 50)
    @Column(name = "lastname", length = 50)
    private String lastname;

    @Size(max = 100)
    @Column(name = "email", length = 100)
    private String email;

    @Size(max = 20)
    @Column(name = "gender", length = 20)
    private String gender;

    @Size(max = 256)
    @Column(name = "imageurl", length = 256)
    private String imageurl;

    @Column(name = "activated")
    private Boolean activated;

    @Size(max = 5)
    @Column(name = "langkey", length = 5)
    private String langkey;

    @Size(max = 20)
    @Column(name = "activationkey", length = 20)
    private String activationkey;

    @Size(max = 20)
    @Column(name = "resetkey", length = 20)
    private String resetkey;

    @Size(max = 50)
    @Column(name = "createdby", length = 50)
    private String createdby;

    @Column(name = "createddate")
    private LocalDate createddate;

    @Column(name = "resetdate")
    private LocalDate resetdate;

    @Size(max = 50)
    @Column(name = "lastmodifiedby", length = 50)
    private String lastmodifiedby;

    @Column(name = "lastmodifieddate")
    private LocalDate lastmodifieddate;

    @Size(max = 60)
    @Column(name = "user_type", length = 60)
    private String user_type;

    @Size(max = 60)
    @Column(name = "outlet_id", length = 60)
    private String outlet_id;

    @Size(max = 60)
    @Column(name = "comp_code", length = 60)
    private String comp_code;

    @Size(max = 60)
    @Column(name = "mobi_phone", length = 60)
    private String mobi_phone;

    @ManyToMany(mappedBy = "bamsUsers")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BamsGenerCode> bamsGenerCodes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public BamsUser uname(String uname) {
        this.uname = uname;
        return this;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPasswordhash() {
        return passwordhash;
    }

    public BamsUser passwordhash(String passwordhash) {
        this.passwordhash = passwordhash;
        return this;
    }

    public void setPasswordhash(String passwordhash) {
        this.passwordhash = passwordhash;
    }

    public String getFirstname() {
        return firstname;
    }

    public BamsUser firstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public BamsUser lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public BamsUser email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public BamsUser gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImageurl() {
        return imageurl;
    }

    public BamsUser imageurl(String imageurl) {
        this.imageurl = imageurl;
        return this;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Boolean isActivated() {
        return activated;
    }

    public BamsUser activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getLangkey() {
        return langkey;
    }

    public BamsUser langkey(String langkey) {
        this.langkey = langkey;
        return this;
    }

    public void setLangkey(String langkey) {
        this.langkey = langkey;
    }

    public String getActivationkey() {
        return activationkey;
    }

    public BamsUser activationkey(String activationkey) {
        this.activationkey = activationkey;
        return this;
    }

    public void setActivationkey(String activationkey) {
        this.activationkey = activationkey;
    }

    public String getResetkey() {
        return resetkey;
    }

    public BamsUser resetkey(String resetkey) {
        this.resetkey = resetkey;
        return this;
    }

    public void setResetkey(String resetkey) {
        this.resetkey = resetkey;
    }

    public String getCreatedby() {
        return createdby;
    }

    public BamsUser createdby(String createdby) {
        this.createdby = createdby;
        return this;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public LocalDate getCreateddate() {
        return createddate;
    }

    public BamsUser createddate(LocalDate createddate) {
        this.createddate = createddate;
        return this;
    }

    public void setCreateddate(LocalDate createddate) {
        this.createddate = createddate;
    }

    public LocalDate getResetdate() {
        return resetdate;
    }

    public BamsUser resetdate(LocalDate resetdate) {
        this.resetdate = resetdate;
        return this;
    }

    public void setResetdate(LocalDate resetdate) {
        this.resetdate = resetdate;
    }

    public String getLastmodifiedby() {
        return lastmodifiedby;
    }

    public BamsUser lastmodifiedby(String lastmodifiedby) {
        this.lastmodifiedby = lastmodifiedby;
        return this;
    }

    public void setLastmodifiedby(String lastmodifiedby) {
        this.lastmodifiedby = lastmodifiedby;
    }

    public LocalDate getLastmodifieddate() {
        return lastmodifieddate;
    }

    public BamsUser lastmodifieddate(LocalDate lastmodifieddate) {
        this.lastmodifieddate = lastmodifieddate;
        return this;
    }

    public void setLastmodifieddate(LocalDate lastmodifieddate) {
        this.lastmodifieddate = lastmodifieddate;
    }

    public String getUser_type() {
        return user_type;
    }

    public BamsUser user_type(String user_type) {
        this.user_type = user_type;
        return this;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getOutlet_id() {
        return outlet_id;
    }

    public BamsUser outlet_id(String outlet_id) {
        this.outlet_id = outlet_id;
        return this;
    }

    public void setOutlet_id(String outlet_id) {
        this.outlet_id = outlet_id;
    }

    public String getComp_code() {
        return comp_code;
    }

    public BamsUser comp_code(String comp_code) {
        this.comp_code = comp_code;
        return this;
    }

    public void setComp_code(String comp_code) {
        this.comp_code = comp_code;
    }

    public String getMobi_phone() {
        return mobi_phone;
    }

    public BamsUser mobi_phone(String mobi_phone) {
        this.mobi_phone = mobi_phone;
        return this;
    }

    public void setMobi_phone(String mobi_phone) {
        this.mobi_phone = mobi_phone;
    }

    public Set<BamsGenerCode> getBamsGenerCodes() {
        return bamsGenerCodes;
    }

    public BamsUser bamsGenerCodes(Set<BamsGenerCode> bamsGenerCodes) {
        this.bamsGenerCodes = bamsGenerCodes;
        return this;
    }

    public BamsUser addBamsGenerCode(BamsGenerCode bamsGenerCode) {
        this.bamsGenerCodes.add(bamsGenerCode);
        bamsGenerCode.getBamsUsers().add(this);
        return this;
    }

    public BamsUser removeBamsGenerCode(BamsGenerCode bamsGenerCode) {
        this.bamsGenerCodes.remove(bamsGenerCode);
        bamsGenerCode.getBamsUsers().remove(this);
        return this;
    }

    public void setBamsGenerCodes(Set<BamsGenerCode> bamsGenerCodes) {
        this.bamsGenerCodes = bamsGenerCodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BamsUser bamsUser = (BamsUser) o;
        if (bamsUser.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, bamsUser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BamsUser{" +
            "id=" + id +
            ", uname='" + uname + "'" +
            ", passwordhash='" + passwordhash + "'" +
            ", firstname='" + firstname + "'" +
            ", lastname='" + lastname + "'" +
            ", email='" + email + "'" +
            ", gender='" + gender + "'" +
            ", imageurl='" + imageurl + "'" +
            ", activated='" + activated + "'" +
            ", langkey='" + langkey + "'" +
            ", activationkey='" + activationkey + "'" +
            ", resetkey='" + resetkey + "'" +
            ", createdby='" + createdby + "'" +
            ", createddate='" + createddate + "'" +
            ", resetdate='" + resetdate + "'" +
            ", lastmodifiedby='" + lastmodifiedby + "'" +
            ", lastmodifieddate='" + lastmodifieddate + "'" +
            ", user_type='" + user_type + "'" +
            ", outlet_id='" + outlet_id + "'" +
            ", comp_code='" + comp_code + "'" +
            ", mobi_phone='" + mobi_phone + "'" +
            '}';
    }
}
