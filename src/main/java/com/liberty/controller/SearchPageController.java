package com.liberty.controller;

import com.liberty.common.PageWrapper;
import com.liberty.dto.SearchAuthorPageResultDto;
import com.liberty.dto.SearchBookPageResultDto;
import com.liberty.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by user on 14.05.2017.
 */
@Controller
public class SearchPageController {

    @Autowired
    private SearchService searchServiceImpl;

    @RequestMapping(value = "/searchbook", method = RequestMethod.GET)
    public String searchBook(Pageable pageable, @RequestParam(value = "query") String query, Model model, HttpServletRequest request, Authentication auth){
        Page<SearchBookPageResultDto> searchPage = searchServiceImpl.searchBookAll(pageable,query,auth);
        PageWrapper pageWrapper = new PageWrapper<>(searchPage,request);

        model.addAttribute("page",pageWrapper);
        model.addAttribute("query", query);
        return "booksearch";
    }

    @RequestMapping(value = "/searchauthor", method = RequestMethod.GET)
    public String searchAuthor(Pageable pageable, @RequestParam(value = "query") String query, Model model, HttpServletRequest request){
        Page<SearchAuthorPageResultDto> searchPage = searchServiceImpl.searchAuthorAll(pageable,query);
        PageWrapper pageWrapper = new PageWrapper<>(searchPage,request);

        model.addAttribute("page",pageWrapper);
        model.addAttribute("query", query);
        return "authorsearch";
    }
}
