package com.liberty.controller;

import com.liberty.facade.AuthorFacade;
import com.liberty.facade.GenreFacade;
import com.liberty.facade.RecommendationFacade;
import com.liberty.model.AuthorEntity;
import com.liberty.model.GenreEntity;
import com.liberty.repository.BookAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

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
    @Autowired
    private RecommendationFacade recoFacade;

    @Autowired
    private GenreFacade genreFacade;

    @RequestMapping(path = "/author/{authorId}", method = RequestMethod.GET)
    public String getOne(@PathVariable Long authorId, Model model) {
        AuthorEntity author = authorFacade.getAuthor(authorId);
        model.addAttribute("author", author);
        model.addAttribute("biography", authorFacade.getBiography(authorId));
        model.addAttribute("collections", authorFacade.getAllBooksGrouped(authorId));
        setGenreSideBar(model);
        List<AuthorEntity> similarAuthors = recoFacade.getSimilarAuthors(author);
        model.addAttribute("similar", similarAuthors);
        return "author";
    }

    @RequestMapping("/author/random")
    public String randomAuthors(Model model) {
        List<AuthorEntity> authors = authorFacade.getRandomAuthors();
        model.addAttribute("authors", authors);
        setGenreSideBar(model);
        return "author-list";
    }

    private void setGenreSideBar(Model model) {
        Map<String, List<GenreEntity>> genresMap = genreFacade.getAllGenresGrouped();
        model.addAttribute("genres", genresMap);
    }
}
