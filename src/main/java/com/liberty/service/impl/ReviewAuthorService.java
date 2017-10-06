package com.liberty.service.impl;

import com.liberty.model.AuthorEntity;
import com.liberty.model.UserAuthorReviewEntity;
import com.liberty.repository.AuthorRepository;
import com.liberty.repository.UserAuthorReviewRepository;
import com.liberty.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by user on 26.05.2017.
 */
@Service
@Slf4j
public class ReviewAuthorService extends BaseReviewBookService<UserAuthorReviewEntity, UserAuthorReviewRepository> implements ReviewService<UserAuthorReviewEntity> {

    @Autowired
    private AuthorRepository repository;

    @Override
    UserAuthorReviewEntity newObject() {
        return new UserAuthorReviewEntity();
    }

    @Override
    Boolean objectVerification(Long objectId) {
        AuthorEntity authorEntity = repository.getOne(objectId);
        if(authorEntity==null){
            return false;
        }else return true;
    }
}
