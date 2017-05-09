package com.liberty.social.sign;

import com.liberty.model.UserEntity;
import com.liberty.social.service.SignService;
import com.liberty.social.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Service;

/**
 * Created by user on 08.05.2017.
 */
@Slf4j
@Service
public class ImplicitConnectionSignup implements ConnectionSignUp {

    @Autowired
    private SignService signService;

    @Autowired
    private UserService userService;

    //Execute called every time when no connection find in userconnection table for current social user that login.
    //Returned value will stored in connection userId in userconnection table
    @Override
    public String execute(Connection<?> connection) {
        UserEntity user = userService.getUserFromConnection(connection);
        if(user!=null){
            String userId =  user.getId().toString();
            log.info("Login with "+connection.getKey().getProviderId()+" and user id "+ userId);
            return userId;
        }else{
            String userId = signService.signup(connection);
            log.info("Register with "+connection.getKey().getProviderId()+" and user id "+ userId);
            return userId;
        }
    }
}