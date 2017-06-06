package com.liberty.controller;

import com.liberty.facade.GenreFacade;
import com.liberty.model.GenreEntity;
import com.liberty.service.QuoteService;
import com.liberty.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private GenreFacade genreFacade;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private QuoteService quoteService;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String index(Model model) {
        setGenreSideBar(model);
        model.addAttribute("topReadable", ratingService.getMostReadable());
        model.addAttribute("dayQuote", quoteService.getQuoteOfTheDay());
        return "index";
    }

    private void setGenreSideBar(Model model) {
        Map<String, List<GenreEntity>> genresMap = genreFacade.getAllGenresGrouped();
        model.addAttribute("genres", genresMap);
    }
}
