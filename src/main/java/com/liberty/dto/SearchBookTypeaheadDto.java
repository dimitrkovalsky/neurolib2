package com.liberty.dto;

import com.liberty.model.SimpleBookEntity;
import lombok.Data;

import com.liberty.model.FullBookEntity;

import java.io.Serializable;

/**
 * Created by user on 12.05.2017.
 */
@Data
public class SearchBookTypeaheadDto implements Serializable{

    private static final long serialVersionUID = -8510189431954311826L;

    private Long bookId;

    private String title;

    public SearchBookTypeaheadDto(){}

    public SearchBookTypeaheadDto(FullBookEntity fullBookEntity){
        this.bookId = fullBookEntity.getBookId();
        this.title = fullBookEntity.getTitle();
    }

    public SearchBookTypeaheadDto(SimpleBookEntity simpleBookEntity){
        this.bookId = simpleBookEntity.getBookId();
        this.title = simpleBookEntity.getTitle();
    }

}
