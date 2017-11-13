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
 * A BamsGenerType.
 */
@Entity
@Table(name = "bams_generic_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "bamsgenertype")
public class BamsGenerType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 0, max = 60)
    @Column(name = "type_code", length = 60, nullable = false)
    private String typeCode;

    @Size(max = 150)
    @Column(name = "type_name", length = 150)
    private String typeName;

    @Column(name = "type_status_code")
    private Boolean typeStatusCode;

    @Size(max = 60)
    @Column(name = "type_created_by", length = 60)
    private String typeCreatedBy;

    @Column(name = "type_created_date")
    private LocalDate typeCreatedDate;

    @Size(max = 60)
    @Column(name = "type_last_update_by", length = 60)
    private String typeLastUpdateBy;

    @Column(name = "type_last_update_date")
    private LocalDate typeLastUpdateDate;

    @Column(name = "type_version_id")
    private Double typeVersionId;

    @OneToMany(mappedBy = "bamsGenerType")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BamsGenerCode> bamsGenerCodes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public BamsGenerType typeCode(String typeCode) {
        this.typeCode = typeCode;
        return this;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public BamsGenerType typeName(String typeName) {
        this.typeName = typeName;
        return this;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Boolean isTypeStatusCode() {
        return typeStatusCode;
    }

    public BamsGenerType typeStatusCode(Boolean typeStatusCode) {
        this.typeStatusCode = typeStatusCode;
        return this;
    }

    public void setTypeStatusCode(Boolean typeStatusCode) {
        this.typeStatusCode = typeStatusCode;
    }

    public String getTypeCreatedBy() {
        return typeCreatedBy;
    }

    public BamsGenerType typeCreatedBy(String typeCreatedBy) {
        this.typeCreatedBy = typeCreatedBy;
        return this;
    }

    public void setTypeCreatedBy(String typeCreatedBy) {
        this.typeCreatedBy = typeCreatedBy;
    }

    public LocalDate getTypeCreatedDate() {
        return typeCreatedDate;
    }

    public BamsGenerType typeCreatedDate(LocalDate typeCreatedDate) {
        this.typeCreatedDate = typeCreatedDate;
        return this;
    }

    public void setTypeCreatedDate(LocalDate typeCreatedDate) {
        this.typeCreatedDate = typeCreatedDate;
    }

    public String getTypeLastUpdateBy() {
        return typeLastUpdateBy;
    }

    public BamsGenerType typeLastUpdateBy(String typeLastUpdateBy) {
        this.typeLastUpdateBy = typeLastUpdateBy;
        return this;
    }

    public void setTypeLastUpdateBy(String typeLastUpdateBy) {
        this.typeLastUpdateBy = typeLastUpdateBy;
    }

    public LocalDate getTypeLastUpdateDate() {
        return typeLastUpdateDate;
    }

    public BamsGenerType typeLastUpdateDate(LocalDate typeLastUpdateDate) {
        this.typeLastUpdateDate = typeLastUpdateDate;
        return this;
    }

    public void setTypeLastUpdateDate(LocalDate typeLastUpdateDate) {
        this.typeLastUpdateDate = typeLastUpdateDate;
    }

    public Double getTypeVersionId() {
        return typeVersionId;
    }

    public BamsGenerType typeVersionId(Double typeVersionId) {
        this.typeVersionId = typeVersionId;
        return this;
    }

    public void setTypeVersionId(Double typeVersionId) {
        this.typeVersionId = typeVersionId;
    }

    public Set<BamsGenerCode> getBamsGenerCodes() {
        return bamsGenerCodes;
    }

    public BamsGenerType bamsGenerCodes(Set<BamsGenerCode> bamsGenerCodes) {
        this.bamsGenerCodes = bamsGenerCodes;
        return this;
    }

    public BamsGenerType addBamsGenerCode(BamsGenerCode bamsGenerCode) {
        this.bamsGenerCodes.add(bamsGenerCode);
        bamsGenerCode.setBamsGenerType(this);
        return this;
    }

    public BamsGenerType removeBamsGenerCode(BamsGenerCode bamsGenerCode) {
        this.bamsGenerCodes.remove(bamsGenerCode);
        bamsGenerCode.setBamsGenerType(null);
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
        BamsGenerType bamsGenerType = (BamsGenerType) o;
        if (bamsGenerType.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, bamsGenerType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BamsGenerType{" +
            "id=" + id +
            ", typeCode='" + typeCode + "'" +
            ", typeName='" + typeName + "'" +
            ", typeStatusCode='" + typeStatusCode + "'" +
            ", typeCreatedBy='" + typeCreatedBy + "'" +
            ", typeCreatedDate='" + typeCreatedDate + "'" +
            ", typeLastUpdateBy='" + typeLastUpdateBy + "'" +
            ", typeLastUpdateDate='" + typeLastUpdateDate + "'" +
            ", typeVersionId='" + typeVersionId + "'" +
            '}';
    }
}
