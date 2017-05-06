package com.liberty.repository;

import com.liberty.model.BookAuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Dimitr
 * Date: 24.04.2017
 * Time: 21:38
 */
@Repository
public interface BookAuthorRepository extends JpaRepository<BookAuthorEntity, Long> {
    List<BookAuthorEntity> findAllByBookId(Long bookId);
}
