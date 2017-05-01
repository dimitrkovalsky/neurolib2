package com.liberty.service;

import com.liberty.model.SimpleBookEntity;

import java.util.List;

/**
 * User: Dimitr
 * Date: 26.04.2017
 * Time: 21:28
 */
public interface DataMinerService {
    void computeRecommendations();

    List<Long> findRecommendations(SimpleBookEntity b);
}
