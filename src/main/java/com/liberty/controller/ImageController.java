package com.liberty.controller;

import com.liberty.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * User: Dimitr
 * Date: 11.05.2017
 * Time: 21:48
 */
@RestController
@RequestMapping
@Slf4j
public class ImageController {

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/api/images/author/{id}", method = RequestMethod.GET, produces = MediaType
            .IMAGE_JPEG_VALUE)
    public ResponseEntity<?> getAuthor(@PathVariable Long id) {
        Optional<File> image = imageService.getAuthorImage(id);
        return sendImage(image);
    }

    @RequestMapping(value = "/api/images/book/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> getBookImage(@PathVariable Long id) {
        Optional<File> image = imageService.getBookImage(id);
        return sendImage(image);
    }

    @RequestMapping(value = "/i/{rootDir}/{dir}/{file}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> getBookImage(@PathVariable String rootDir, @PathVariable String dir, @PathVariable String file) {
        Optional<File> image = imageService.getBookImage(rootDir, dir, file);
        return sendImage(image);
    }

    private ResponseEntity<?> sendImage(Optional<File> image) {
        if (!image.isPresent()) {
            return notFound();
        }
        File imageFile = image.get();
        return sendFile(imageFile);
    }
       // todo: fix cache control
    private ResponseEntity<?> sendFile(File imageFile) {
        byte[] bytes;
        try {
            return ResponseEntity.ok()
                    .header("Cache-Control", "public, max-age=3600")
                    .contentType(MediaType.IMAGE_JPEG)
                    .cacheControl(CacheControl.maxAge(14, TimeUnit.DAYS))
                    .body(new InputStreamResource(new FileInputStream(imageFile)));
        } catch (Exception e) {
            return notFound();
        }
    }

    private ResponseEntity<?> notFound() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("images/book-placeholder.jpg").getFile());
        return sendFile(file);
    }
}
