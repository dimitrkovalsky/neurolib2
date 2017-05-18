package com.liberty.controller;

import com.liberty.dto.BookShelfEntryDto;
import com.liberty.facade.RecommendationFacade;
import com.liberty.repository.UserBookshelfRepository;
import com.liberty.service.BookShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by user on 18.05.2017.
 */
@Controller
public class BookShelfController {

    @Autowired
    private BookShelfService bookShelfService;

    @RequestMapping(path = "/shelf", method = RequestMethod.GET)
    public String showShelf(Model model, Authentication authentication) {
        List<BookShelfEntryDto> booksInShelf = bookShelfService.getShelfEntry((Long)authentication.getPrincipal());
        model.addAttribute("booksInShelf", booksInShelf);
        return "shelf";
    }

    @RequestMapping(path = "/shelf", method = RequestMethod.POST)
    public String showShelf(Authentication authentication, @RequestParam("bookId") Long bookId) {
        bookShelfService.addBookToShelf((Long)authentication.getPrincipal(),bookId);
        return "redirect:/shelf";
    }

}
