package com.liberty.dto;

import lombok.Data;

/**
 * Created by user on 23.09.2017.
 */
@Data
public class SocialBookDto extends BaseBookDto {

    private Boolean isInShelf;

    private Float commonRate;
}
