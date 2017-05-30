package com.liberty.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User: Dimitr
 * Date: 30.05.2017
 * Time: 21:29
 */
@Entity(name = "author_recommendation")
@Table(name = "author_recommendation", schema = "neurolib")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(AuthorRecommendationEntity.RecoPK.class)
public class AuthorRecommendationEntity {
    @Id
    @Column(name = "author_id")
    private Integer authorId;

    @Id
    @Column(name = "similar_id")
    private Integer similarId;

    @Data
    public static class RecoPK implements Serializable {
        private Integer authorId;
        private Integer similarId;
    }
}
