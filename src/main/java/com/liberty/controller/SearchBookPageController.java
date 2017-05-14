package com.liberty.controller;

import com.liberty.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by user on 14.05.2017.
 */
@Controller
public class SearchBookPageController {

    @Autowired
    private SearchService searchServiceImpl;

    @RequestMapping(value = "/searchbook", method = RequestMethod.GET)
    public String searchBook(Pageable pageable, @RequestParam(value = "query") String query, Model model){
        model.addAttribute("books",searchServiceImpl.searchBookAll(pageable,query));
        model.addAttribute("query", query);
        return "booksearch";
    }
}
