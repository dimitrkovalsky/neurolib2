package com.liberty.service;

import com.liberty.dto.SearchBookDTO;
import com.liberty.dto.SearchBookPageResultDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by user on 13.05.2017.
 */
public interface SearchService {

    List<SearchBookDTO> searchBookTypeAhead(Integer size, String query);

    Page<SearchBookPageResultDTO> searchBookAll(Pageable pageRequest, String query);

}
