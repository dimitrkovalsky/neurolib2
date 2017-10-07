package com.liberty.model;

import lombok.Data;

import javax.persistence.*;

/**
 * User: Dimitr
 * Date: 24.04.2017
 * Time: 21:29
 */
@Entity(name = "book_card")
@Table(name = "book_card", schema = "neurolib")
@Data
public class BookCardEntity {
    @Id
    @Column(name = "BookId")
    private Long bookId;

    @Column(name = "AuthorId")
    private Long authorId;

    @Column(name = "GenreId")
    private Integer genreId;

    @Column(name = "Lang")
    private String lang;

    @Column(name = "Title")
    private String title;

    @Column(name = "authorLastName")
    private String authorLastName;

    @Column(name = "authorFirstName")
    private String authorFirstName;

    @Column(name = "GenreDesc")
    private String genreDescription;

    @Column(name = "GenreMeta")
    private String genreMetadata;

    @Column(name = "Rate")
    private Double rate;

    @Transient
    private String imagePath;
}
