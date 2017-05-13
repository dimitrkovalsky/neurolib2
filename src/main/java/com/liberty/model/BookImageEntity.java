package com.liberty.model;

import lombok.Data;

import javax.persistence.*;

/**
 * User: Dimitr
 * Date: 24.04.2017
 * Time: 21:29
 */
@Entity
@Table(name = "libbpics", schema = "neurolib")
@Data
public class BookImageEntity {
    @Id
    @Column(name = "BookId")
    private Long bookId;
    @Column(name = "nid")
    private int nid;
    @Column(name = "File")
    private String file;

}
