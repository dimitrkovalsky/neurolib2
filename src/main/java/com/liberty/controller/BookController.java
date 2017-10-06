package com.liberty.controller;

import com.liberty.common.CollectionDivider;
import com.liberty.dto.TwoColumnDto;
import com.liberty.facade.GenreFacade;
import com.liberty.facade.RecommendationFacade;
import com.liberty.model.BookCardEntity;
import com.liberty.model.BookDescriptionEntity;
import com.liberty.model.GenreEntity;
import com.liberty.model.SimpleBookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * Created by user on 09.05.2017.
 */
@Controller
public class BookController {

    @Autowired
    private RecommendationFacade facade;
    @Autowired
    private GenreFacade genreFacade;

    @RequestMapping("/book/{bookId}")
    public String book(@PathVariable Long bookId, Model model) {
        SimpleBookEntity book = facade.getBook(bookId);
        model.addAttribute("book", book);
        BookDescriptionEntity bookDescription = facade.getBookDescription(bookId);
        String description = bookDescription == null ? "" : bookDescription.getDescription();
        model.addAttribute("description", description);
        model.addAttribute("recommendations", facade.getRecommendations(bookId));
        model.addAttribute("authors", facade.getAuthor(bookId));
        model.addAttribute("bookGenres", facade.getGenres(bookId));
        model.addAttribute("flibustaComments", facade.getComments(bookId));
        setGenreSideBar(model);
        return "book";
    }

    @RequestMapping("/book/random")
    public String randomBooks(Model model) {
        List<BookCardEntity> books = facade.getRandomBooks(10);
        TwoColumnDto<BookCardEntity> divided = CollectionDivider.divide(books);
        model.addAttribute("left", divided.getLeftColumn());
        model.addAttribute("right", divided.getRightColumn());
        setGenreSideBar(model);
        return "book-cards";
    }

    @RequestMapping("/author/{authorId}/book")
    public String authorBooks(@PathVariable(name = "authorId") Long authorId, Model model) {
        List<SimpleBookEntity> books = facade.getByAuthor(authorId);
        model.addAttribute("books", books);
        setGenreSideBar(model);
        return "book-list";
    }

    private void setGenreSideBar(Model model) {
        Map<String, List<GenreEntity>> genresMap = genreFacade.getAllGenresGrouped();
        model.addAttribute("genres", genresMap);
    }
}
