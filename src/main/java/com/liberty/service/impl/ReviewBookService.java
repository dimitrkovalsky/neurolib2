package com.liberty.service.impl;

import com.liberty.facade.RecommendationFacade;
import com.liberty.model.SimpleBookEntity;
import com.liberty.model.UserBookReviewEntity;
import com.liberty.repository.UserBookReviewRepository;
import com.liberty.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by user on 26.05.2017.
 */
@Service
@Slf4j
public class ReviewBookService  extends BaseReviewBookService<UserBookReviewEntity, UserBookReviewRepository>  implements ReviewService<UserBookReviewEntity> {

    @Autowired
    private RecommendationFacade facade;

    @Override
    UserBookReviewEntity newObject() {
        return new UserBookReviewEntity();
    }

    @Override
    Boolean objectVerification(Long objectId) {
        SimpleBookEntity book = facade.getBook(objectId);
        if(book==null){
            return false;
        }else if(book.getDeleted()){
            return false;
        }else return true;
    }
}
