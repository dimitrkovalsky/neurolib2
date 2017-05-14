package com.liberty.service.impl;

import com.liberty.dto.SearchBookDTO;
import com.liberty.model.FullBookEntity;
import com.liberty.repository.FullBookRepository;
import com.liberty.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    public List<SearchBookDTO> searchBook(Integer size, String query){
        log.info("Search books in title like '{}' with maximum response size {} ",query,size);
        if(size>100||size<1){
            log.info("Size is out of range 1-100. Set maximum size to 100");
            size=100;
        }
        List<FullBookEntity> books = repository.findAllByDeletedFalseAndTitleContainingOrderByRateDesc(query,  new PageRequest(0, size));
        log.info("Find {} books in base",books.size());
        return books.stream().map(fullBookEntity -> new SearchBookDTO(fullBookEntity)).distinct().collect(Collectors.toList());
    }

}
