package com.liberty.model;

import javax.persistence.*;

/**
 * User: Dimitr
 * Date: 24.04.2017
 * Time: 21:29
 */
@Entity
@Table(name = "libapics", schema = "neurolib", catalog = "")
public class LibapicsEntity {
    private int avtorId;
    private int nid;
    private String file;

    @Basic
    @Column(name = "AvtorId")
    public int getAvtorId() {
        return avtorId;
    }

    public void setAvtorId(int avtorId) {
        this.avtorId = avtorId;
    }

    @Basic
    @Column(name = "nid")
    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    @Basic
    @Column(name = "File")
    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LibapicsEntity that = (LibapicsEntity) o;

        if (avtorId != that.avtorId) return false;
        if (nid != that.nid) return false;
        if (file != null ? !file.equals(that.file) : that.file != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = avtorId;
        result = 31 * result + nid;
        result = 31 * result + (file != null ? file.hashCode() : 0);
        return result;
    }

    private String id;

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
