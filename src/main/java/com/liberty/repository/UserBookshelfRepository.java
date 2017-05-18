package com.liberty.repository;

import com.liberty.model.UserBookshelfEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by user on 18.05.2017.
 */

    @Repository
    public interface UserBookshelfRepository extends JpaRepository<UserBookshelfEntity, Long> {

        UserBookshelfEntity getOneByBookIdAndUserId(Long bookId,Long userId);

        List<UserBookshelfEntity> getAllByUserId(Long userId);
    }

