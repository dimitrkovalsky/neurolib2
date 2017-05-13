package com.liberty.repository;

import com.liberty.model.SimpleBookEntity;
import org.springframework.data.domain.Pageable;
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
public interface SimpleBookRepository extends JpaRepository<SimpleBookEntity, Long> {
    List<SimpleBookEntity> findAllByDeletedFalse(Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT *  FROM libbook ORDER BY RAND() LIMIT 10")
    List<SimpleBookEntity> findAllRandom();

    @Query(nativeQuery = true, value = "SELECT * FROM libbook WHERE BookId IN ( SELECT BookId FROM neurolib.libavtor WHERE AvtorId = :id)")
    List<SimpleBookEntity> findAllByAuthor(@Param("id") Long authorId);

    @Query(nativeQuery = true, value = "SELECT * FROM libbook WHERE BookId IN (SELECT BookId FROM libgenre WHERE GenreId = :genreId) LIMIT :size")
    List<SimpleBookEntity> findAllByGenre(@Param("genreId") Integer genreId, @Param("size") Integer size);
}
