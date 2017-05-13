package com.liberty.dto;

import lombok.Data;

import com.liberty.model.FullBookEntity;

import java.io.Serializable;

/**
 * Created by user on 12.05.2017.
 */
@Data
public class SearchBookDTO implements Serializable{

    private static final long serialVersionUID = -8510189431954311826L;

    private Long bookId;

    private String title;

    public SearchBookDTO(){}

    public SearchBookDTO(FullBookEntity fullBookEntity){
        this.bookId = fullBookEntity.getBookId();
        this.title = fullBookEntity.getTitle();
    }

}
