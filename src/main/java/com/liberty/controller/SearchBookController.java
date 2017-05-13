package com.liberty.controller;

import com.liberty.dto.SearchBookDTO;
import com.liberty.service.impl.SearchServiceImpl;
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
public class SearchBookController {

    @Autowired
    private SearchServiceImpl searchServiceImpl;

    @RequestMapping(value = "/searchbook", method = RequestMethod.GET)
    public List<SearchBookDTO> searchBook(@RequestParam(value = "size", required = false,defaultValue = "5") Integer size, @RequestParam (value = "q") String query){
        return searchServiceImpl.searchBook(size,query);
    }
}
