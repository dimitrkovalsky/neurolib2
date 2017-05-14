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
public class TagEntity {
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
}
