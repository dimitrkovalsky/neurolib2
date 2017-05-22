package com.liberty.controller;

import com.liberty.common.CollectionDivider;
import com.liberty.dto.TwoColumnDto;
import com.liberty.facade.GenreFacade;
import com.liberty.model.BookCardEntity;
import com.liberty.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * User: Dimitr
 * Date: 22.05.2017
 * Time: 8:44
 */
@Controller
public class GenreController {

    @Autowired
    private GenreFacade genreFacade;
    @Autowired
    private GenreRepository genreRepository;

    @RequestMapping("/genre/{genreId}")
    public String GenreBooks(@PathVariable Integer genreId, Model model) {
        List<BookCardEntity> books = genreFacade.getByGenre(genreId);
        TwoColumnDto<BookCardEntity> divided = CollectionDivider.divide(books);
        model.addAttribute("left", divided.getLeftColumn());
        model.addAttribute("right", divided.getRightColumn());
        model.addAttribute("genre", genreRepository.findOne(genreId));
        return "genre-books";
    }
}
