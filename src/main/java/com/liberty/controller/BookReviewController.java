package com.liberty.controller;

import com.liberty.dto.CommentDto;
import com.liberty.model.UserBookReviewEntity;
import com.liberty.service.ReviewService;
import com.liberty.service.impl.ReviewBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by user on 26.05.2017.
 */

@RestController
@RequestMapping("/book/{bookId}/comments")
public class BookReviewController {

    @Autowired
    private ReviewService reviewBookService;

    @RequestMapping("")
    public List<CommentDto> getAllComments(@PathVariable Long bookId,
                                           @RequestParam(value = "before",required = false) Long beforeTime,
                                           @RequestParam(value = "after", required = false,defaultValue = "0") Long afterTime,
                                           @RequestParam(value = "size",required = false, defaultValue = "20") Integer size,
                                           Authentication auth) {

        return reviewBookService.getComments(bookId,auth,beforeTime,afterTime,size);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.POST)
    public void postComment(@PathVariable Long bookId, Authentication auth, @RequestParam(value = "comment") String comment) {
        reviewBookService.postComment(bookId,(Long)auth.getPrincipal(), comment);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/{commentId}",method = RequestMethod.DELETE)
    public void deleteComment(@PathVariable Long bookId,@PathVariable Long commentId, Authentication auth) {
        reviewBookService.deleteComment(bookId,(Long)auth.getPrincipal(),commentId);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/{commentId}",method = RequestMethod.PUT)
    public CommentDto restoreComment(@PathVariable Long bookId,@PathVariable Long commentId, Authentication auth) {
        return reviewBookService.restoreComment(bookId,(Long)auth.getPrincipal(),commentId);
    }

}
