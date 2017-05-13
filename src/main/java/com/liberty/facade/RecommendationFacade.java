package com.liberty.facade;

import com.liberty.model.*;
import com.liberty.repository.*;
import com.liberty.service.DataMinerService;
import com.liberty.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

/**
 * User: Dimitr
 * Date: 02.05.2017
 * Time: 10:45
 */
@Component
public class RecommendationFacade {
    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private SimpleBookRepository simpleBookRepository;

    @Autowired
    private BookAuthorRepository bookAuthorRepository;

    @Autowired
    private BookDescriptionRepository bookDescriptionRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private DataMinerService dataMinerService;

    public List<SimpleBookEntity> getRecommendations(Long bookId) {
        List<RecommendationEntity> recommendations = recommendationRepository.findAllByBookId(bookId);
        if (CollectionUtils.isEmpty(recommendations)) {
            recommendations = dataMinerService.findRecommendations(bookId);
        }

        return toSimpleBooks(recommendations);
    }

    public BookDescriptionEntity getBookDescription(Long bookId) {
        return bookDescriptionRepository.findOne(bookId);
    }

    private List<SimpleBookEntity> toSimpleBooks(List<RecommendationEntity> recommendations) {
        if (CollectionUtils.isEmpty(recommendations)) {
            return emptyList();
        }
        List<Long> ids = recommendations.stream()
                .map(RecommendationEntity::getRecommendationId)
                .collect(Collectors.toList());
        return simpleBookRepository.findAll(ids);
    }

    public SimpleBookEntity getBook(Long bookId) {
        return simpleBookRepository.findOne(bookId);
    }

    // TODO: process if no author
    public List<AuthorEntity> getAuthor(Long bookId) {
        List<BookAuthorEntity> authors = bookAuthorRepository.findAllByBookId(bookId);
        List<Integer> authorIds = authors.stream().map(BookAuthorEntity::getAuthorId).collect(Collectors.toList());
        return authorRepository.findAll(authorIds);
    }

    // TODO: replace to more optimal method
    public List<SimpleBookEntity> getRandomBooks(int size) {
        return simpleBookRepository.findAllRandom();
    }
}
