package com.liberty.controller;

import com.liberty.dto.SearchAuthorTypeaheadDto;
import com.liberty.dto.SearchBookTypeaheadDto;
import com.liberty.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by user on 12.05.2017.
 */
@RestController
public class SearchTypeaheadController {

    @Autowired
    private SearchService searchServiceImpl;

    @RequestMapping(value = "/searchbooktypeahead", method = RequestMethod.GET)
    public List<SearchBookTypeaheadDto> searchBookTypeahead(@RequestParam(value = "size", required = false,defaultValue = "5") Integer size, @RequestParam (value = "q") String query){
        return searchServiceImpl.searchBookTypeahead(size,query);
    }

    @RequestMapping(value = "/searchauthortypeahead", method = RequestMethod.GET)
    public List<SearchAuthorTypeaheadDto> searchAuthorTypeahead(@RequestParam(value = "size", required = false,defaultValue = "5") Integer size, @RequestParam (value = "q") String query){
        return searchServiceImpl.searchAuthorTypeahead(size,query);
    }
}
