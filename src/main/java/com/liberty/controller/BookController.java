package com.liberty.controller;

import com.liberty.repository.BookAuthorRepository;
import com.liberty.repository.FullBookRepository;
import com.liberty.service.DataMinerService;
import com.liberty.service.RecommendationService;
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
public class BookController {

    @Autowired
    private BookAuthorRepository repository;

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private DataMinerService dataMinerService;

    @Autowired
    private FullBookRepository fullBookRepository;

    @RequestMapping(path = "/{bookId}", method = RequestMethod.GET)
    public void getOne(@PathVariable Long bookId) {
        dataMinerService.computeRecommendations();
//        recommendationService.evaluate();
//        return bookRepository.findOne(bookId);
    }
}
