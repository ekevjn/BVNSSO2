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
 * A BamsAuthority.
 */
@Entity
@Table(name = "bams_authority")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "bamsauthority")
public class BamsAuthority implements Serializable {

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

    @Column(name = "activated")
    private Boolean activated;

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

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "bams_authority_bams_permission",
               joinColumns = @JoinColumn(name="bams_authorities_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="bams_permissions_id", referencedColumnName="id"))
    private Set<BamsPermission> bamsPermissions = new HashSet<>();

    @ManyToMany(mappedBy = "bamsAuthorities")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BamsGenerCode> bamsGenerCodes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public BamsAuthority name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public BamsAuthority description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isActivated() {
        return activated;
    }

    public BamsAuthority activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getCreatedby() {
        return createdby;
    }

    public BamsAuthority createdby(String createdby) {
        this.createdby = createdby;
        return this;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public LocalDate getCreateddate() {
        return createddate;
    }

    public BamsAuthority createddate(LocalDate createddate) {
        this.createddate = createddate;
        return this;
    }

    public void setCreateddate(LocalDate createddate) {
        this.createddate = createddate;
    }

    public String getLastmodifiedby() {
        return lastmodifiedby;
    }

    public BamsAuthority lastmodifiedby(String lastmodifiedby) {
        this.lastmodifiedby = lastmodifiedby;
        return this;
    }

    public void setLastmodifiedby(String lastmodifiedby) {
        this.lastmodifiedby = lastmodifiedby;
    }

    public LocalDate getLastmodifieddate() {
        return lastmodifieddate;
    }

    public BamsAuthority lastmodifieddate(LocalDate lastmodifieddate) {
        this.lastmodifieddate = lastmodifieddate;
        return this;
    }

    public void setLastmodifieddate(LocalDate lastmodifieddate) {
        this.lastmodifieddate = lastmodifieddate;
    }

    public Set<BamsPermission> getBamsPermissions() {
        return bamsPermissions;
    }

    public BamsAuthority bamsPermissions(Set<BamsPermission> bamsPermissions) {
        this.bamsPermissions = bamsPermissions;
        return this;
    }

    public BamsAuthority addBamsPermission(BamsPermission bamsPermission) {
        this.bamsPermissions.add(bamsPermission);
        bamsPermission.getBamsAuthorities().add(this);
        return this;
    }

    public BamsAuthority removeBamsPermission(BamsPermission bamsPermission) {
        this.bamsPermissions.remove(bamsPermission);
        bamsPermission.getBamsAuthorities().remove(this);
        return this;
    }

    public void setBamsPermissions(Set<BamsPermission> bamsPermissions) {
        this.bamsPermissions = bamsPermissions;
    }

    public Set<BamsGenerCode> getBamsGenerCodes() {
        return bamsGenerCodes;
    }

    public BamsAuthority bamsGenerCodes(Set<BamsGenerCode> bamsGenerCodes) {
        this.bamsGenerCodes = bamsGenerCodes;
        return this;
    }

    public BamsAuthority addBamsGenerCode(BamsGenerCode bamsGenerCode) {
        this.bamsGenerCodes.add(bamsGenerCode);
        bamsGenerCode.getBamsAuthorities().add(this);
        return this;
    }

    public BamsAuthority removeBamsGenerCode(BamsGenerCode bamsGenerCode) {
        this.bamsGenerCodes.remove(bamsGenerCode);
        bamsGenerCode.getBamsAuthorities().remove(this);
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
        BamsAuthority bamsAuthority = (BamsAuthority) o;
        if (bamsAuthority.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, bamsAuthority.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BamsAuthority{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", activated='" + activated + "'" +
            ", createdby='" + createdby + "'" +
            ", createddate='" + createddate + "'" +
            ", lastmodifiedby='" + lastmodifiedby + "'" +
            ", lastmodifieddate='" + lastmodifieddate + "'" +
            '}';
    }
}
