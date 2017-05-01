package com.liberty.repository;

import com.liberty.model.FullBookEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Dimitr
 * Date: 24.04.2017
 * Time: 21:38
 */
@Repository
@RepositoryRestResource
public interface BookRepository extends JpaRepository<FullBookEntity, Long> {

    List<FullBookEntity> findAllByAuthorIdOrderByRate(Long authorId, Pageable pageable);

    List<FullBookEntity> findAllByTagIdOrderByRate(Integer tagId, Pageable pageable);

    List<FullBookEntity> findAllByGenreIdOrderByRate(Integer genreId, Pageable pageable);

    List<FullBookEntity> findAllByAuthorIdAndGenreIdAndTagIdOrderByRate(Long authorId, Integer genreId, Integer tagId, Pageable pageable);
}
