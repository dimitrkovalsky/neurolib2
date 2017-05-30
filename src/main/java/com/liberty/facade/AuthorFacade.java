package com.liberty.facade;

import com.liberty.error.NotFoundException;
import com.liberty.model.*;
import com.liberty.repository.*;
import com.liberty.service.GenreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.util.Function;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * User: Dimitr
 * Date: 15.05.2017
 * Time: 9:19
 */
@Component
@Slf4j
public class AuthorFacade {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private SimpleBookRepository simpleBookRepository;
    @Autowired
    private BookCollectionDescriptionRepository collectionDescriptionRepository;
    @Autowired
    private BookCollectionRepository bookCollectionRepository;
    @Autowired
    private AuthorBiographyRepository authorBiographyRepository;
    @Autowired
    private GenreService genreService;

    public AuthorEntity getAuthor(Integer authorId) {
        AuthorEntity one = authorRepository.findOne(authorId);
        if (one == null) {
            throw new NotFoundException("Can not find author with id : " + authorId);
        }
        return one;
    }

    public List<AuthorEntity> getRandomAuthors() {
        return authorRepository.getRandomAuthors(20);
    }

    public String getBiography(Integer authorId) {
        AuthorBiography biography = authorBiographyRepository.findOne(authorId);
        if (biography == null || biography.getBody() == null)
            return "";
        return biography.getBody();
    }

    //TODO: optimize queries. Use view to fetch data.
    public Map<BookCollectionDescriptionEntity, Collection<SimpleBookEntity>> getAllBooksGrouped(Integer authorId) {
        List<BookCollectionEntity> collections = bookCollectionRepository.findAllByAuthor(authorId);
        if (CollectionUtils.isEmpty(collections)) {
            // get no collection books
            return Collections.emptyMap();
        }
        Set<Long> booksToFetch = new HashSet<>();
        Set<Integer> collectionToFetch = new HashSet<>();
        collections.forEach(c -> {
            booksToFetch.add(c.getBookId());
            collectionToFetch.add(c.getTagId());
        });
        Map<Integer, BookCollectionDescriptionEntity> collectionDescriptions = toMap(collectionDescriptionRepository
                .findAll(collectionToFetch), BookCollectionDescriptionEntity::getSeqId);
        Map<Long, SimpleBookEntity> books = toMap(simpleBookRepository.findAll(booksToFetch), SimpleBookEntity::getBookId);

        Map<BookCollectionDescriptionEntity, Collection<SimpleBookEntity>> map = new HashMap<>();
        collections.forEach(c -> {
            BookCollectionDescriptionEntity key = collectionDescriptions.get(c.getTagId());
            Collection<SimpleBookEntity> byCollection = map.computeIfAbsent(key, k -> new HashSet<>());
            SimpleBookEntity book = books.get(c.getBookId());
            byCollection.add(book);
        });
        // TODO: precompute genres
        map.forEach((k, v) -> {
            if (!v.isEmpty()) {
                List<GenreEntity> genres = genreService.getGenre(v.iterator().next().getBookId());
                k.setGenres(genres);
            }
        });
        return map;
    }

    private <K, V> Map<K, V> toMap(Iterable<V> iterable, Function<V, K> mapper) {
        Map<K, V> map = new HashMap<>();
        iterable.forEach(x -> {
            try {
                map.put(mapper.apply(x), x);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        });

        return map;
    }
    
}
