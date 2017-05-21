package com.liberty.controller;

import com.liberty.dto.BookShelfEntryDto;
import com.liberty.facade.RecommendationFacade;
import com.liberty.repository.UserBookshelfRepository;
import com.liberty.service.BookShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public void addBookToShelf(Authentication authentication, @RequestParam("bookId") Long bookId) {
        bookShelfService.addBookToShelf((Long)authentication.getPrincipal(),bookId);
        // return "redirect:/shelf";
    }

    @RequestMapping(value = "/shelf/{bookId}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteBookFromShelf(Authentication authentication, @PathVariable  Long bookId) {
        bookShelfService.deleteBookFromShelf((Long)authentication.getPrincipal(),bookId);
        return "ok";
    }
    @ResponseBody
    @RequestMapping(path = "/rate", method = RequestMethod.POST)
    public String rateBook(Authentication authentication, @RequestParam("bookId") Long bookId, Integer rate) {
        bookShelfService.rateBook((Long)authentication.getPrincipal(),bookId,rate);
        return "ok";
    }

}
