package com.liberty.controller;

import com.liberty.facade.GenreFacade;
import com.liberty.model.GenreEntity;
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

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String getOne(Model model) {
        setGenreSideBar(model);
        return "index";
    }

    private void setGenreSideBar(Model model) {
        Map<String, List<GenreEntity>> genresMap = genreFacade.getAllGenresGrouped();
        model.addAttribute("genres", genresMap);
    }
}
