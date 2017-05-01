package com.liberty.repository;

import com.liberty.model.RecommendationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

/**
 * User: Dimitr
 * Date: 24.04.2017
 * Time: 21:38
 */
@Repository
@RepositoryRestResource
public interface RecommendationRepository extends JpaRepository<RecommendationEntity, Long> {
}
