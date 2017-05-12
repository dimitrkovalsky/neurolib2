package com.liberty.service.impl;

import com.liberty.dao.SearchBookDAO;
import com.liberty.model.FullBookEntity;
import com.liberty.repository.RecoBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by user on 12.05.2017.
 */
@Service
public class SearchServiceImpl {

    @Autowired
    private RecoBookRepository repository;

    public List<SearchBookDAO> searchBook(Integer size, String query){
        if(size>100||size<1){
            size=100;
        }
        List<FullBookEntity> books = repository.findAllByDeletedFalseAndTitleContainingOrderByRateDesc(query,  new PageRequest(0, size));
        return books.stream().map(fullBookEntity -> new SearchBookDAO(fullBookEntity)).distinct().collect(Collectors.toList());
    }

}
