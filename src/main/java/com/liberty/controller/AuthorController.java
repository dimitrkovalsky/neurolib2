package com.liberty.controller;

import com.liberty.facade.AuthorFacade;
import com.liberty.model.AuthorEntity;
import com.liberty.repository.BookAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * User: Dimitr
 * Date: 25.04.2017
 * Time: 8:05
 */
@Controller
public class AuthorController {

    @Autowired
    private BookAuthorRepository repository;

    @Autowired
    private AuthorFacade authorFacade;

    @RequestMapping(path = "/author/{authorId}", method = RequestMethod.GET)
    public String getOne(@PathVariable Integer authorId, Model model) {
        model.addAttribute("author", authorFacade.getAuthor(authorId));
        model.addAttribute("biography", authorFacade.getBiography(authorId));
        model.addAttribute("collections", authorFacade.getAllBooksGrouped(authorId));
        return "author";
    }

    @RequestMapping("/author/random")
    public String randomAuthors(Model model) {
        List<AuthorEntity> authors = authorFacade.getRandomAuthors();
        model.addAttribute("authors", authors);

        return "author-list";
    }
}
