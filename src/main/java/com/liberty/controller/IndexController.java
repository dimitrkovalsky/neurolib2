package com.liberty.controller;

import com.liberty.facade.RecommendationFacade;
import com.liberty.model.SimpleBookEntity;
import com.liberty.service.DataMinerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * User: Dimitr
 * Date: 26.04.2017
 * Time: 8:59
 */
@Controller
public class IndexController {

    @Autowired
    private DataMinerService dataMinerService;

    @Autowired
    private RecommendationFacade facade;

    @RequestMapping("/")
    public String greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "index";
    }

    @RequestMapping("/book/{bookId}")
    public String book(@PathVariable Long bookId, Model model) {
        SimpleBookEntity book = facade.getBook(bookId);
        model.addAttribute("book", book);
        model.addAttribute("recommendations", facade.getRecommendations(bookId));
        model.addAttribute("authors", facade.getAuthor(bookId));
        return "book";
    }
}