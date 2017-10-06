package com.liberty.service;

import com.liberty.model.AuthorEntity;
import com.liberty.model.BookCardEntity;
import com.liberty.model.SimpleBookEntity;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * User: Dimitr
 * Date: 11.05.2017
 * Time: 21:37
 */
public interface ImageService {
    Optional<File> getAuthorImage(Long id);

    Optional<File> getBookImage(Long id);

    String getBookImagePath(Long id);

    String getAuthorImagePath(Long id);

    Map<Long, String> getBookImagePath(List<Long> ids);

    Map<Long, String> getAuthorImagePath(List<Long> ids);

    Optional<File> getBookImage(String rootDir, String dir, String file);

    List<AuthorEntity> addAuthorImages(List<AuthorEntity> writers);

    List<SimpleBookEntity> addSimpleBookImages(Collection<SimpleBookEntity> books);

    List<BookCardEntity> addBookCardImages(List<BookCardEntity> books);
}
