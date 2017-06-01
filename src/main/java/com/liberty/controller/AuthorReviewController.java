package com.liberty.controller;

import com.liberty.dto.CommentDto;
import com.liberty.service.ReviewService;
import com.liberty.service.impl.ReviewAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by user on 26.05.2017.
 */

@RestController
@RequestMapping("/author/{authorId}/comments")
public class AuthorReviewController {

    @Autowired
    private ReviewService reviewAuthorService;

    @RequestMapping("")
    public List<CommentDto> getAllComments(@PathVariable Long authorId,
                                           @RequestParam(value = "before",required = false) Long beforeTime,
                                           @RequestParam(value = "after", required = false,defaultValue = "0") Long afterTime,
                                           @RequestParam(value = "size",required = false, defaultValue = "20") Integer size,
                                           Authentication auth) {

        return reviewAuthorService.getComments(authorId,auth,beforeTime,afterTime,size);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.POST)
    public void postComment(@PathVariable Long authorId, Authentication auth, @RequestParam(value = "comment") String comment) {
        reviewAuthorService.postComment(authorId,(Long)auth.getPrincipal(), comment);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/{commentId}",method = RequestMethod.DELETE)
    public void deleteComment(@PathVariable Long authorId,@PathVariable Long commentId, Authentication auth) {
        reviewAuthorService.deleteComment(authorId,(Long)auth.getPrincipal(),commentId);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/{commentId}",method = RequestMethod.PUT)
    public CommentDto restoreComment(@PathVariable Long authorId,@PathVariable Long commentId, Authentication auth) {
        return reviewAuthorService.restoreComment(authorId,(Long)auth.getPrincipal(),commentId);
    }

}
