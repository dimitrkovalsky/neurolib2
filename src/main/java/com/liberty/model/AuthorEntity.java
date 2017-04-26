package com.liberty.model;

import lombok.Data;

import javax.persistence.*;

/**
 * User: Dimitr
 * Date: 24.04.2017
 * Time: 21:29
 */
@Entity(name = "libavtor" )
@Table(name = "libavtor", schema = "neurolib")
@Data
public class AuthorEntity {
    @Id
    @Column(name = "BookId")
    private Long bookId;

    @Column(name = "AvtorId")
    private Long authorId;

    @Column(name = "Pos")
    private Integer position;

}
