package com.liberty.dto;

import com.liberty.model.SelectionBooksEntity;
import com.liberty.model.SimpleBookEntity;
import lombok.Data;

/**
 * Created by user on 23.09.2017.
 */
@Data
public class SelectionBookAdditionInfoDto extends SocialBookDto{
    private SelectionBooksEntity selectionBooksEntity;
}
