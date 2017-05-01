package com.liberty.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User: Dimitr
 * Date: 24.04.2017
 * Time: 21:29
 */
@Entity(name = "recommendation")
@Table(name = "recommendation", schema = "neurolib")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(RecommendationEntity.RecoPK.class)
public class RecommendationEntity {
    @Id
    @Column(name = "BookId")
    private Long bookId;

    @Id
    @Column(name = "RecommendationId")
    private Long RecommendationId;

    @Data
    public static class RecoPK implements Serializable {
        private Long bookId;
        private Long RecommendationId;
    }
}
