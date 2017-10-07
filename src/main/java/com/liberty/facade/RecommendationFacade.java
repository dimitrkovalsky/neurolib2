package com.liberty.facade;

import com.liberty.common.RandomPicker;
import com.liberty.dto.RecommendationDto;
import com.liberty.model.*;
import com.liberty.repository.*;
import com.liberty.service.DataMinerService;
import com.liberty.service.GenreService;
import com.liberty.service.ImageService;
import com.liberty.service.RecommendationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

/**
 * User: Dimitr
 * Date: 02.05.2017
 * Time: 10:45
 */
@Component
@Slf4j
public class RecommendationFacade {
    public static final int RECOMMENDATION_LIMIT = 10;
    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private AuthorFacade authorFacade;

    @Autowired
    private GenreService genreService;

    @Autowired
    private AuthorRecommendationRepository authorRecommendationRepository;

    @Autowired
    private SimpleBookRepository simpleBookRepository;

    @Autowired
    private BookCardRepository bookCardRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private FlibustaCommentRepository flibustaCommentRepository;

    @Autowired
    private BookAuthorRepository bookAuthorRepository;

    @Autowired
    private BookDescriptionRepository bookDescriptionRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private DataMinerService dataMinerService;

    @Autowired
    private ImageService imageService;

    private List<Long> cachedBookIds;

    public List<RecommendationDto> getRecommendations(Long bookId) {
        List<RecommendationEntity> recommendations = recommendationRepository.findAllByBookId(bookId);
        if (CollectionUtils.isEmpty(recommendations)) {
            recommendations = dataMinerService.findRecommendations(bookId);
        }

        return toRecoBooks(recommendations);
    }

    public BookDescriptionEntity getBookDescription(Long bookId) {
        return bookDescriptionRepository.findOne(bookId);
    }

    // todo: optimize, too much queries
    private List<RecommendationDto> toRecoBooks(List<RecommendationEntity> recommendations) {
        if (CollectionUtils.isEmpty(recommendations)) {
            return emptyList();
        }
        List<Long> ids = recommendations.stream().map(RecommendationEntity::getRecommendationId)
                .collect(Collectors.toList());

        List<RecommendationDto> collected = simpleBookRepository.findAll(ids).stream().map(b -> {
            List<AuthorEntity> authors = getAuthor(b.getBookId());
            List<GenreEntity> genres = getGenres(b.getBookId());
            RecommendationDto dto = new RecommendationDto();
            dto.setBookId(b.getBookId());
            dto.setTitle(b.getTitle());
            if (!authors.isEmpty())
                dto.setAuthor(authors.get(0));
            if (!genres.isEmpty()) {
                dto.setGenre(genres);
            }
            return dto;
        }).collect(Collectors.toList());
        return imageService.addRecoBookImages(collected);
    }

    public SimpleBookEntity getBook(Long bookId) {//todo: book image
        SimpleBookEntity bookEntity = simpleBookRepository.findOne(bookId);
        if (bookEntity != null) {
            String imagePath = imageService.getBookImagePath(bookId);
            bookEntity.setImagePath(imagePath);
        }
        return bookEntity;
    }

    public List<AuthorEntity> getAuthor(Long bookId) {
        List<BookAuthorEntity> authors = bookAuthorRepository.findAllByBookId(bookId);
        if (isEmpty(authors))
            return emptyList();
        List<Long> authorIds = authors.stream().map(BookAuthorEntity::getAuthorId).collect(Collectors.toList());
        List<AuthorEntity> writers = authorRepository.findAll(authorIds);
        return imageService.addAuthorImages(writers);
    }

    // TODO: replace to more optimal method
    public List<BookCardEntity> getRandomBooks(int size) {
        if (cachedBookIds == null)
            initCache();
        List<Long> ids = RandomPicker.pickNRandomElements(cachedBookIds, size);
        return imageService.addBookCardImages(bookCardRepository.findAllByIds(ids));
    }

    private void initCache() {
        log.info("Initializing cache");
        cachedBookIds = bookCardRepository.findAllIds();
        log.info("Cache initialized");
    }

    public List<SimpleBookEntity> getByAuthor(Long authorId) {
        List<SimpleBookEntity> byAuthor = simpleBookRepository.findAllByAuthor(authorId);
        if (CollectionUtils.isEmpty(byAuthor))
            return emptyList();
        return imageService.addSimpleBookImages(byAuthor);
    }

    public List<GenreEntity> getGenres(Long bookId) {
        return genreRepository.getAllGenres(bookId);
    }


    public List<FlibustaCommentEntity> getComments(Long bookId) {
        List<FlibustaCommentEntity> commentEntities = flibustaCommentRepository.findAllByBookIdOrderByTime(bookId);
        if (commentEntities == null)
            return emptyList();
        return commentEntities;
    }

    public List<AuthorEntity> getSimilarAuthors(AuthorEntity author) {
        long authorId = author.getAuthorId();
        List<AuthorEntity> stored = findSimilar(authorId);
        if (stored != null) {
            log.info("Used {} stored similar authors for author with id {}", stored.size(), authorId);
            return imageService.addAuthorImages(stored);
        }
        List<SimpleBookEntity> byAuthor = simpleBookRepository.findAllByAuthor(authorId);
        List<Long> ids = byAuthor.stream().map(SimpleBookEntity::getBookId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(ids))
            return emptyList();
        List<GenreEntity> genres = genreRepository.getAllGenres(ids);
        if (CollectionUtils.isEmpty(genres)) {
            log.warn("Author has not any genres: {}", authorId);
            return emptyList();
        }
        final List<AuthorEntity> similar = new ArrayList<>();
        if (genres.size() == 1) {
            List<AuthorEntity> byGenre = authorRepository.getByGenre(genres.get(0).getGenreId(), authorId, 6);
            if (!CollectionUtils.isEmpty(byGenre))
                similar.addAll(byGenre);
        }
        else if (genres.size() == 2) {
            fetchGenres(genres, similar, authorId, 3);
        }
        else {
            fetchGenres(genres, similar, authorId, 2);
        }
        saveSimilar(similar, author);
        log.info("Fetched {} similar authors for author with id {}", similar.size(), authorId);
        return imageService.addAuthorImages(similar);
    }

    private List<AuthorEntity> findSimilar(long authorId) {
        List<AuthorRecommendationEntity> similar = authorRecommendationRepository.findAllByAuthorId(authorId);
        if (CollectionUtils.isEmpty(similar))
            return null;
        List<Long> ids = similar.stream().map(AuthorRecommendationEntity::getSimilarId).collect(Collectors.toList());
        return authorRepository.findAll(ids);
    }

    private void saveSimilar(List<AuthorEntity> similar, AuthorEntity author) {
        if (CollectionUtils.isEmpty(similar))
            return;
        List<AuthorRecommendationEntity> collected = similar.stream().map(s -> {
            AuthorRecommendationEntity entity = new AuthorRecommendationEntity();
            entity.setAuthorId(author.getAuthorId());
            entity.setSimilarId(s.getAuthorId());
            return entity;
        }).collect(Collectors.toList());
        authorRecommendationRepository.save(collected);
    }

    private void fetchGenres(List<GenreEntity> genres, List<AuthorEntity> similar, long authorId, int limit) {
        genres.forEach(g -> {
            List<AuthorEntity> retrieved = authorRepository.getByGenre(g.getGenreId(), authorId, limit);
            if (!CollectionUtils.isEmpty(retrieved) && similar.size() <= RECOMMENDATION_LIMIT)
                similar.addAll(retrieved);
        });
    }
}
