package com.liberty.service;

import com.liberty.dto.SearchAuthorPageResultDto;
import com.liberty.dto.SearchAuthorTypeaheadDto;
import com.liberty.dto.SearchBookTypeaheadDto;
import com.liberty.dto.SearchBookPageResultDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;

/**
 * Created by user on 13.05.2017.
 */
public interface SearchService {

    List<SearchBookTypeaheadDto> searchBookTypeahead(Integer size, String query);

    Page<SearchBookPageResultDto> searchBookAll(Pageable pageRequest, String query, Authentication auth);

    List<SearchAuthorTypeaheadDto> searchAuthorTypeahead(Integer size, String query);

    public Page<SearchAuthorPageResultDto> searchAuthorAll(Pageable paginationRequest, String query);


    }
