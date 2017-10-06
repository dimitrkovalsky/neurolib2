package com.liberty.service.impl;

import com.liberty.model.BookRateEntity;
import com.liberty.model.SimpleBookEntity;
import com.liberty.repository.BookRateRepository;
import com.liberty.repository.SimpleBookRepository;
import com.liberty.service.ImageService;
import com.liberty.service.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * User: Dimitr
 * Date: 31.05.2017
 * Time: 7:55
 */
@Service
@Slf4j
public class RatingServiceImpl implements RatingService {
    @Autowired
    private BookRateRepository bookRateRepository;

    @Autowired
    private SimpleBookRepository simpleBookRepository;

    @Autowired
    private ImageService imageService;

    private static List<SimpleBookEntity> mostReadable = null;

    @Override
    public List<SimpleBookEntity> getMostReadable() {
        if (mostReadable != null)
            return mostReadable;
        
        List<BookRateEntity> top = bookRateRepository.findAllByRate(5, 10);
        log.info("Found {} book for top", top.size());
        mostReadable = toSimpleBook(top);
        return imageService.addSimpleBookImages(mostReadable);
    }

    private List<SimpleBookEntity> toSimpleBook(List<BookRateEntity> list) {
        return simpleBookRepository.findAll(list.stream()
                .map(BookRateEntity::getBookId)
                .collect(Collectors.toList()));
    }
}
