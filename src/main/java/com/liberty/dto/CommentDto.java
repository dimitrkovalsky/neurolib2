package com.liberty.dto;

import lombok.Data;

/**
 * Created by user on 27.05.2017.
 */
@Data
public class CommentDto {
    private Long id;
    private String userName;
    private Long createTime;
    private String comment;
    private Integer rate;
    private Boolean isOwner;
}
