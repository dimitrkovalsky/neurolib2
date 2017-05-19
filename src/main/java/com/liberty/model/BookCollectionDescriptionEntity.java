package com.liberty.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Dimitr
 * Date: 24.04.2017
 * Time: 21:29
 */
@Entity(name = "libseqname")
@Table(name = "libseqname", schema = "neurolib")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCollectionDescriptionEntity {
    @Id
    @Column(name = "SeqId")
    private Integer seqId;
    @Column(name = "SeqName")
    private String seqName;
    @Transient
    private List<GenreEntity> genres = new ArrayList<>();

    public BookCollectionDescriptionEntity(Integer seqId) {
        this.seqId = seqId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        BookCollectionDescriptionEntity that = (BookCollectionDescriptionEntity) o;

        return seqId != null ? seqId.equals(that.seqId) : that.seqId == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (seqId != null ? seqId.hashCode() : 0);
        return result;
    }

    @Data
    public static class GenreLink {
        private Integer genreId;
        private Integer genreName;
    }
}
