package com.liberty.facade;

import com.liberty.model.BookCardEntity;
import com.liberty.repository.BookCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * User: Dimitr
 * Date: 22.05.2017
 * Time: 9:03
 */
@Component
public class GenreFacade {
    @Autowired
    private BookCardRepository bookCardRepository;

    public List<BookCardEntity> getByGenre(Integer genreId) {
        return bookCardRepository.findAllByGenre(genreId, 20);
    }
}
