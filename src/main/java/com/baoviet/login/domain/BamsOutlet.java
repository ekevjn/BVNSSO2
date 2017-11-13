package com.baoviet.login.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A BamsOutlet.
 */
@Entity
@Table(name = "bams_outlet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "bamsoutlet")
public class BamsOutlet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 30)
    @Column(name = "daily_id", length = 30)
    private String dailyId;

    @Size(max = 1000)
    @Column(name = "tendaily", length = 1000)
    private String tendaily;

    @Size(max = 500)
    @Column(name = "cvdl", length = 500)
    private String cvdl;

    @Size(max = 500)
    @Column(name = "mota_cvdl", length = 500)
    private String motaCvdl;

    @Size(max = 1000)
    @Column(name = "diachi", length = 1000)
    private String diachi;

    @Size(max = 1000)
    @Column(name = "dienthoai", length = 1000)
    private String dienthoai;

    @Size(max = 30)
    @Column(name = "matinh", length = 30)
    private String matinh;

    @Column(name = "activated")
    private Boolean activated;

    @Size(max = 500)
    @Column(name = "vphd", length = 500)
    private String vphd;

    @Size(max = 500)
    @Column(name = "diachivphd", length = 500)
    private String diachivphd;

    @Size(max = 500)
    @Column(name = "maphongban", length = 500)
    private String maphongban;

    @Size(max = 500)
    @Column(name = "tenphongban", length = 500)
    private String tenphongban;

    @Size(max = 500)
    @Column(name = "manhom", length = 500)
    private String manhom;

    @Size(max = 500)
    @Column(name = "tennhom", length = 500)
    private String tennhom;

    @OneToMany(mappedBy = "bamsOutlet")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BamsOutletPro> bamsOutletPros = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDailyId() {
        return dailyId;
    }

    public BamsOutlet dailyId(String dailyId) {
        this.dailyId = dailyId;
        return this;
    }

    public void setDailyId(String dailyId) {
        this.dailyId = dailyId;
    }

    public String getTendaily() {
        return tendaily;
    }

    public BamsOutlet tendaily(String tendaily) {
        this.tendaily = tendaily;
        return this;
    }

    public void setTendaily(String tendaily) {
        this.tendaily = tendaily;
    }

    public String getCvdl() {
        return cvdl;
    }

    public BamsOutlet cvdl(String cvdl) {
        this.cvdl = cvdl;
        return this;
    }

    public void setCvdl(String cvdl) {
        this.cvdl = cvdl;
    }

    public String getMotaCvdl() {
        return motaCvdl;
    }

    public BamsOutlet motaCvdl(String motaCvdl) {
        this.motaCvdl = motaCvdl;
        return this;
    }

    public void setMotaCvdl(String motaCvdl) {
        this.motaCvdl = motaCvdl;
    }

    public String getDiachi() {
        return diachi;
    }

    public BamsOutlet diachi(String diachi) {
        this.diachi = diachi;
        return this;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getDienthoai() {
        return dienthoai;
    }

    public BamsOutlet dienthoai(String dienthoai) {
        this.dienthoai = dienthoai;
        return this;
    }

    public void setDienthoai(String dienthoai) {
        this.dienthoai = dienthoai;
    }

    public String getMatinh() {
        return matinh;
    }

    public BamsOutlet matinh(String matinh) {
        this.matinh = matinh;
        return this;
    }

    public void setMatinh(String matinh) {
        this.matinh = matinh;
    }

    public Boolean isActivated() {
        return activated;
    }

    public BamsOutlet activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getVphd() {
        return vphd;
    }

    public BamsOutlet vphd(String vphd) {
        this.vphd = vphd;
        return this;
    }

    public void setVphd(String vphd) {
        this.vphd = vphd;
    }

    public String getDiachivphd() {
        return diachivphd;
    }

    public BamsOutlet diachivphd(String diachivphd) {
        this.diachivphd = diachivphd;
        return this;
    }

    public void setDiachivphd(String diachivphd) {
        this.diachivphd = diachivphd;
    }

    public String getMaphongban() {
        return maphongban;
    }

    public BamsOutlet maphongban(String maphongban) {
        this.maphongban = maphongban;
        return this;
    }

    public void setMaphongban(String maphongban) {
        this.maphongban = maphongban;
    }

    public String getTenphongban() {
        return tenphongban;
    }

    public BamsOutlet tenphongban(String tenphongban) {
        this.tenphongban = tenphongban;
        return this;
    }

    public void setTenphongban(String tenphongban) {
        this.tenphongban = tenphongban;
    }

    public String getManhom() {
        return manhom;
    }

    public BamsOutlet manhom(String manhom) {
        this.manhom = manhom;
        return this;
    }

    public void setManhom(String manhom) {
        this.manhom = manhom;
    }

    public String getTennhom() {
        return tennhom;
    }

    public BamsOutlet tennhom(String tennhom) {
        this.tennhom = tennhom;
        return this;
    }

    public void setTennhom(String tennhom) {
        this.tennhom = tennhom;
    }

    public Set<BamsOutletPro> getBamsOutletPros() {
        return bamsOutletPros;
    }

    public BamsOutlet bamsOutletPros(Set<BamsOutletPro> bamsOutletPros) {
        this.bamsOutletPros = bamsOutletPros;
        return this;
    }

    public BamsOutlet addBamsOutletPro(BamsOutletPro bamsOutletPro) {
        this.bamsOutletPros.add(bamsOutletPro);
        bamsOutletPro.setBamsOutlet(this);
        return this;
    }

    public BamsOutlet removeBamsOutletPro(BamsOutletPro bamsOutletPro) {
        this.bamsOutletPros.remove(bamsOutletPro);
        bamsOutletPro.setBamsOutlet(null);
        return this;
    }

    public void setBamsOutletPros(Set<BamsOutletPro> bamsOutletPros) {
        this.bamsOutletPros = bamsOutletPros;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BamsOutlet bamsOutlet = (BamsOutlet) o;
        if (bamsOutlet.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, bamsOutlet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BamsOutlet{" +
            "id=" + id +
            ", dailyId='" + dailyId + "'" +
            ", tendaily='" + tendaily + "'" +
            ", cvdl='" + cvdl + "'" +
            ", motaCvdl='" + motaCvdl + "'" +
            ", diachi='" + diachi + "'" +
            ", dienthoai='" + dienthoai + "'" +
            ", matinh='" + matinh + "'" +
            ", activated='" + activated + "'" +
            ", vphd='" + vphd + "'" +
            ", diachivphd='" + diachivphd + "'" +
            ", maphongban='" + maphongban + "'" +
            ", tenphongban='" + tenphongban + "'" +
            ", manhom='" + manhom + "'" +
            ", tennhom='" + tennhom + "'" +
            '}';
    }
}
