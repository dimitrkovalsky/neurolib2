package com.liberty.model;

import javax.persistence.*;

/**
 * User: Dimitr
 * Date: 24.04.2017
 * Time: 21:29
 */
@Entity
@Table(name = "libseqname", schema = "neurolib")
public class LibseqnameEntity {
    private int seqId;
    private String seqName;

    @Id
    @Column(name = "SeqId")
    public int getSeqId() {
        return seqId;
    }

    public void setSeqId(int seqId) {
        this.seqId = seqId;
    }

    @Basic
    @Column(name = "SeqName")
    public String getSeqName() {
        return seqName;
    }

    public void setSeqName(String seqName) {
        this.seqName = seqName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LibseqnameEntity that = (LibseqnameEntity) o;

        if (seqId != that.seqId) return false;
        if (seqName != null ? !seqName.equals(that.seqName) : that.seqName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = seqId;
        result = 31 * result + (seqName != null ? seqName.hashCode() : 0);
        return result;
    }
}
