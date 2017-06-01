package com.liberty.repository;

import com.liberty.model.UserAuthorReviewEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by user on 18.05.2017.
 */

@Repository
public interface UserAuthorReviewRepository extends BaseUserObjectReview<UserAuthorReviewEntity> {
    @Query(nativeQuery = true, value = "SELECT * FROM neurolib.user_author_review WHERE author_id=:objectId AND is_deleted = FALSE ")
    List<UserAuthorReviewEntity> getAllByCommentedObjectId(Long objectId);

    @Query(nativeQuery = true, value = "SELECT * FROM neurolib.user_author_review WHERE author_id=:authorId AND is_deleted = FALSE AND create_time >:createTime LIMIT :size ")
    List<UserAuthorReviewEntity> findAllCommentAfter(@Param("authorId") Long authorId, @Param("createTime") Long createTime, @Param("size") Integer size);

    @Query(nativeQuery = true, value = "SELECT * FROM neurolib.user_author_review WHERE author_id=:authorId AND is_deleted = FALSE AND create_time <:createTime :size ")
    List<UserAuthorReviewEntity> findAllCommentBefore(@Param("authorId") Long authorId, @Param("createTime") Long createTime, @Param("size") Integer size);
}

