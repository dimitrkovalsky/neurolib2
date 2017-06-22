package com.liberty.repository;

import com.liberty.model.SelectionBooksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by user on 15.06.2017.
 */
@Repository
public interface SelectionBookRepository extends JpaRepository<SelectionBooksEntity, Long> {

    List<SelectionBooksEntity>findAllByLivelibBookId(Long livelibBookId);

    List<SelectionBooksEntity>findAllBySelectionId(Long selectionId);

    List<SelectionBooksEntity>findAllByNeurolibId(Long neurolibId);

}

