package com.baoviet.login.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A BamsOutletPro.
 */
@Entity
@Table(name = "bams_outlet_product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "bamsoutletpro")
public class BamsOutletPro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 20)
    @Column(name = "sourcedata", length = 20)
    private String sourcedata;

    @Size(max = 60)
    @Column(name = "producttype", length = 60)
    private String producttype;

    @NotNull
    @Column(name = "effectivedate", nullable = false)
    private LocalDate effectivedate;

    @NotNull
    @Column(name = "ceasedate", nullable = false)
    private LocalDate ceasedate;

    @NotNull
    @Size(max = 60)
    @Column(name = "productcode", length = 60, nullable = false)
    private String productcode;

    @NotNull
    @Size(max = 9)
    @Column(name = "companycode", length = 9, nullable = false)
    private String companycode;

    @ManyToOne
    private BamsOutlet bamsOutlet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourcedata() {
        return sourcedata;
    }

    public BamsOutletPro sourcedata(String sourcedata) {
        this.sourcedata = sourcedata;
        return this;
    }

    public void setSourcedata(String sourcedata) {
        this.sourcedata = sourcedata;
    }

    public String getProducttype() {
        return producttype;
    }

    public BamsOutletPro producttype(String producttype) {
        this.producttype = producttype;
        return this;
    }

    public void setProducttype(String producttype) {
        this.producttype = producttype;
    }

    public LocalDate getEffectivedate() {
        return effectivedate;
    }

    public BamsOutletPro effectivedate(LocalDate effectivedate) {
        this.effectivedate = effectivedate;
        return this;
    }

    public void setEffectivedate(LocalDate effectivedate) {
        this.effectivedate = effectivedate;
    }

    public LocalDate getCeasedate() {
        return ceasedate;
    }

    public BamsOutletPro ceasedate(LocalDate ceasedate) {
        this.ceasedate = ceasedate;
        return this;
    }

    public void setCeasedate(LocalDate ceasedate) {
        this.ceasedate = ceasedate;
    }

    public String getProductcode() {
        return productcode;
    }

    public BamsOutletPro productcode(String productcode) {
        this.productcode = productcode;
        return this;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public String getCompanycode() {
        return companycode;
    }

    public BamsOutletPro companycode(String companycode) {
        this.companycode = companycode;
        return this;
    }

    public void setCompanycode(String companycode) {
        this.companycode = companycode;
    }

    public BamsOutlet getBamsOutlet() {
        return bamsOutlet;
    }

    public BamsOutletPro bamsOutlet(BamsOutlet bamsOutlet) {
        this.bamsOutlet = bamsOutlet;
        return this;
    }

    public void setBamsOutlet(BamsOutlet bamsOutlet) {
        this.bamsOutlet = bamsOutlet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BamsOutletPro bamsOutletPro = (BamsOutletPro) o;
        if (bamsOutletPro.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, bamsOutletPro.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BamsOutletPro{" +
            "id=" + id +
            ", sourcedata='" + sourcedata + "'" +
            ", producttype='" + producttype + "'" +
            ", effectivedate='" + effectivedate + "'" +
            ", ceasedate='" + ceasedate + "'" +
            ", productcode='" + productcode + "'" +
            ", companycode='" + companycode + "'" +
            '}';
    }
}
