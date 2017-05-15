package com.liberty.facade;

import com.liberty.error.NotFoundException;
import com.liberty.model.AuthorEntity;
import com.liberty.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * User: Dimitr
 * Date: 15.05.2017
 * Time: 9:19
 */
@Component
public class AuthorFacade {

    @Autowired
    private AuthorRepository authorRepository;

    public AuthorEntity getAuthor(Integer authorId) {
        AuthorEntity one = authorRepository.findOne(authorId);
        if (one == null) {
            throw new NotFoundException("Can not find author with id : " + authorId);
        }
        return one;
    }

    public List<AuthorEntity> getRandomAuthors(int size) {
        return null;
    }
}
