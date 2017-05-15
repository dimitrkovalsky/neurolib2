package com.liberty.service;

import java.io.File;
import java.util.Optional;

/**
 * User: Dimitr
 * Date: 11.05.2017
 * Time: 21:37
 */
public interface ImageService {
    Optional<File> getAuthorImage(Long id);

    Optional<File> getBookImage(Long id);

    Optional<File> getBookImage(String rootDir, String dir, String file);
}
