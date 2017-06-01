package com.liberty.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by user on 26.05.2017.
 */
@Entity
@Table(name = "user_author_review", schema = "neurolib")
@Data
public class UserAuthorReviewEntity extends BaseUserReviewEntity {

    @Column(name = "author_id")
    private Long authorId;

    @Override
    public Long getObjectId() {
        return this.authorId;
    }

    @Override
    public void setObjectId(Long objectId) {
        this.authorId = objectId;
    }

}
