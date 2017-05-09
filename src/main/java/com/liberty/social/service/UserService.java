package com.liberty.social.service;

import com.liberty.basis.enumeration.UserAuthority;
import com.liberty.model.UserEntity;
import com.liberty.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public UserEntity createUser(Connection<?> connection) {

		UserEntity user = new UserEntity();

		ConnectionKey connectionKey = connection.getKey();
		String providerUserId = connectionKey.getProviderUserId();

		switch (connectionKey.getProviderId()){
			case "facebook":
				user.setFacebookId(providerUserId);
				break;
			case "linkedin":
				user.setLinkedinId(providerUserId);
				break;
			case "twitter":
				user.setTwitterId(providerUserId);
				break;
			case "vkontakte":
				user.setVkontakteId(providerUserId);
				break;
		}
		user.setAuthority(UserAuthority.USER);
		user.setCreateTime(new Date().getTime());
		user.setLogin(connection.fetchUserProfile().getUsername());
		user = userRepository.save(user);
		return user;

	}

	public UserEntity getUserFromConnection(Connection<?> connection){
		ConnectionKey connectionKey = connection.getKey();
		String providerUserId = connectionKey.getProviderUserId();

		switch (connectionKey.getProviderId()){
			case "facebook":
				return userRepository.findOneByFacebookId(providerUserId);
			case "linkedin":
				return userRepository.findOneByLinkedinId(providerUserId);
			case "twitter":
				return userRepository.findOneByTwitterId(providerUserId);
			case "vkontakte":
				return userRepository.findOneByVkontakteId(providerUserId);
			default:return null;
		}

	}

	public UserEntity getUserFromId(long id){
		return userRepository.findOne(id);
	}

	

	public Boolean existUser(Connection<?> connection) {
		UserEntity user = getUserFromConnection(connection);

		return user != null ;
	}

}
