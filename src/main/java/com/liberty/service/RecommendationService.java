package com.liberty.service;

import java.util.List;

/**
 * User: Dimitr
 * Date: 26.04.2017
 * Time: 21:28
 */
public interface RecommendationService {
    List<Long> recommend(Long bookId);

    void preProcess();
}
