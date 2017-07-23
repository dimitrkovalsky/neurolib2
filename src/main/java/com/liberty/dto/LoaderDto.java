package com.liberty.dto;

import lombok.Data;

/**
 * Created by user on 21.07.2017.
 */
@Data
public class LoaderDto <T> {
    private T data;
    private Boolean available;
}
