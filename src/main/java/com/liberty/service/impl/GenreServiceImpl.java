package com.liberty.service.impl;

import com.liberty.model.AuthorEntity;
import com.liberty.model.GenreEntity;
import com.liberty.repository.GenreRepository;
import com.liberty.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: Dimitr
 * Date: 19.05.2017
 * Time: 8:45
 */
@Service
public class GenreServiceImpl implements GenreService{
    @Autowired
    private GenreRepository genreRepository;

    @Override
    public List<GenreEntity> getGenre(Long bookId) {
        return genreRepository.getAllGenres(bookId);
    }

    @Override
    public List<AuthorEntity> getTopAuthors(Integer genreId, int limit) {
        return null;
    }
}
