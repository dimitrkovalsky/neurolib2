package com.liberty.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * User: Dimitr
 * Date: 24.04.2017
 * Time: 21:29
 */
public class LibgenrelistEntityPK implements Serializable {
    private int genreId;
    private String genreCode;

    @Column(name = "GenreId")
    @Id
    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    @Column(name = "GenreCode")
    @Id
    public String getGenreCode() {
        return genreCode;
    }

    public void setGenreCode(String genreCode) {
        this.genreCode = genreCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LibgenrelistEntityPK that = (LibgenrelistEntityPK) o;

        if (genreId != that.genreId) return false;
        if (genreCode != null ? !genreCode.equals(that.genreCode) : that.genreCode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = genreId;
        result = 31 * result + (genreCode != null ? genreCode.hashCode() : 0);
        return result;
    }
}
