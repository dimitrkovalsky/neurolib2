package com.liberty.repository;

import com.liberty.model.AuthorRecommendationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AuthorRecommendationRepository extends JpaRepository<AuthorRecommendationEntity, Long> {

    List<AuthorRecommendationEntity> findAllByAuthorId(long authorId);
}
