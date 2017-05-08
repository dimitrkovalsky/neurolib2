package com.liberty.repository;

import com.liberty.model.UserLibertyProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by user on 04.05.2017.
 */
public interface UserLibertyProfileRepository extends JpaRepository<UserLibertyProfileEntity, Long> {

    UserLibertyProfileEntity findOneByUserId(Long userId);

}
