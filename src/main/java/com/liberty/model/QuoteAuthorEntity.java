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
 * Date: 02.06.2017
 * Time: 7:38
 */
@Data
@AllArgsConstructor
@Entity
@Table(name = "quote_author", schema = "neurolib")
@NoArgsConstructor
public class QuoteAuthorEntity {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String authorName;
    
    @Column(name = "bio")
    private String bio;

    @Column(name = "flibusta_id")
    private Integer flibustaId;
}
