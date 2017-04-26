package com.liberty.model;

import javax.persistence.*;

/**
 * User: Dimitr
 * Date: 24.04.2017
 * Time: 21:29
 */
@Entity
@Table(name = "libtranslator", schema = "neurolib")
@IdClass(LibtranslatorEntityPK.class)
public class LibtranslatorEntity {
    private int bookId;
    private int translatorId;
    private byte pos;

    @Id
    @Column(name = "BookId")
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Id
    @Column(name = "TranslatorId")
    public int getTranslatorId() {
        return translatorId;
    }

    public void setTranslatorId(int translatorId) {
        this.translatorId = translatorId;
    }

    @Basic
    @Column(name = "Pos")
    public byte getPos() {
        return pos;
    }

    public void setPos(byte pos) {
        this.pos = pos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LibtranslatorEntity that = (LibtranslatorEntity) o;

        if (bookId != that.bookId) return false;
        if (translatorId != that.translatorId) return false;
        if (pos != that.pos) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bookId;
        result = 31 * result + translatorId;
        result = 31 * result + (int) pos;
        return result;
    }
}
