package com.liberty.service.impl;

import com.liberty.model.FullBookEntity;
import com.liberty.repository.BookRateRepository;
import com.liberty.repository.BookRepository;
import com.liberty.repository.LibBookRepository;
import com.liberty.service.RecommendationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    private BookRepository repository;

    @Autowired
    private BookRateRepository rateRepository;

    @Autowired
    private LibBookRepository libBookRepository;

    @Override
    public List<Long> recommend(Long bookId) {
        FullBookEntity book = repository.findOne(bookId);
        if(book == null){
            log.info("Can not find full book for {}", bookId);
            return Collections.emptyList();
        }
        List<FullBookEntity> identical = getIdentical(book);
        List<FullBookEntity> sameAuthor = repository.findAllByAuthorIdOrderByRate(book.getAuthorId(), top(2));
        List<FullBookEntity> sameGenre = repository.findAllByGenreIdOrderByRate(book.getGenreId(), top(3));
        List<FullBookEntity> sameTags = repository.findAllByTagIdOrderByRate(book.getTagId(), top(3));

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
//        log.info("Recommended for {}", book);
//        libBookRepository.findAll(ids).forEach(b -> log.info("Rec # {}", b));
        ids.remove(bookId);
        return ids;
    }

    private List<FullBookEntity> getIdentical(FullBookEntity book) {
        return repository.findAllByAuthorIdAndGenreIdAndTagIdOrderByRate(book.getAuthorId(),
                book.getGenreId(), book.getTagId(), top(5));
    }

    private Pageable top(int limit) {
        return new PageRequest(0, limit);
    }

    @Override
    public void evaluate() {
        repository.findAll(new PageRequest(0, 10))
                .forEach(x -> recommend(x.getBookId()));
    }
}
