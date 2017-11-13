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
 * A BamsPermission.
 */
@Entity
@Table(name = "bams_permission")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "bamspermission")
public class BamsPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Size(max = 150)
    @Column(name = "description", length = 150)
    private String description;

    @Size(max = 50)
    @Column(name = "createdby", length = 50)
    private String createdby;

    @Column(name = "createddate")
    private LocalDate createddate;

    @Size(max = 50)
    @Column(name = "lastmodifiedby", length = 50)
    private String lastmodifiedby;

    @Column(name = "lastmodifieddate")
    private LocalDate lastmodifieddate;

    @ManyToMany(mappedBy = "bamsPermissions")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BamsAuthority> bamsAuthorities = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public BamsPermission name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public BamsPermission description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedby() {
        return createdby;
    }

    public BamsPermission createdby(String createdby) {
        this.createdby = createdby;
        return this;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public LocalDate getCreateddate() {
        return createddate;
    }

    public BamsPermission createddate(LocalDate createddate) {
        this.createddate = createddate;
        return this;
    }

    public void setCreateddate(LocalDate createddate) {
        this.createddate = createddate;
    }

    public String getLastmodifiedby() {
        return lastmodifiedby;
    }

    public BamsPermission lastmodifiedby(String lastmodifiedby) {
        this.lastmodifiedby = lastmodifiedby;
        return this;
    }

    public void setLastmodifiedby(String lastmodifiedby) {
        this.lastmodifiedby = lastmodifiedby;
    }

    public LocalDate getLastmodifieddate() {
        return lastmodifieddate;
    }

    public BamsPermission lastmodifieddate(LocalDate lastmodifieddate) {
        this.lastmodifieddate = lastmodifieddate;
        return this;
    }

    public void setLastmodifieddate(LocalDate lastmodifieddate) {
        this.lastmodifieddate = lastmodifieddate;
    }

    public Set<BamsAuthority> getBamsAuthorities() {
        return bamsAuthorities;
    }

    public BamsPermission bamsAuthorities(Set<BamsAuthority> bamsAuthorities) {
        this.bamsAuthorities = bamsAuthorities;
        return this;
    }

    public BamsPermission addBamsAuthority(BamsAuthority bamsAuthority) {
        this.bamsAuthorities.add(bamsAuthority);
        bamsAuthority.getBamsPermissions().add(this);
        return this;
    }

    public BamsPermission removeBamsAuthority(BamsAuthority bamsAuthority) {
        this.bamsAuthorities.remove(bamsAuthority);
        bamsAuthority.getBamsPermissions().remove(this);
        return this;
    }

    public void setBamsAuthorities(Set<BamsAuthority> bamsAuthorities) {
        this.bamsAuthorities = bamsAuthorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BamsPermission bamsPermission = (BamsPermission) o;
        if (bamsPermission.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, bamsPermission.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BamsPermission{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", createdby='" + createdby + "'" +
            ", createddate='" + createddate + "'" +
            ", lastmodifiedby='" + lastmodifiedby + "'" +
            ", lastmodifieddate='" + lastmodifieddate + "'" +
            '}';
    }
}
