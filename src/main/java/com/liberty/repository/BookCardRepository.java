package com.liberty.repository;

import com.liberty.model.BookCardEntity;
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
public interface BookCardRepository extends JpaRepository<BookCardEntity, Long> {

    @Query(nativeQuery = true, value = "SELECT *  FROM book_card ORDER BY RAND() LIMIT :size ")
    List<BookCardEntity> findAllRandom(@Param("size") int size);

    @Query(nativeQuery = true, value = "SELECT BookId  FROM book_card LIMIT 150")
    List<Long> findAllIds();

    // TODO: merge one book with different genres to single entity
    @Query(nativeQuery = true, value = "SELECT * FROM book_card WHERE BookId IN (:ids) GROUP BY BookId")
    List<BookCardEntity> findAllByIds(@Param("ids") List<Long> ids);

    //    @Query(nativeQuery = true, value = "SELECT * FROM libbook WHERE BookId IN ( SELECT BookId FROM neurolib.libavtor WHERE AvtorId = :id)")
    List<BookCardEntity> findAllByAuthorId(Integer authorId);

//    @Query(nativeQuery = true, value = "SELECT * FROM libbook WHERE BookId IN ( SELECT BookId FROM neurolib.libavtor WHERE AvtorId = :id) ORDER BY RAND() LIMIT :size")
//    List<BookCardEntity> findAllByAuthor(@Param("id") Integer authorId, @Param("size") Integer size);
//
    @Query(nativeQuery = true, value = "SELECT * FROM book_card WHERE GenreId = :genreId LIMIT :size ")
    List<BookCardEntity> findAllByGenre(@Param("genreId") Integer genreId, @Param("size") Integer size);

}
