package com.liberty.repository;

import com.liberty.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by user on 04.05.2017.
 */
public interface UserRepository extends JpaRepository<UserEntity, Long>{

    UserEntity findOneByVkontakteId(String vkontakteId);

    UserEntity findOneByFacebookId(String facebookId);

    UserEntity findOneByLinkedinId(String linkedinId);

    UserEntity findOneByTwitterId(String twitterId);

}
