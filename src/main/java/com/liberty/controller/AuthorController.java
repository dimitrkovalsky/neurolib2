package com.liberty.controller;

import com.liberty.model.AuthorEntity;
import com.liberty.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: Dimitr
 * Date: 25.04.2017
 * Time: 8:05
 */
@RestController
@RequestMapping("/api/author")
public class AuthorController {

    @Autowired
    private AuthorRepository repository;

    @RequestMapping(path = "/{bookId}", method = RequestMethod.GET)
    public AuthorEntity getOne(@PathVariable Long bookId) {
        return repository.findOne(bookId);
    }
}
