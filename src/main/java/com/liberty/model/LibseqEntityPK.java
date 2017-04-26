package com.liberty.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * User: Dimitr
 * Date: 24.04.2017
 * Time: 21:29
 */
public class LibseqEntityPK implements Serializable {
    private int bookId;
    private int seqId;

    @Column(name = "BookId")
    @Id
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Column(name = "SeqId")
    @Id
    public int getSeqId() {
        return seqId;
    }

    public void setSeqId(int seqId) {
        this.seqId = seqId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LibseqEntityPK that = (LibseqEntityPK) o;

        if (bookId != that.bookId) return false;
        if (seqId != that.seqId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bookId;
        result = 31 * result + seqId;
        return result;
    }
}
