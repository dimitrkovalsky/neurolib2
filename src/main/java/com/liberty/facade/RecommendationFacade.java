package com.liberty.facade;

import com.liberty.common.RandomPicker;
import com.liberty.model.*;
import com.liberty.repository.*;
import com.liberty.service.DataMinerService;
import com.liberty.service.RecommendationService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class RecommendationFacade {
    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private SimpleBookRepository simpleBookRepository;

    @Autowired
    private BookCardRepository bookCardRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private FlibustaCommentRepository flibustaCommentRepository;

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

    List<Long> cachedBookIds;

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

    public List<AuthorEntity> getAuthor(Long bookId) {
        List<BookAuthorEntity> authors = bookAuthorRepository.findAllByBookId(bookId);
        if (CollectionUtils.isEmpty(authors))
            return emptyList();
        List<Integer> authorIds = authors.stream().map(BookAuthorEntity::getAuthorId).collect(Collectors.toList());
        return authorRepository.findAll(authorIds);
    }

    // TODO: replace to more optimal method
    public List<BookCardEntity> getRandomBooks(int size) {
        if (cachedBookIds == null)
            initCache();
        List<Long> ids = RandomPicker.pickNRandomElements(cachedBookIds, size);
        return bookCardRepository.findAllByIds(ids);
    }

    private void initCache() {
        log.info("Initializing cache");
        cachedBookIds = bookCardRepository.findAllIds();
        log.info("Cache initialized");
    }

    public List<SimpleBookEntity> getByAuthor(Integer authorId) {
        List<SimpleBookEntity> byAuthor = simpleBookRepository.findAllByAuthor(authorId);
        if (CollectionUtils.isEmpty(byAuthor))
            return emptyList();
        return byAuthor;
    }

    public List<GenreEntity> getGenres(Long bookId) {
        return genreRepository.getAllGenres(bookId);
    }

    public List<SimpleBookEntity> getByGenre(Integer genreId) {
        return simpleBookRepository.findAllByGenre(genreId, 10);
    }

    public List<FlibustaCommentEntity> getComments(Long bookId) {
        List<FlibustaCommentEntity> commentEntities = flibustaCommentRepository.findAllByBookIdOrderByTime(bookId);
        if (commentEntities == null)
            return emptyList();
        return commentEntities;
    }
}
