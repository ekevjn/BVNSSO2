package com.baoviet.login.domain;

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
 * A BamsGenerCode.
 */
@Entity
@Table(name = "bams_generic_code")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "bamsgenercode")
public class BamsGenerCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 0, max = 50)
    @Column(name = "code", length = 50, nullable = false)
    private String code;

    @Size(max = 150)
    @Column(name = "name", length = 150)
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

    @Column(name = "version_id")
    private Long versionId;

    @ManyToOne
    private BamsGenerType bamsGenerType;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "bams_gener_code_bams_user",
               joinColumns = @JoinColumn(name="bams_gener_codes_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="bams_users_id", referencedColumnName="id"))
    private Set<BamsUser> bamsUsers = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "bams_gener_code_bams_authority",
               joinColumns = @JoinColumn(name="bams_gener_codes_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="bams_authorities_id", referencedColumnName="id"))
    private Set<BamsAuthority> bamsAuthorities = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public BamsGenerCode code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public BamsGenerCode name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public BamsGenerCode description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isActivated() {
        return activated;
    }

    public BamsGenerCode activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getCreatedby() {
        return createdby;
    }

    public BamsGenerCode createdby(String createdby) {
        this.createdby = createdby;
        return this;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public LocalDate getCreateddate() {
        return createddate;
    }

    public BamsGenerCode createddate(LocalDate createddate) {
        this.createddate = createddate;
        return this;
    }

    public void setCreateddate(LocalDate createddate) {
        this.createddate = createddate;
    }

    public String getLastmodifiedby() {
        return lastmodifiedby;
    }

    public BamsGenerCode lastmodifiedby(String lastmodifiedby) {
        this.lastmodifiedby = lastmodifiedby;
        return this;
    }

    public void setLastmodifiedby(String lastmodifiedby) {
        this.lastmodifiedby = lastmodifiedby;
    }

    public LocalDate getLastmodifieddate() {
        return lastmodifieddate;
    }

    public BamsGenerCode lastmodifieddate(LocalDate lastmodifieddate) {
        this.lastmodifieddate = lastmodifieddate;
        return this;
    }

    public void setLastmodifieddate(LocalDate lastmodifieddate) {
        this.lastmodifieddate = lastmodifieddate;
    }

    public Long getVersionId() {
        return versionId;
    }

    public BamsGenerCode versionId(Long versionId) {
        this.versionId = versionId;
        return this;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    public BamsGenerType getBamsGenerType() {
        return bamsGenerType;
    }

    public BamsGenerCode bamsGenerType(BamsGenerType bamsGenerType) {
        this.bamsGenerType = bamsGenerType;
        return this;
    }

    public void setBamsGenerType(BamsGenerType bamsGenerType) {
        this.bamsGenerType = bamsGenerType;
    }

    public Set<BamsUser> getBamsUsers() {
        return bamsUsers;
    }

    public BamsGenerCode bamsUsers(Set<BamsUser> bamsUsers) {
        this.bamsUsers = bamsUsers;
        return this;
    }

    public BamsGenerCode addBamsUser(BamsUser bamsUser) {
        this.bamsUsers.add(bamsUser);
        bamsUser.getBamsGenerCodes().add(this);
        return this;
    }

    public BamsGenerCode removeBamsUser(BamsUser bamsUser) {
        this.bamsUsers.remove(bamsUser);
        bamsUser.getBamsGenerCodes().remove(this);
        return this;
    }

    public void setBamsUsers(Set<BamsUser> bamsUsers) {
        this.bamsUsers = bamsUsers;
    }

    public Set<BamsAuthority> getBamsAuthorities() {
        return bamsAuthorities;
    }

    public BamsGenerCode bamsAuthorities(Set<BamsAuthority> bamsAuthorities) {
        this.bamsAuthorities = bamsAuthorities;
        return this;
    }

    public BamsGenerCode addBamsAuthority(BamsAuthority bamsAuthority) {
        this.bamsAuthorities.add(bamsAuthority);
        bamsAuthority.getBamsGenerCodes().add(this);
        return this;
    }

    public BamsGenerCode removeBamsAuthority(BamsAuthority bamsAuthority) {
        this.bamsAuthorities.remove(bamsAuthority);
        bamsAuthority.getBamsGenerCodes().remove(this);
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
        BamsGenerCode bamsGenerCode = (BamsGenerCode) o;
        if (bamsGenerCode.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, bamsGenerCode.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BamsGenerCode{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", activated='" + activated + "'" +
            ", createdby='" + createdby + "'" +
            ", createddate='" + createddate + "'" +
            ", lastmodifiedby='" + lastmodifiedby + "'" +
            ", lastmodifieddate='" + lastmodifieddate + "'" +
            ", versionId='" + versionId + "'" +
            '}';
    }
}
