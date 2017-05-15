package com.liberty.model;

import lombok.Data;

import javax.persistence.*;

/**
 * User: Dimitr
 * Date: 24.04.2017
 * Time: 21:29
 */
@Entity
@Table(name = "libseq", schema = "neurolib")
@Data
public class BookCollectionEntity {
    @Id
    @Column(name = "BookId")
    private Long bookId;

    @Column(name = "SeqId")
    private Integer tagId;

    @Column(name = "SeqNumb")
    private Integer seqNumb;

    @Column(name = "Level")
    private byte level;

    @Column(name = "Type")
    private byte type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        BookCollectionEntity that = (BookCollectionEntity) o;

        if (bookId != null ? !bookId.equals(that.bookId) : that.bookId != null) return false;
        return tagId != null ? tagId.equals(that.tagId) : that.tagId == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (bookId != null ? bookId.hashCode() : 0);
        result = 31 * result + (tagId != null ? tagId.hashCode() : 0);
        return result;
    }
}
