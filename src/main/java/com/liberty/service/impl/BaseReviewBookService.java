package com.liberty.service.impl;

import com.liberty.dto.CommentDto;
import com.liberty.error.ValidationException;
import com.liberty.model.BaseUserReviewEntity;
import com.liberty.model.UserLibertyProfileEntity;
import com.liberty.repository.UserLibertyProfileRepository;
import com.liberty.repository.BaseUserObjectReview;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by user on 01.06.2017.
 */
@Slf4j
public abstract class BaseReviewBookService<T extends BaseUserReviewEntity, R extends BaseUserObjectReview<T>> {

    private static final Integer MAX_MESSAGE_SIZE = 512;

    @Autowired
    private XSSFilterImpl filter;

    @Autowired
    private UserLibertyProfileRepository profileRepository;

    @Autowired
    private R reviewRepository;

    public List<CommentDto> getComments(Long objectId, Authentication auth, Long beforeTime, Long afterTime, Integer size) {
        Long authUserId = -1L;
        if(auth!=null){
            authUserId=(Long) auth.getPrincipal();
        }
        Long userId = authUserId;
        List<T> userReviews;

        if(beforeTime!=null){
            userReviews = reviewRepository.findAllCommentBefore(objectId,beforeTime,size);
        }else
        if(afterTime!=null){
            userReviews = reviewRepository.findAllCommentAfter(objectId,afterTime,size);
        }
        else userReviews = reviewRepository.getAllByCommentedObjectId(objectId);

        return userReviews.stream().map(userObjectReviewEntity ->{
            return remapComment(userObjectReviewEntity,userId);
        }).collect(Collectors.toList());
    }


    public T postComment(Long objectId, Long userId, String comment) {
        if(!objectVerification(objectId)){
            throw new ValidationException("Object verification error");
        }
        if(comment.length()>512){
            throw new ValidationException("Message size is too long.");
        }

        String clearedComment = filter.cleanHTML(comment);

        //Проверить количество символов в сообщении, проверит или книга не удалена
        T reviewEntity = newObject();
        reviewEntity.setObjectId(objectId);
        reviewEntity.setComment(clearedComment);
        reviewEntity.setCreateTime(new Date().getTime());
        reviewEntity.setUserId(userId);
        return reviewRepository.save(reviewEntity);
    }

    public T deleteComment(Long objectId, Long userId,Long commentId ) {
        //Проверить на то что коммент принадлежит этому человеку,и этой книге.
        T reviewEntity = reviewRepository.getOne(commentId);
        if(reviewEntity==null||reviewEntity.getDeleted()){
            throw new ValidationException("Comment not exist");
        }
        if(!reviewEntity.getUserId().equals(userId)){
            throw new ValidationException("Can not delete comment of another user");
        }
        if(!reviewEntity.getObjectId().equals(objectId)) {
            throw new ValidationException("Id not correspond to commented object id");
        }
        reviewEntity.setDeleted(true);
        return reviewRepository.save(reviewEntity);
    }

    public CommentDto restoreComment(Long objectId, Long userId,Long commentId ) {
        //Проверить на то что коммент принадлежит этому человеку,и этой книге.
        T reviewEntity = reviewRepository.getOne(commentId);
        if(reviewEntity==null||!reviewEntity.getDeleted()){
            throw new ValidationException("Comment not deleted");
        }
        if(!reviewEntity.getUserId().equals(userId)){
            throw new ValidationException("Can not restore comment of another user");
        }
        if(!reviewEntity.getObjectId().equals(objectId)) {
            throw new ValidationException("Id not correspond to commented object id");
        }
        reviewEntity.setDeleted(false);

        return remapComment(reviewRepository.save(reviewEntity),userId);
    }

    abstract T newObject();

    abstract Boolean objectVerification(Long objectId);

    private String generateUserName(UserLibertyProfileEntity profile){
        String lastName = profile.getLastName();
        if(lastName==null){
            lastName = "";
        }
        return String.format("%s %s",profile.getFirstName(),lastName);
    }

    private CommentDto remapComment(T entity, Long userId){
        Long commentUserId = entity.getUserId();
        CommentDto comment = new CommentDto();
        comment.setComment(entity.getComment());
        comment.setCreateTime(entity.getCreateTime());
        comment.setId(entity.getId());
        comment.setUserName(generateUserName(profileRepository.findOneByUserId(commentUserId)));
        comment.setIsOwner(userId.equals(commentUserId));
        return comment;
    }

}
