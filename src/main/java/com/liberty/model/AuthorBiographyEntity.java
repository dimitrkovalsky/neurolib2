package com.liberty.model;

import lombok.Data;

import javax.persistence.*;

/**
 * User: Dimitr
 * Date: 24.04.2017
 * Time: 21:29
 */
@Entity(name = "libaannotations")
@Table(name = "libaannotations", schema = "neurolib")
@Data
public class AuthorBiographyEntity {
    @Id
    @Column(name = "AvtorId")
    private Integer authorId;
    @Column(name = "nid")
    private int nid;
    @Column(name = "Title")
    private String title;
    @Column(name = "Body")
    private String body;
}
