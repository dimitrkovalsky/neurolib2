package com.liberty.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * User: Dimitr
 * Date: 24.04.2017
 * Time: 21:29
 */
public class LibtranslatorEntityPK implements Serializable {
    private int bookId;
    private int translatorId;

    @Column(name = "BookId")
    @Id
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Column(name = "TranslatorId")
    @Id
    public int getTranslatorId() {
        return translatorId;
    }

    public void setTranslatorId(int translatorId) {
        this.translatorId = translatorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LibtranslatorEntityPK that = (LibtranslatorEntityPK) o;

        if (bookId != that.bookId) return false;
        if (translatorId != that.translatorId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bookId;
        result = 31 * result + translatorId;
        return result;
    }
}
