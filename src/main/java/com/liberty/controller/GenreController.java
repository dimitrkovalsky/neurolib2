package com.liberty.controller;

import com.liberty.common.CollectionDivider;
import com.liberty.common.RandomPicker;
import com.liberty.dto.TwoColumnDto;
import com.liberty.facade.GenreFacade;
import com.liberty.facade.RecommendationFacade;
import com.liberty.model.BookCardEntity;
import com.liberty.model.GenreEntity;
import com.liberty.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

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
    private RecommendationFacade recommendationFacade;
    @Autowired
    private GenreRepository genreRepository;

    // todo: optimise loading
    @RequestMapping("/genre/{genreId}")
    public String getGenreBooks(@PathVariable Integer genreId, Model model) {
        GenreEntity currentGenre = genreRepository.findOne(genreId);
        setGenreData(model, currentGenre);
        setGenreSideBar(model);
        return "genres";
    }

    @RequestMapping("/genres")
    public String getAllGenres(Model model) {
        Map<String, List<GenreEntity>> genresMap = genreFacade.getAllGenresGrouped();
        model.addAttribute("genres", genresMap);
        GenreEntity currentGenre = RandomPicker.pickRandomElement(genresMap);
        setGenreData(model, currentGenre);
        return "genres";
    }

    private void setGenreData(Model model, GenreEntity currentGenre) {
        List<BookCardEntity> randomBooks = genreFacade.getByGenre(currentGenre.getGenreId());
        TwoColumnDto<BookCardEntity> divided = CollectionDivider.divide(randomBooks);
        model.addAttribute("left", divided.getLeftColumn());
        model.addAttribute("right", divided.getRightColumn());
        model.addAttribute("currentGenre", currentGenre);
    }

    private void setGenreSideBar(Model model) {
        Map<String, List<GenreEntity>> genresMap = genreFacade.getAllGenresGrouped();
        model.addAttribute("genres", genresMap);
    }
}
