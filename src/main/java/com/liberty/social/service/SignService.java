package com.liberty.social.service;

import com.liberty.model.UserEntity;
import com.liberty.model.UserLibertyProfileEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by user on 08.05.2017.
 */
@Slf4j
@Service
public class SignService {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserService userService;

    /**
     * Programmatically signs in the user with the given the user and profile.
     */
    public void signin(Connection connection) {
        //Get profile and user
        UserEntity user = userService.getUserFromConnection(connection);
        Long userId = user.getId();
        UserLibertyProfileEntity profile = userProfileService.getProfileFromUserId(userId);
        //get authority
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getAuthority().name()));
        //Set information to context
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userId, profile, authorities));
        log.info("User "+userId+" successfully logged on");
    }

    public String signup(Connection connection) {
        UserEntity userEntity = userService.createUser(connection);
        userProfileService.createProfileFromSocialConnection(connection,userEntity.getId());
        Long userId = userEntity.getId();
        log.info("User "+userId+" successfully signed in");
        return userId.toString();
    }


}
