package com.liberty.service.impl;

import com.liberty.model.AuthorImageEntity;
import com.liberty.model.BookImageEntity;
import com.liberty.repository.AuthorImageRepository;
import com.liberty.repository.BookImageRepository;
import com.liberty.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;

/**
 * User: Dimitr
 * Date: 11.05.2017
 * Time: 21:37
 */
@Service
public class ImageServiceImpl implements ImageService {
    // TODO: move to properties
    // TODO: use separate service to serve images as static resources
    private String IMAGE_BASE_PATH = "D:\\dump\\img";
    private String AUTHOR_IMAGE_PATH = IMAGE_BASE_PATH + "\\ia";
    private String BOOK_IMAGE_PATH = IMAGE_BASE_PATH + "\\i";

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
    public Optional<File> getBookImage(String rootDir, String dir, String file) {
        String path = IMAGE_BASE_PATH + File.separator + dir + File.separator + file;
        return getImageFile(path);
    }
}
