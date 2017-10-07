package com.liberty.service.impl;

import com.liberty.dto.AuthorBornDto;
import com.liberty.dto.RecommendationDto;
import com.liberty.model.*;
import com.liberty.repository.AuthorImageRepository;
import com.liberty.repository.BookImageRepository;
import com.liberty.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.emptyCollection;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

/**
 * User: Dimitr
 * Date: 11.05.2017
 * Time: 21:37
 */
@Service
public class ImageServiceImpl implements ImageService {
    // TODO: move to properties
    // TODO: use separate service to serve images as static resources
    private String IMAGE_BASE_PATH = "http://localhost:7788/";
    private String AUTHOR_IMAGE_PATH = IMAGE_BASE_PATH + "ia";
    private String NO_IMAGE = IMAGE_BASE_PATH + "book-placeholder.jpg";
    private String BOOK_IMAGE_PATH = IMAGE_BASE_PATH + "ib";

    @Autowired
    private BookImageRepository bookImageRepository;

    @Autowired
    private AuthorImageRepository authorImageRepository;

    @Override
    public Optional<File> getAuthorImage(Long id) {
        AuthorImageEntity imageEntity = authorImageRepository.findOne(id);
        if (imageEntity == null)
            return Optional.empty();
        String path = getAuthorPath(imageEntity.getFile());

        return getImageFile(path);
    }

    private Optional<File> getImageFile(String path) {
        File file = new File(path);
        if (file.exists() && !file.isDirectory()) {
            return Optional.of(file);
        }
        return Optional.empty();
    }

    private String getBookPath(String file) {
        return BOOK_IMAGE_PATH + File.separator + file;
    }

    private String getAuthorPath(String file) {
        return AUTHOR_IMAGE_PATH + File.separator + file;
    }

    @Override
    public Optional<File> getBookImage(Long id) {
        BookImageEntity imageEntity = bookImageRepository.findOne(id);
        if (imageEntity == null)
            return Optional.empty();
        String path = getBookPath(imageEntity.getFile());

        return getImageFile(path);
    }

    @Override
    public String getBookImagePath(Long id) {
        BookImageEntity imageEntity = bookImageRepository.findOne(id);
        if (imageEntity == null)
            return "http://localhost:7788/book-placeholder.jpg";
        String path = "http://localhost:7788/ib/" + imageEntity.getFile();

        return path;
    }

    @Override
    public String getAuthorImagePath(Long id) {
        AuthorImageEntity imageEntity = authorImageRepository.findOne(id);
        if (imageEntity == null)
            return "http://localhost:7788/book-placeholder.jpg";
        String path = "http://localhost:7788/ia/" + imageEntity.getFile();

        return path;
    }

    @Override
    public Map<Long, String> getBookImagePath(List<Long> ids) {
        List<BookImageEntity> images = bookImageRepository.findAll(ids);
        Map<Long, String> map = new HashMap<>(ids.size());
        for (Long id : ids) {
            Optional<BookImageEntity> image = getBookImage(images, id);
            String imagePath = getBookImagePath(image);
            map.put(id, imagePath);
        }
        return map;
    }

    @Override
    public Map<Long, String> getAuthorImagePath(List<Long> ids) {
        List<AuthorImageEntity> images = authorImageRepository.findAll(ids);
        Map<Long, String> map = new HashMap<>(ids.size());
        for (Long id : ids) {
            Optional<AuthorImageEntity> image = getImage(images, id);
            String imagePath = getAuthorImagePath(image);
            map.put(id, imagePath);
        }
        return map;
    }

    private String getAuthorImagePath(Optional<AuthorImageEntity> image) {
        return image.map(authorImageEntity -> AUTHOR_IMAGE_PATH + authorImageEntity.getFile()).orElse(NO_IMAGE);
    }

    private String getBookImagePath(Optional<BookImageEntity> image) {
        return image.map(imageEntity -> BOOK_IMAGE_PATH + imageEntity.getFile()).orElse(NO_IMAGE);
    }

    private Optional<AuthorImageEntity> getImage(List<AuthorImageEntity> images, Long id) {
        for (AuthorImageEntity image : images) {
            if (image.getAuthorId().equals(id))
                return Optional.of(image);
        }
        return Optional.empty();
    }

    private Optional<BookImageEntity> getBookImage(List<BookImageEntity> images, Long id) {
        for (BookImageEntity image : images) {
            if (image.getBookId().equals(id))
                return Optional.of(image);
        }
        return Optional.empty();
    }

    @Override
    public Optional<File> getBookImage(String rootDir, String dir, String file) {
        String path = IMAGE_BASE_PATH + File.separator + dir + File.separator + file;
        return getImageFile(path);
    }

    @Override
    public List<AuthorEntity> addAuthorImages(List<AuthorEntity> writers) {
        if (isEmpty(writers))
            return writers;
        List<Long> ids = writers.stream().map(AuthorEntity::getAuthorId).collect(Collectors.toList());
        Map<Long, String> authorImagePath = getAuthorImagePath(ids);

        return writers.stream().peek(w -> w.setImagePath(authorImagePath.get(w.getAuthorId())))
                .collect(Collectors.toList());
    }

    @Override
    public List<SimpleBookEntity> addSimpleBookImages(Collection<SimpleBookEntity> books) {
        if (isEmpty(books))
            return Collections.emptyList();
        List<Long> ids = books.stream().map(SimpleBookEntity::getBookId).collect(Collectors.toList());
        Map<Long, String> bookImagePath = getBookImagePath(ids);

        return books.stream().peek(w -> w.setImagePath(bookImagePath.get(w.getBookId()))).collect(Collectors.toList());
    }

    @Override
    public List<BookCardEntity> addBookCardImages(List<BookCardEntity> books) {
        if (isEmpty(books))
            return books;
        List<Long> ids = books.stream().map(BookCardEntity::getBookId).collect(Collectors.toList());
        Map<Long, String> bookImagePath = getBookImagePath(ids);

        return books.stream().peek(w -> w.setImagePath(bookImagePath.get(w.getBookId()))).collect(Collectors.toList());
    }

    @Override
    public List<AuthorBornDto> addAuthorSimpleImages(List<AuthorBornDto> authors) {
        if (isEmpty(authors))
            return authors;

        List<Long> ids = authors.stream().map(AuthorBornDto::getId).collect(Collectors.toList());
        Map<Long, String> authorImagePath = getAuthorImagePath(ids);

        return authors.stream().peek(w -> w.setImagePath(authorImagePath.get(w.getId()))).collect(Collectors.toList());
    }

    @Override
    public List<RecommendationDto> addRecoBookImages(List<RecommendationDto> collected) {
        if (isEmpty(collected))
            return collected;

        List<Long> ids = collected.stream().map(RecommendationDto::getBookId).collect(Collectors.toList());
        Map<Long, String> bookImages = getBookImagePath(ids);

        return collected.stream().peek(w -> w.setImagePath(bookImages.get(w.getBookId()))).collect(Collectors.toList());
    }
}
