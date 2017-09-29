package com.liberty.service.impl;

import com.liberty.dto.SearchAuthorPageResultDto;
import com.liberty.dto.SearchAuthorTypeaheadDto;
import com.liberty.dto.SearchBookTypeaheadDto;
import com.liberty.dto.SearchBookPageResultDto;
import com.liberty.facade.RecommendationFacade;
import com.liberty.model.AuthorEntity;
import com.liberty.model.SimpleBookEntity;
import com.liberty.repository.AuthorRepository;
import com.liberty.repository.SimpleBookRepository;
import com.liberty.repository.UserBookshelfRepository;
import com.liberty.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private UserBookshelfRepository bookshelfRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Cacheable("searchBookTypeahead")
    public List<SearchBookTypeaheadDto> searchBookTypeahead(Integer size, String query){
        log.info("Search books in title like '{}' with maximum response size {} ",query,size);
        if(size>100||size<1){
            log.info("Size is out of range 1-100. Set maximum size to 100");
            size=100;
        }
        List<SimpleBookEntity> books = searchBooks(new PageRequest(0,size), query).getContent();

        return books.stream().map(fullBookEntity -> new SearchBookTypeaheadDto(fullBookEntity)).collect(Collectors.toList());
    }

    public Page<SearchBookPageResultDto> searchBookAll(Pageable paginationRequest, String query){
        Page<SimpleBookEntity> books = searchBooks(paginationRequest,query);
        List<SearchBookPageResultDto> booksDTOList =  books.getContent().stream().map(book -> {
            SearchBookPageResultDto searchBookPageResultDTO = new SearchBookPageResultDto();
            searchBookPageResultDTO.setAuthors(facade.getAuthor(book.getBookId()));
            searchBookPageResultDTO.setBook(book);
            searchBookPageResultDTO.setGenres(facade.getGenres(book.getBookId()));
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(!"anonymousUser".equals(auth.getPrincipal())) {
                searchBookPageResultDTO.setIsInShelf(bookshelfRepository.getOneByBookIdAndUserId(book.getBookId(), (Long)auth.getPrincipal()) != null);
            }
            return searchBookPageResultDTO;
        }).collect(Collectors.toList());
        return new PageImpl<SearchBookPageResultDto>(booksDTOList,paginationRequest,books.getTotalElements());
    }

    @Cacheable("searchAuthorTypeahead")
    public List<SearchAuthorTypeaheadDto> searchAuthorTypeahead(Integer size,String query){
        log.info("Search authors like '{}' with maximum response size {} ",query,size);
        if(size>100||size<1){
            log.info("Size is out of range 1-100. Set maximum size to 100");
            size=100;
        }
        List<AuthorEntity> authorEntities = searchAuthors(new PageRequest(0,size),query).getContent();

        return authorEntities.stream().map(authorEntity -> {
            SearchAuthorTypeaheadDto authors = new SearchAuthorTypeaheadDto();
            authors.setAuthorId(authorEntity.getAuthorId());
            authors.setAuthorName(generateFullName(authorEntity));
            return authors;
        }).collect(Collectors.toList());
    }

    public Page<SearchAuthorPageResultDto> searchAuthorAll(Pageable paginationRequest, String query){
        Page<AuthorEntity> authorEntities = searchAuthors(paginationRequest,query);

        List<SearchAuthorPageResultDto> authorsDtoList = authorEntities.getContent().stream().map(authorEntity -> {
            SearchAuthorPageResultDto authors = new SearchAuthorPageResultDto();
            authors.setAuthorId(authorEntity.getAuthorId());
            authors.setAuthorName(generateFullName(authorEntity));
            return authors;
        }).collect(Collectors.toList());

        return new PageImpl<SearchAuthorPageResultDto>(authorsDtoList,paginationRequest,authorEntities.getTotalElements());
    }

    private Page<SimpleBookEntity> searchBooks(Pageable paginationRequest, String query){
        Page<SimpleBookEntity> pages = simpleBookRepository.findAllByTitleContaining(query, paginationRequest);
        log.info("{} books finded ",pages.getNumberOfElements());
        return pages;
    }


    private Page<AuthorEntity> searchAuthors(Pageable paginationRequest, String query){
        Page<AuthorEntity> pages = authorRepository.getAllByFirstNameOrMiddleNameOrLastNameContainingOrderByLastName(paginationRequest,query,query,query);
        log.info("{} authors finded ",pages.getNumberOfElements());
        return pages;
    }

    private String generateFullName(AuthorEntity authorEntity){
        String s = String.format("%s %s %s",authorEntity.getLastName(),authorEntity.getFirstName(),authorEntity.getMiddleName());
        return s.replace("  ", " ");
    }

}
