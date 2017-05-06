package com.liberty.service;

import com.liberty.model.RecommendationEntity;
import com.liberty.model.SimpleBookEntity;

import java.util.List;

/**
 * User: Dimitr
 * Date: 26.04.2017
 * Time: 21:28
 */
public interface DataMinerService {
    void computeRecommendations();

    List<RecommendationEntity> findRecommendations(SimpleBookEntity b);

    List<RecommendationEntity> findRecommendations(Long bookId);
}
