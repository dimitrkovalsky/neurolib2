package com.liberty.repository;

import com.liberty.model.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM libgenrelist WHERE GenreId IN (SELECT GenreId FROM libgenre WHERE BookId = :id)")
    List<GenreEntity> getAllGenres(@Param("id") Long bookId);

    @Query(nativeQuery = true, value = "SELECT * FROM libgenrelist WHERE GenreId IN (SELECT GenreId FROM libgenre WHERE BookId IN(:ids))")
    List<GenreEntity> getAllGenres(@Param("ids") List<Long> bookIds);
}
