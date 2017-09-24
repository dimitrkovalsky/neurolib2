package com.liberty.repository;

import com.liberty.model.SelectionBooksEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by user on 15.06.2017.
 */
@Repository
public interface SelectionBookRepository extends JpaRepository<SelectionBooksEntity, Long> {

    List<SelectionBooksEntity>findAllByLivelibBookId(Long livelibBookId);

    Page<SelectionBooksEntity> findAllBySelectionIdAndNeurolibBookIdIsNotNull(Long selectionId, Pageable pageable);

    List<SelectionBooksEntity>findAllByNeurolibBookId(Long neurolibId);

}

