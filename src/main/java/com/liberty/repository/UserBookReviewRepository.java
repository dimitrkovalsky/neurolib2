package com.liberty.repository;

import com.liberty.model.UserBookReviewEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by user on 18.05.2017.
 */

@Repository
public interface UserBookReviewRepository extends BaseUserObjectReview<UserBookReviewEntity> {

    @Query(nativeQuery = true, value = "SELECT * FROM neurolib.user_book_review WHERE book_id=:bookId AND is_deleted = FALSE")
    List<UserBookReviewEntity> getAllByCommentedObjectId(Long bookId);

    @Query(nativeQuery = true, value = "SELECT * FROM neurolib.user_book_review WHERE book_id=:bookId AND is_deleted = FALSE AND create_time >:createTime LIMIT :size ")
    List<UserBookReviewEntity> findAllCommentAfter(@Param("bookId") Long bookId, @Param("createTime") Long createTime, @Param("size") Integer size);

    @Query(nativeQuery = true, value = "SELECT * FROM neurolib.user_book_review WHERE book_id=:bookId AND is_deleted = FALSE AND create_time <:createTime :size ")
    List<UserBookReviewEntity> findAllCommentBefore(@Param("bookId") Long bookId, @Param("createTime") Long createTime, @Param("size") Integer size);
}

