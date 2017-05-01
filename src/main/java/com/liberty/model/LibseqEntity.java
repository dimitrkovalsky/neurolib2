package com.liberty.model;

import javax.persistence.*;

/**
 * User: Dimitr
 * Date: 24.04.2017
 * Time: 21:29
 */
@Entity
@Table(name = "libseq", schema = "neurolib")
@IdClass(LibseqEntityPK.class)
public class LibseqEntity {
    private int bookId;
    private int seqId;
    private int seqNumb;
    private byte level;
    private byte type;
    
    @Id
    @Column(name = "BookId")
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Id
    @Column(name = "SeqId")
    public int getSeqId() {
        return seqId;
    }

    public void setSeqId(int seqId) {
        this.seqId = seqId;
    }

    @Basic
    @Column(name = "SeqNumb")
    public int getSeqNumb() {
        return seqNumb;
    }

    public void setSeqNumb(int seqNumb) {
        this.seqNumb = seqNumb;
    }

    @Basic
    @Column(name = "Level")
    public byte getLevel() {
        return level;
    }

    public void setLevel(byte level) {
        this.level = level;
    }

    @Basic
    @Column(name = "Type")
    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LibseqEntity that = (LibseqEntity) o;

        if (bookId != that.bookId) return false;
        if (seqId != that.seqId) return false;
        if (seqNumb != that.seqNumb) return false;
        if (level != that.level) return false;
        if (type != that.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bookId;
        result = 31 * result + seqId;
        result = 31 * result + seqNumb;
        result = 31 * result + (int) level;
        result = 31 * result + (int) type;
        return result;
    }
}
