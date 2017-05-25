package com.liberty.repository;

import com.liberty.model.AuthorEntity;
import org.springframework.data.domain.Page;
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
public interface AuthorRepository extends JpaRepository<AuthorEntity, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM libavtorname ORDER BY RAND() LIMIT :size")
    List<AuthorEntity> getRandomAuthors(@Param("size") int size);

    Page<AuthorEntity> getAllByFirstNameOrMiddleNameOrLastNameContainingOrderByLastName(Pageable pageable, String firstName, String middleName, String lastName);
}
