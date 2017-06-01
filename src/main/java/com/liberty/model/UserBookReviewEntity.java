package com.liberty.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by user on 26.05.2017.
 */
@Entity
@Table(name = "user_book_review", schema = "neurolib")
@Data
public class UserBookReviewEntity extends BaseUserReviewEntity{

    @Column(name = "book_id")
    private Long bookId;

    @Override
    public Long getObjectId() {
        return bookId;
    }

    @Override
    public void setObjectId(Long objectId) {
        this.bookId = objectId;
    }
}
