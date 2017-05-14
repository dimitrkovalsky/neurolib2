package com.liberty.service.impl;

import com.liberty.dto.SearchBookDTO;
import com.liberty.dto.SearchBookPageResultDTO;
import com.liberty.facade.RecommendationFacade;
import com.liberty.model.FullBookEntity;
import com.liberty.model.SimpleBookEntity;
import com.liberty.repository.FullBookRepository;
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
    private FullBookRepository repository;

    @Autowired
    private RecommendationFacade facade;

    @Autowired
    private SimpleBookRepository simpleBookRepository;

    public List<SearchBookDTO> searchBookTypeAhead(Integer size, String query){
        log.info("Search books in title like '{}' with maximum response size {} ",query,size);
        if(size>100||size<1){
            log.info("Size is out of range 1-100. Set maximum size to 100");
            size=100;
        }
        List<FullBookEntity> books = searchBook(0,size,query);

        return books.stream().map(fullBookEntity -> new SearchBookDTO(fullBookEntity)).distinct().collect(Collectors.toList());
    }

    private List<SimpleBookEntity> searchBooksId(Pageable pageRequest, String query){
        return simpleBookRepository.findAllByTitleContaining(query, pageRequest).getContent();
    }

    private List<FullBookEntity> searchBook(Integer page, Integer size, String query){
        List<FullBookEntity> books = recoBookRepository.findAllByDeletedFalseAndTitleContainingOrderByRateDesc(query,  new PageRequest(page, size));
        log.info("Find {} books in base",books.size());
        return books;
    }

    public Page<SearchBookPageResultDTO> searchBookAll(Pageable pageRequest, String query){
        List<SimpleBookEntity> books = searchBooksId(pageRequest,query);
        List<SearchBookPageResultDTO> booksDTO =  books.stream().map(book -> {
            SearchBookPageResultDTO searchBookFullDTO = new SearchBookPageResultDTO();
            searchBookFullDTO.setAuthors(facade.getAuthor(book.getBookId()));
            searchBookFullDTO.setBook(book);
            searchBookFullDTO.setGenres(facade.getGenres(book.getBookId()));
            return searchBookFullDTO;
        }).collect(Collectors.toList());
        return new PageImpl<SearchBookPageResultDTO>(booksDTO,pageRequest,booksDTO.size());
    }

}
