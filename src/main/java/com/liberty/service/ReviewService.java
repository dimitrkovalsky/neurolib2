package com.liberty.service;

import com.liberty.dto.CommentDto;
import com.liberty.model.BaseUserReviewEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

/**
 * Created by user on 01.06.2017.
 */
public interface ReviewService <T extends BaseUserReviewEntity> {

    List<CommentDto> getComments(Long objectId, Authentication auth, Long beforeTime, Long afterTime, Integer size) ;

    T postComment(Long objectId, Long userId, String comment);

    T deleteComment(Long objectId, Long userId,Long commentId );

    CommentDto restoreComment(Long objectId, Long userId,Long commentId );

}
