package com.liberty.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * User: Dimitr
 * Date: 24.04.2017
 * Time: 21:29
 */
@Entity(name = "libreviews")
@Table(name = "libreviews", schema = "neurolib")
@Data
@IdClass(FlibustaCommentEntity.CommentPK.class)
public class FlibustaCommentEntity {
    @Id
    @Column(name = "BookId")
    private Long bookId;
    @Id
    @Column(name = "Name")
    private String name;
    @Id
    @Column(name = "Time")
    private Timestamp time;


    @Column(name = "Text")
    private String text;

    @Data
    public static class CommentPK implements Serializable {
        private Long bookId;
        private String name;
        private Timestamp time;
    }
}
