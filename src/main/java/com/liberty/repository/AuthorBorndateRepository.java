package com.liberty.repository;


import com.liberty.model.AuthorBorndateEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Dimitr
 * Date: 24.04.2017
 * Time: 21:38
 */
@Repository
public interface AuthorBorndateRepository extends JpaRepository<AuthorBorndateEntity, Long> {

    List<AuthorBorndateEntity> findAllByNeurolibAuthorIdIsNull();

    Page<AuthorBorndateEntity> findAllByBornMonthEqualsAndBornDayEqualsAndNeurolibAuthorIdNotNull(Integer bornMonth,Integer bornDay, Pageable pageable);

}
