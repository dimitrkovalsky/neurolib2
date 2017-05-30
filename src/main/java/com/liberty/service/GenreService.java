package com.liberty.service;

import com.liberty.model.AuthorEntity;
import com.liberty.model.GenreEntity;

import java.util.List;

/**
 * User: Dimitr
 * Date: 19.05.2017
 * Time: 8:47
 */
public interface GenreService {
    List<GenreEntity> getGenre(Long bookId);

    List<AuthorEntity> getTopAuthors(Integer genreId, int limit);
}
