package com.liberty.repository;

import com.liberty.model.AuthorBiography;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User: Dimitr
 * Date: 15.05.2017
 * Time: 21:38
 */
@Repository
public interface AuthorBiographyRepository extends JpaRepository<AuthorBiography, Integer> {

}
