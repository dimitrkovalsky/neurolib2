package com.liberty.dto;

import com.liberty.model.SelectionEntity;
import com.liberty.model.SimpleBookEntity;
import lombok.Data;

import java.util.List;

/**
 * Created by user on 14.09.2017.
 */
@Data
public class SelectionBookListDto {
    private SelectionEntity selection;
    private List<SimpleBookEntity> books;
}
