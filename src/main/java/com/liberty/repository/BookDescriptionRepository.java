package com.liberty.repository;

import com.liberty.model.BookDescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookDescriptionRepository extends JpaRepository<BookDescriptionEntity, Long> {

}
