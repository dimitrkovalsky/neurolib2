package com.liberty.model;

import javax.persistence.*;

/**
 * User: Dimitr
 * Date: 24.04.2017
 * Time: 21:29
 */
@Entity
@Table(name = "libgenrelist", schema = "neurolib")
@IdClass(LibgenrelistEntityPK.class)
public class LibgenrelistEntity {
    private int genreId;
    private String genreCode;
    private String genreDesc;
    private String genreMeta;

    @Id
    @Column(name = "GenreId")
    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    @Id
    @Column(name = "GenreCode")
    public String getGenreCode() {
        return genreCode;
    }

    public void setGenreCode(String genreCode) {
        this.genreCode = genreCode;
    }

    @Basic
    @Column(name = "GenreDesc")
    public String getGenreDesc() {
        return genreDesc;
    }

    public void setGenreDesc(String genreDesc) {
        this.genreDesc = genreDesc;
    }

    @Basic
    @Column(name = "GenreMeta")
    public String getGenreMeta() {
        return genreMeta;
    }

    public void setGenreMeta(String genreMeta) {
        this.genreMeta = genreMeta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LibgenrelistEntity that = (LibgenrelistEntity) o;

        if (genreId != that.genreId) return false;
        if (genreCode != null ? !genreCode.equals(that.genreCode) : that.genreCode != null) return false;
        if (genreDesc != null ? !genreDesc.equals(that.genreDesc) : that.genreDesc != null) return false;
        if (genreMeta != null ? !genreMeta.equals(that.genreMeta) : that.genreMeta != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = genreId;
        result = 31 * result + (genreCode != null ? genreCode.hashCode() : 0);
        result = 31 * result + (genreDesc != null ? genreDesc.hashCode() : 0);
        result = 31 * result + (genreMeta != null ? genreMeta.hashCode() : 0);
        return result;
    }
}
