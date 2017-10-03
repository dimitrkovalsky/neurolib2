package com.liberty.service.impl;

import com.liberty.model.*;
import com.liberty.repository.*;
import com.liberty.service.RecommendationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

/**
 * User: Dimitr
 * Date: 26.04.2017
 * Time: 21:28
 */
@Service
@Slf4j
public class RecommendationServiceImpl implements RecommendationService {

    public static final int LIMIT = 10;
    @Autowired
    private FullBookRepository fullBookRepository;

    @Autowired
    private BookRateRepository rateRepository;

    @Autowired
    private BookCollectionRepository bookCollectionRepository;

    @Autowired
    private SimpleBookRepository simpleBookRepository;

    @Autowired
    private BookAuthorRepository bookAuthorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public List<Long> recommend(Long bookId) {
        FullBookEntity book = fullBookRepository.findOne(bookId);
        if (book == null) {
            log.info("Can not find full book for {}", bookId);
            return getRecommendations(bookId);
        }
        List<FullBookEntity> identical = getIdentical(book);
        List<FullBookEntity> sameAuthor = fullBookRepository.findAllByAuthorIdOrderByRate(book.getAuthorId(), top(2));
        List<FullBookEntity> sameGenre = fullBookRepository.findAllByGenreIdOrderByRate(book.getGenreId(), top(3));
        List<FullBookEntity> sameTags = fullBookRepository.findAllByTagIdOrderByRate(book.getTagId(), top(3));

        log.info("Book : {}", book);
        log.info("Identical : {}", identical.size());
        log.info("Same author : {}", sameAuthor.size());
        log.info("Same genre : {}", sameGenre.size());
        log.info("Same tags : {}", sameTags.size());

        List<FullBookEntity> all = new ArrayList<>();
        all.addAll(identical);
        all.addAll(sameAuthor);
        all.addAll(sameGenre);
        all.addAll(sameTags);

        List<Long> ids = all.stream().map(FullBookEntity::getBookId).collect(Collectors.toList());
        ids.remove(bookId);
        return ids;
    }

    private List<Long> getRecommendations(Long bookId) {
        SimpleBookEntity bookEntity = simpleBookRepository.findOne(bookId);
        if (bookEntity == null) {
            log.error("Can not find book for {}", bookId);
            return emptyList();
        }
        List<BookAuthorEntity> authors = bookAuthorRepository.findAllByBookId(bookId);
        List<GenreEntity> genres = genreRepository.getAllGenres(bookId);
        List<BookCollectionEntity> tags = bookCollectionRepository.findAllByBookId(bookId);
        List<Long> ids = new ArrayList<>();
        ids.addAll(sameAuthorRecommendations(authors));
        ids.addAll(sameGenreRecommendations(genres));
        ids.addAll(sameTagRecommendations(tags));
        log.info("Found {} recommendations for : {}", ids.size(), bookId);
        return ids;
    }

    private List<Long> sameTagRecommendations(List<BookCollectionEntity> tags) {
        if (CollectionUtils.isEmpty(tags))
            return emptyList();
        if (tags.size() == 1) {
            List<SimpleBookEntity> byGenres = simpleBookRepository.findAllByTag(tags.get(0).getTagId(), 4);
            return safeMapToIds(byGenres);
        }
        List<SimpleBookEntity> bookEntities = tags.stream()
                .flatMap(t -> simpleBookRepository.findAllByGenre(t.getTagId(), 2).stream())
                .collect(Collectors.toList());

        return safeMapToIds(bookEntities);
    }

    // TODO: refactor use single method with function references
    private List<Long> sameGenreRecommendations(List<GenreEntity> genres) {
        if (CollectionUtils.isEmpty(genres))
            return emptyList();
        if (genres.size() == 1) {
            List<SimpleBookEntity> byGenres = simpleBookRepository.findAllByGenre(genres.get(0).getGenreId(), 4);
            return safeMapToIds(byGenres);
        }
        List<SimpleBookEntity> bookEntities = genres.stream()
                .flatMap(g -> simpleBookRepository.findAllByGenre(g.getGenreId(), 2).stream())
                .collect(Collectors.toList());

        return safeMapToIds(bookEntities);
    }

    private List<Long> sameAuthorRecommendations(List<BookAuthorEntity> authors) {
        if (CollectionUtils.isEmpty(authors))
            return emptyList();
        if (authors.size() == 1) {
            List<SimpleBookEntity> byAuthor = simpleBookRepository.findAllByAuthor(authors.get(0).getAuthorId(), 4);
            return safeMapToIds(byAuthor);
        }
        List<SimpleBookEntity> bookEntities = authors.stream()
                .flatMap(a -> simpleBookRepository.findAllByAuthor(a.getAuthorId(), 2).stream())
                .collect(Collectors.toList());
        return safeMapToIds(bookEntities);
    }

    private List<Long> safeMapToIds(List<SimpleBookEntity> books) {
        if (books != null) {
            return books.stream().map(SimpleBookEntity::getBookId).collect(Collectors.toList());
        }
        return emptyList();
    }

    private List<FullBookEntity> getIdentical(FullBookEntity book) {
        return fullBookRepository
                .findAllByAuthorIdAndGenreIdAndTagIdOrderByRate(book.getAuthorId(), book.getGenreId(), book.getTagId(),
                        top(5));
    }

    private Pageable top(int limit) {
        return new PageRequest(0, limit);
    }

    @Override
    public void preProcess() {// todo: Preprocessed 18000/153294 books
        boolean shouldStop = false;
        AtomicInteger counter = new AtomicInteger(0);
        int page = 0;
        long size = fullBookRepository.count();
        int pageSize = 1000;
        System.out.println("Trying to cache recommendations for " + size + " books. Starting from : " + page * size);
        while (!shouldStop) {
            Page<FullBookEntity> all = fullBookRepository.findAll(new PageRequest(page, pageSize));
            if (CollectionUtils.isEmpty(all.getContent()) || all.getContent().size() < pageSize) {
                shouldStop = true;
            }
            all.getContent().parallelStream().forEach(x -> {
                recommend(x.getBookId());
                counter.incrementAndGet();
            });
            page++;
            System.out.println("Preprocessed " + counter.get() + "/" + size + " books : ");
        }
    }
}
