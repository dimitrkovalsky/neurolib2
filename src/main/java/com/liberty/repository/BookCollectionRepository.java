package com.liberty.repository;

import com.liberty.model.BookCollectionEntity;
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
public interface BookCollectionRepository extends JpaRepository<BookCollectionEntity, Long> {

    List<BookCollectionEntity> findAllByBookId(Long bookId);

    @Query(nativeQuery = true, value = "SELECT * FROM libseq WHERE BookId IN (SELECT BookId FROM libavtor WHERE AvtorId= :authorId)")
    List<BookCollectionEntity> findAllByAuthor(@Param("authorId") Long authorId);
}
