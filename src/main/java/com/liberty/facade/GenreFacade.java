package com.liberty.facade;

import com.liberty.model.BookCardEntity;
import com.liberty.model.GenreEntity;
import com.liberty.repository.BookCardRepository;
import com.liberty.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * User: Dimitr
 * Date: 22.05.2017
 * Time: 9:03
 */
@Component
public class GenreFacade {

    @Autowired
    private BookCardRepository bookCardRepository;
    @Autowired
    private GenreRepository genreRepository;

    private static NavigableMap<String, List<GenreEntity>> genresCache = null;

    public List<BookCardEntity> getByGenre(Integer genreId) {
        return bookCardRepository.findAllByGenre(genreId, 20);
    }

    public List<GenreEntity> getAllGenres() {
        return genreRepository.findAll();
    }

    // todo: use ehcache
    public Map<String, List<GenreEntity>> getAllGenresGrouped() {
        if (genresCache != null)
            return genresCache;
        List<GenreEntity> genres = genreRepository.findAll();
        Map<String, List<GenreEntity>> map = genres.stream()
                .collect(Collectors.groupingBy(GenreEntity::getGenreMeta));
        genresCache = new TreeMap<>(map);
        return genresCache;
    }
}
