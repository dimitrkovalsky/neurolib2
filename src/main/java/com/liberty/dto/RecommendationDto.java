package com.liberty.dto;

import com.liberty.model.AuthorEntity;
import com.liberty.model.GenreEntity;
import lombok.Data;

import java.util.List;

/**
 * User: Dimitr
 * Date: 21.05.2017
 * Time: 20:48
 */
@Data
public class RecommendationDto {
    private Long bookId;
    private String title;
    private AuthorEntity author;
    private List<GenreEntity> genre;
}
