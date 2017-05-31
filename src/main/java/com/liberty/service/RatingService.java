package com.liberty.service;

import com.liberty.model.SimpleBookEntity;

import java.util.List;

/**
 * User: Dimitr
 * Date: 31.05.2017
 * Time: 7:55
 */
public interface RatingService {
    List<SimpleBookEntity> getMostReadable();
}
