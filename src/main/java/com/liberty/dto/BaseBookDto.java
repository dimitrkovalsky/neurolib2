package com.liberty.dto;

import com.liberty.model.AuthorEntity;
import com.liberty.model.GenreEntity;
import com.liberty.model.SimpleBookEntity;
import lombok.Data;

import java.util.List;

/**
 * Created by user on 18.05.2017.
 */
@Data
public class BaseBookDto {

    private SimpleBookEntity book;

    private List<AuthorEntity> authors;

    private List<GenreEntity> genres;
}
