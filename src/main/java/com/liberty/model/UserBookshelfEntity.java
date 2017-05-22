package com.liberty.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by user on 18.05.2017.
 */
@Entity(name = "user_bookshelf")
@Table(name = "user_bookshelf", schema = "neurolib")
@Data
public class UserBookshelfEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;
    @Column(name = "book_id")
    private Long bookId;
    @Column(name = "rate")
    private Integer rate;
}
