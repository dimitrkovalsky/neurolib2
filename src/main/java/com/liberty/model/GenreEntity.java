package com.liberty.model;

import lombok.Data;

import javax.persistence.*;

/**
 * User: Dimitr
 * Date: 24.04.2017
 * Time: 21:29
 */
@Entity(name = "libgenrelist")
@Table(name = "libgenrelist", schema = "neurolib")
@Data
public class GenreEntity {
    @Id
    @Column(name = "GenreId")
    private Integer genreId;
    @Column(name = "GenreCode")
    private String genreCode;
    @Column(name = "GenreDesc")
    private String genreDesc;
    @Column(name = "GenreMeta")
    private String genreMeta;
}
