package com.liberty.controller;

import com.liberty.facade.RecommendationFacade;
import com.liberty.model.SimpleBookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by user on 09.05.2017.
 */
@Controller
public class BooksController {

    @Autowired
    private RecommendationFacade facade;

    @RequestMapping("/book/{bookId}")
    public String book(@PathVariable Long bookId, Model model) {
        SimpleBookEntity book = facade.getBook(bookId);
        model.addAttribute("book", book);
        model.addAttribute("recommendations", facade.getRecommendations(bookId));
        model.addAttribute("authors", facade.getAuthor(bookId));
        return "book";
    }
}
