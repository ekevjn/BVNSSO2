package com.baoviet.login.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A BamsCompany.
 */
@Entity
@Table(name = "bams_company")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "bamscompany")
public class BamsCompany implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "matinh", length = 10, nullable = false)
    private String matinh;

    @Size(max = 500)
    @Column(name = "tentinh", length = 500)
    private String tentinh;

    @Size(max = 500)
    @Column(name = "diachi", length = 500)
    private String diachi;

    @Size(max = 200)
    @Column(name = "dienthoai", length = 200)
    private String dienthoai;

    @Size(max = 200)
    @Column(name = "fax", length = 200)
    private String fax;

    @Size(max = 500)
    @Column(name = "tentinh_eng", length = 500)
    private String tentinhEng;

    @Size(max = 500)
    @Column(name = "diachi_eng", length = 500)
    private String diachiEng;

    @Size(max = 200)
    @Column(name = "dienthoai_eng", length = 200)
    private String dienthoaiEng;

    @Size(max = 20)
    @Column(name = "fax_eng", length = 20)
    private String faxEng;

    @Size(max = 64)
    @Column(name = "comp_id", length = 64)
    private String compId;

    @Column(name = "sequenced")
    private Long sequenced;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatinh() {
        return matinh;
    }

    public BamsCompany matinh(String matinh) {
        this.matinh = matinh;
        return this;
    }

    public void setMatinh(String matinh) {
        this.matinh = matinh;
    }

    public String getTentinh() {
        return tentinh;
    }

    public BamsCompany tentinh(String tentinh) {
        this.tentinh = tentinh;
        return this;
    }

    public void setTentinh(String tentinh) {
        this.tentinh = tentinh;
    }

    public String getDiachi() {
        return diachi;
    }

    public BamsCompany diachi(String diachi) {
        this.diachi = diachi;
        return this;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getDienthoai() {
        return dienthoai;
    }

    public BamsCompany dienthoai(String dienthoai) {
        this.dienthoai = dienthoai;
        return this;
    }

    public void setDienthoai(String dienthoai) {
        this.dienthoai = dienthoai;
    }

    public String getFax() {
        return fax;
    }

    public BamsCompany fax(String fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getTentinhEng() {
        return tentinhEng;
    }

    public BamsCompany tentinhEng(String tentinhEng) {
        this.tentinhEng = tentinhEng;
        return this;
    }

    public void setTentinhEng(String tentinhEng) {
        this.tentinhEng = tentinhEng;
    }

    public String getDiachiEng() {
        return diachiEng;
    }

    public BamsCompany diachiEng(String diachiEng) {
        this.diachiEng = diachiEng;
        return this;
    }

    public void setDiachiEng(String diachiEng) {
        this.diachiEng = diachiEng;
    }

    public String getDienthoaiEng() {
        return dienthoaiEng;
    }

    public BamsCompany dienthoaiEng(String dienthoaiEng) {
        this.dienthoaiEng = dienthoaiEng;
        return this;
    }

    public void setDienthoaiEng(String dienthoaiEng) {
        this.dienthoaiEng = dienthoaiEng;
    }

    public String getFaxEng() {
        return faxEng;
    }

    public BamsCompany faxEng(String faxEng) {
        this.faxEng = faxEng;
        return this;
    }

    public void setFaxEng(String faxEng) {
        this.faxEng = faxEng;
    }

    public String getCompId() {
        return compId;
    }

    public BamsCompany compId(String compId) {
        this.compId = compId;
        return this;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    public Long getSequenced() {
        return sequenced;
    }

    public BamsCompany sequenced(Long sequenced) {
        this.sequenced = sequenced;
        return this;
    }

    public void setSequenced(Long sequenced) {
        this.sequenced = sequenced;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BamsCompany bamsCompany = (BamsCompany) o;
        if (bamsCompany.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, bamsCompany.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BamsCompany{" +
            "id=" + id +
            ", matinh='" + matinh + "'" +
            ", tentinh='" + tentinh + "'" +
            ", diachi='" + diachi + "'" +
            ", dienthoai='" + dienthoai + "'" +
            ", fax='" + fax + "'" +
            ", tentinhEng='" + tentinhEng + "'" +
            ", diachiEng='" + diachiEng + "'" +
            ", dienthoaiEng='" + dienthoaiEng + "'" +
            ", faxEng='" + faxEng + "'" +
            ", compId='" + compId + "'" +
            ", sequenced='" + sequenced + "'" +
            '}';
    }
}
