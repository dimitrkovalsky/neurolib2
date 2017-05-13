package com.liberty.repository;

import com.liberty.model.BookImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User: Dimitr
 * Date: 24.04.2017
 * Time: 21:38
 */
@Repository
public interface BookImageRepository extends JpaRepository<BookImageEntity, Long> {

}
