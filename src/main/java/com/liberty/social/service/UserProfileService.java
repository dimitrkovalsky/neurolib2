package com.liberty.social.service;

import com.liberty.model.UserLibertyProfileEntity;
import com.liberty.repository.UserLibertyProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by user on 08.05.2017.
 */
@Service
public class UserProfileService {

    @Autowired
    private UserLibertyProfileRepository userProfileRepository;

    public UserLibertyProfileEntity createProfileFromSocialConnection(Connection connection, Long userId){
        UserProfile userProfile = connection.fetchUserProfile();

        UserLibertyProfileEntity userLibertyProfileEntity = new UserLibertyProfileEntity();

        userLibertyProfileEntity.setAvatar(connection.getImageUrl());
        userLibertyProfileEntity.setChangeTime(new Date().getTime());
        userLibertyProfileEntity.setEmail(userProfile.getEmail());
        userLibertyProfileEntity.setUserId(userId);
        userLibertyProfileEntity.setFirstName(userProfile.getFirstName());
        userLibertyProfileEntity.setLastName(userProfile.getLastName());
        userLibertyProfileEntity = userProfileRepository.save(userLibertyProfileEntity);
        return userLibertyProfileEntity;
    }

    public UserLibertyProfileEntity getProfileFromUserId(Long userId){
        return userProfileRepository.findOneByUserId(userId);
    }
}
