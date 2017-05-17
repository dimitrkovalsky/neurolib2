package com.liberty.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * User: Dimitr
 * Date: 17.05.2017
 * Time: 8:57
 */
@Data
@AllArgsConstructor
public class TwoColumnDto<T> {
    List<T> leftColumn;
    List<T> rightColumn;
}
