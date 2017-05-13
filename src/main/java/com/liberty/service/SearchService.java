package com.liberty.service;

import com.liberty.dto.SearchBookDTO;

import java.util.List;

/**
 * Created by user on 13.05.2017.
 */
public interface SearchService {

    List<SearchBookDTO> searchBook(Integer size, String query);

}
