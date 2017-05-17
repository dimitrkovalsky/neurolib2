package com.liberty.controller;

import com.liberty.common.CollectionDivider;
import com.liberty.dto.TwoColumnDto;
import com.liberty.facade.RecommendationFacade;
import com.liberty.model.BookDescriptionEntity;
import com.liberty.model.SimpleBookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by user on 09.05.2017.
 */
@Controller
public class BookController {

    @Autowired
    private RecommendationFacade facade;

    @RequestMapping("/book/{bookId}")
    public String book(@PathVariable Long bookId, Model model) {
        SimpleBookEntity book = facade.getBook(bookId);
        model.addAttribute("book", book);
        BookDescriptionEntity bookDescription = facade.getBookDescription(bookId);
        String description = bookDescription == null ? "" : bookDescription.getDescription();
        model.addAttribute("description", description);
        model.addAttribute("recommendations", facade.getRecommendations(bookId));
        model.addAttribute("authors", facade.getAuthor(bookId));
        model.addAttribute("genres", facade.getGenres(bookId));
        model.addAttribute("flibustaComments", facade.getComments(bookId));
        return "book";
    }

    @RequestMapping("/book/random")
    public String randomBooks(Model model) {
        List<SimpleBookEntity> books = facade.getRandomBooks(10);
        TwoColumnDto<SimpleBookEntity> divided = CollectionDivider.divide(books);
        model.addAttribute("left", divided.getLeftColumn());
        model.addAttribute("right", divided.getRightColumn());

        return "book-cards";
    }

    @RequestMapping("/author/{authorId}/book")
    public String authorBooks(@PathVariable(name = "authorId") Integer authorId, Model model) {
        List<SimpleBookEntity> books = facade.getByAuthor(authorId);
        model.addAttribute("books", books);

        return "book-list";
    }

    @RequestMapping("/genre/{genreId}")
    public String GenreBooks(@PathVariable Integer genreId, Model model) {
        List<SimpleBookEntity> books = facade.getByGenre(genreId);
        model.addAttribute("books", books);

        return "book-list";
    }
}
