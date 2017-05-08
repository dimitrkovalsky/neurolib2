package com.liberty.social.sign;

import com.liberty.social.service.SignService;
import com.liberty.social.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Service;

/**
 * Created by user on 08.05.2017.
 */
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
        System.out.println("Signup with "+connection.getKey().getProviderId()+" called");
        if(userService.existUser(connection)){
            String userId =  userService.getUserFromConnection(connection).getId().toString();
            System.out.println("Login with "+connection.getKey().getProviderId()+" and user id "+ userId);
            return userId;
        }else{
            String userId = signService.signup(connection);
            System.out.println("Register with "+connection.getKey().getProviderId()+" and user id "+ userId);
            return userId;
        }
			//return connection.getKey().getProviderUserId();
    }
}