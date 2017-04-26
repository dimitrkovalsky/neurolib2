package com.liberty.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * User: Dimitr
 * Date: 24.04.2017
 * Time: 21:29
 */
public class LibavtorEntityPK implements Serializable {
    private int bookId;
    private int avtorId;

    @Column(name = "BookId")
    @Id
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Column(name = "AvtorId")
    @Id
    public int getAvtorId() {
        return avtorId;
    }

    public void setAvtorId(int avtorId) {
        this.avtorId = avtorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LibavtorEntityPK that = (LibavtorEntityPK) o;

        if (bookId != that.bookId) return false;
        if (avtorId != that.avtorId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bookId;
        result = 31 * result + avtorId;
        return result;
    }
}
