package com.liberty.service.impl;

import com.liberty.model.SimpleBookEntity;
import com.liberty.model.RecommendationEntity;
import com.liberty.repository.SimpleBookRepository;
import com.liberty.repository.RecommendationRepository;
import com.liberty.service.DataMinerService;
import com.liberty.service.RecommendationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * User: Dimitr
 * Date: 30.04.2017
 * Time: 22:53
 */
@Service
@Slf4j
public class DataMinerServiceImpl implements DataMinerService {
    private int page = 2648;
    private int size = 10;

    @Autowired
    private SimpleBookRepository simpleBookRepository;

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Override
    public void computeRecommendations() {
        long count = simpleBookRepository.count();
        int totalPages = (int) (count / size);
        log.info("Found {} books for recommendations. Total Pages : {}", count, totalPages);
        AtomicInteger counter = new AtomicInteger(page);
        IntStream.range(page, totalPages).parallel().forEach(currentPage -> {
            log.info("Processing page # {}: ", currentPage);
            List<SimpleBookEntity> singlePage = processPage(currentPage);
            if (CollectionUtils.isEmpty(singlePage)) {
                log.info("Page # {} is empty", singlePage);
            }
//            log.info("Processed page #{}/{}. Total books {}/{}", counter.incrementAndGet(), totalPages, counter.get() * size, count);
        });
    }

    private List<SimpleBookEntity> processPage(int currentPage) {
        Page<SimpleBookEntity> all = simpleBookRepository.findAll(new PageRequest(currentPage, size));

        List<SimpleBookEntity> singlePage = all.getContent().stream()
                .filter(x -> !x.getDeleted())
                .collect(Collectors.toList());
        singlePage.parallelStream().forEach(b -> {
            findRecommendations(b);
        });
        return singlePage;
    }

    @Override
    public List<RecommendationEntity> findRecommendations(SimpleBookEntity bookEntity) {
        List<Long> recommended = recommendationService.recommend(bookEntity.getBookId());
        if (!recommended.isEmpty())
            return saveRecommendations(bookEntity.getBookId(), recommended);
        else
            log.info("Found 0 recommendations for {}", bookEntity.getBookId());
        return Collections.emptyList();
    }

    @Override
    public List<RecommendationEntity> findRecommendations(Long bookId) {
        SimpleBookEntity bookEntity = new SimpleBookEntity();
        bookEntity.setBookId(bookId);
        return findRecommendations(bookEntity);
    }

    private List<RecommendationEntity> saveRecommendations(Long bookId, List<Long> recommended) {
        List<RecommendationEntity> list = recommended.stream()
                .map(r -> new RecommendationEntity(bookId, r))
                .collect(Collectors.toList());
        recommendationRepository.save(list);
        log.info("Stored {} recommendations for : {}", recommended.size(), bookId);
        return list;
    }
}
