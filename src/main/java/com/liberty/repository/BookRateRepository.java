package com.liberty.repository;

import com.liberty.model.BookRateEntity;
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
public interface BookRateRepository extends JpaRepository<BookRateEntity, Long> {
    @Query(nativeQuery = true, value = "SELECT rate_view.* FROM rate_view INNER JOIN libbpics ON rate_view.BookId = libbpics.BookId WHERE Rate >= :rate   ORDER BY Rate LIMIT :limit")
    List<BookRateEntity> findAllByRate(@Param("rate") int rate, @Param("limit") int limit);

    @Query(nativeQuery = true, value = "SELECT  * FROM rate_view INNER JOIN libgenre ON libgenre.GenreId=rate_view.GenreId INNER JOIN libbpics ON rate_view.BookId = libbpics.BookId WHERE libgenre.GenreId = :genreId ORDER BY Rate LIMIT :limit")
    List<BookRateEntity> findAllByGenreId(@Param("genreId") int genreId, @Param("limit") int limit);
}
