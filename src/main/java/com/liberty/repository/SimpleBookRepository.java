package com.liberty.repository;

import com.liberty.model.SimpleBookEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Dimitr
 * Date: 24.04.2017
 * Time: 21:38
 */
@Repository
public interface SimpleBookRepository extends JpaRepository<SimpleBookEntity, Long> {
    List<SimpleBookEntity> findAllByDeletedFalse(Pageable pageable);
}
