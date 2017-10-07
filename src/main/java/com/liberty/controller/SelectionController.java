package com.liberty.controller;

import com.liberty.common.PageWrapper;
import com.liberty.dto.SearchBookTypeaheadDto;
import com.liberty.dto.SelectionBookAdditionInfoDto;
import com.liberty.dto.SelectionBookListDto;
import com.liberty.facade.GenreFacade;
import com.liberty.model.GenreEntity;
import com.liberty.model.SelectionEntity;
import com.liberty.model.SimpleBookEntity;
import com.liberty.service.impl.SelectionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Created by user on 13.09.2017.
 */

@Controller
public class SelectionController {

    @Autowired
    private GenreFacade genreFacade;

    @Autowired
    private SelectionServiceImpl selectionService;

    @RequestMapping("/selection/{selectionId}")
    public String getSelectionInfo(@PathVariable Long selectionId, Model model, Pageable pageable,
            HttpServletRequest request) {
        SelectionEntity selectionEntity = selectionService.loadSelectionInfo(selectionId);
        model.addAttribute("selectionInfo", selectionEntity);

        Map<String, List<GenreEntity>> genresMap = genreFacade.getAllGenresGrouped();
        model.addAttribute("genres", genresMap);

        Page<SelectionBookAdditionInfoDto> page = selectionService.loadSelectionBooksInfo(selectionId, pageable);
        PageWrapper<SelectionBookAdditionInfoDto> pageWrapper = new PageWrapper<>(page, request);
        model.addAttribute("page", pageWrapper);

        return "selection";
    }

    @RequestMapping(value = "/selection/{selectionId}", method = RequestMethod.POST)
    @ResponseBody
    public List getSelectionBooks(@PathVariable Long selectionId, Pageable pageable) {
        List<SimpleBookEntity> bookEntities = selectionService.loadBooksInSelection(pageable, selectionId).getContent();
        return bookEntities.parallelStream().map(simpleBookEntity -> {
            SearchBookTypeaheadDto bookTypeaheadDto = new SearchBookTypeaheadDto();
            bookTypeaheadDto.setTitle(simpleBookEntity.getTitle());
            bookTypeaheadDto.setBookId(simpleBookEntity.getBookId());
            return bookTypeaheadDto;
        }).collect(Collectors.toList());
    }

    @RequestMapping("/selections/top")
    public String getSelectionTop(Model model, Pageable pageable, HttpServletRequest request) {
        Page<SelectionBookListDto> page = selectionService.loadSelectionsList(pageable);
        PageWrapper<SelectionBookListDto> pageWrapper = new PageWrapper<>(page, request);
        model.addAttribute("page", pageWrapper);
        Map<String, List<GenreEntity>> genresMap = genreFacade.getAllGenresGrouped();
        model.addAttribute("genres", genresMap);
        return "selection-list";
    }
}
