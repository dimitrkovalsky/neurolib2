package com.liberty.controller;

import com.liberty.common.PageWrapper;
import com.liberty.dto.SearchBookPageResultDTO;
import com.liberty.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class SearchBookPageController {

    @Autowired
    private SearchService searchServiceImpl;

    @RequestMapping(value = "/searchbook", method = RequestMethod.GET)
    public String searchBook(Pageable pageable, @RequestParam(value = "query") String query, Model model,HttpServletRequest request){
        Page<SearchBookPageResultDTO> searchPage = searchServiceImpl.searchBookAll(pageable,query);
        PageWrapper pageWrapper = new PageWrapper<>(searchPage,request);

        model.addAttribute("page",pageWrapper);
        model.addAttribute("query", query);
        return "booksearch";
    }
}
