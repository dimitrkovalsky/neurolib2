package com.liberty.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User: Dimitr
 * Date: 24.04.2017
 * Time: 21:29
 */
@Entity(name = "libbannotations")
@Table(name = "libbannotations", schema = "neurolib")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDescriptionEntity {
    @Id
    @Column(name = "BookId")
    private Long bookId;

    @Column(name = "nid")
    private Long nid;

    @Column(name = "Title")
    private String title;

    @Column(name = "Body")
    private String description;
}
