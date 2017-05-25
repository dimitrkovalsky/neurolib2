package com.liberty.dto;

import lombok.Data;

/**
 * Created by user on 14.05.2017.
 */
@Data
public class SearchBookPageResultDto extends BaseBookDto {
    private Boolean isInShelf;

    private Float commonRate;
}
