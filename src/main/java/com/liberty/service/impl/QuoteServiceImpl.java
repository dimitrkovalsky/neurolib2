package com.liberty.service.impl;

import com.liberty.model.QuoteEntity;
import com.liberty.repository.QuoteRepository;
import com.liberty.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Dimitr
 * Date: 06.06.2017
 * Time: 7:50
 */
@Service
public class QuoteServiceImpl implements QuoteService {
    @Autowired
    private QuoteRepository quoteRepository;
    private QuoteEntity dayQuote;

    @Override
    public QuoteEntity getQuoteOfTheDay() {
        if (dayQuote == null) {
            dayQuote = quoteRepository.getRandomQuotes(1).get(0);
        }
        return dayQuote;
    }
}
