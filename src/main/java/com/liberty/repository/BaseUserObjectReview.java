package com.liberty.repository;

import com.liberty.model.BaseUserReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * Created by user on 01.06.2017.
 */
@NoRepositoryBean
public interface BaseUserObjectReview<T extends BaseUserReviewEntity> extends JpaRepository<T, Long> {

    List<T> getAllByCommentedObjectId(Long objectId);

    List<T> findAllCommentAfter( Long objectId, Long createTime, Integer size);

    List<T> findAllCommentBefore( Long objectId, Long createTime, Integer size);
}
