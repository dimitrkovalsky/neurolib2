package com.liberty.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by user on 23.05.2017.
 */
@Data
public class SearchAuthorTypeaheadDto implements Serializable {
    private static final long serialVersionUID = 6831511361010861532L;

    private Integer authorId;

    private String authorName;
}
