package com.liberty.repository;

import com.liberty.model.SelectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by user on 15.06.2017.
 */
@Repository
public interface SelectionRepository extends JpaRepository<SelectionEntity, Long> {

}

