package com.liberty.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User: Dimitr
 * Date: 24.04.2017
 * Time: 21:29
 */
@Entity(name = "full_book" )
@Table(name = "full_book", schema = "neurolib")
@Data
public class FullBookEntity {
    @Id
    @Column(name = "BookId")
    private Long bookId;

    @Column(name = "AuthorId")
    private Long authorId;

    @Column(name = "GenreId")
    private Integer genreId;

    @Column(name = "TagId")
    private Integer tagId;

    @Column(name = "Lang")
    private String lang;

    @Column(name = "Deleted")
    private Boolean deleted;

    @Column(name = "Pages")
    private Integer pages;

    @Column(name = "Chars")
    private Integer chars;

    @Column(name = "Rate")
    private Double rate;
}
