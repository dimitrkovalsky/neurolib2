package com.liberty.repository;

import com.liberty.model.QuoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Dimitr
 * Date: 24.04.2017
 * Time: 21:38
 */
@Repository
public interface QuoteRepository extends JpaRepository<QuoteEntity, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM quote WHERE flibusta_author_id IS NOT NULL ORDER BY rand() LIMIT :limit")
    List<QuoteEntity> getRandomQuotes(@Param("limit") int limit);
}
