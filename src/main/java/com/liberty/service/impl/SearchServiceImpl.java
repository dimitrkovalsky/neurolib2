package com.liberty.service.impl;

import com.liberty.dto.SearchBookTypeaheadDTO;
import com.liberty.dto.SearchBookPageResultDTO;
import com.liberty.facade.RecommendationFacade;
import com.liberty.model.SimpleBookEntity;
import com.liberty.repository.SimpleBookRepository;
import com.liberty.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by user on 12.05.2017.
 */
@Slf4j
@Service
public class SearchServiceImpl implements SearchService{

    @Autowired
    private RecommendationFacade facade;

    @Autowired
    private SimpleBookRepository simpleBookRepository;

    public List<SearchBookTypeaheadDTO> searchBookTypeahead(Integer size, String query){
        log.info("Search books in title like '{}' with maximum response size {} ",query,size);
        if(size>100||size<1){
            log.info("Size is out of range 1-100. Set maximum size to 100");
            size=100;
        }
        List<SimpleBookEntity> books = searchBooks(new PageRequest(0,size), query).getContent();

        return books.stream().map(fullBookEntity -> new SearchBookTypeaheadDTO(fullBookEntity)).collect(Collectors.toList());
    }

    public Page<SearchBookPageResultDTO> searchBookAll(Pageable paginationRequest, String query){
        Page<SimpleBookEntity> books = searchBooks(paginationRequest,query);
        List<SearchBookPageResultDTO> booksDTOList =  books.getContent().stream().map(book -> {
            SearchBookPageResultDTO searchBookPageResultDTO = new SearchBookPageResultDTO();
            searchBookPageResultDTO.setAuthors(facade.getAuthor(book.getBookId()));
            searchBookPageResultDTO.setBook(book);
            searchBookPageResultDTO.setGenres(facade.getGenres(book.getBookId()));
            return searchBookPageResultDTO;
        }).collect(Collectors.toList());
        return new PageImpl<SearchBookPageResultDTO>(booksDTOList,paginationRequest,books.getTotalElements());
    }

    private Page<SimpleBookEntity> searchBooks(Pageable paginationRequest, String query){
        Page<SimpleBookEntity> pages = simpleBookRepository.findAllByTitleContaining(query, paginationRequest);
        log.info("{} books finded ",pages.getNumberOfElements());
        return pages;
    }

}
