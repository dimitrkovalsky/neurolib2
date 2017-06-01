package com.liberty.model;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.InheritanceType.SINGLE_TABLE;

/**
 * Created by user on 01.06.2017.
 */
@Data
@MappedSuperclass
@Inheritance(strategy=SINGLE_TABLE)
public abstract class BaseUserReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "create_time")
    private Long createTime;

    @Column(name = "comment")
    private String comment;

    @Column(name = "is_deleted")
    private Boolean deleted = false;

    @Column(name = "rate")
    private Integer rate;

    public abstract Long getObjectId();

    public abstract void setObjectId(Long objectId );
}
