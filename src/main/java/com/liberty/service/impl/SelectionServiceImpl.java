package com.liberty.service.impl;

import com.liberty.dto.SelectionBookAdditionInfoDto;
import com.liberty.dto.SelectionBookListDto;
import com.liberty.facade.RecommendationFacade;
import com.liberty.model.SelectionBooksEntity;
import com.liberty.model.SelectionEntity;
import com.liberty.model.SimpleBookEntity;
import com.liberty.repository.SelectionBookRepository;
import com.liberty.repository.SelectionRepository;
import com.liberty.repository.SimpleBookRepository;
import com.liberty.repository.UserBookshelfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;



/**
 * Created by user on 14.09.2017.
 */
@Service
public class SelectionServiceImpl {

    @Autowired
    private RecommendationFacade facade;

    @Autowired
    private UserBookshelfRepository bookshelfRepository;

    @Autowired
    private SelectionRepository selectionRepository;

    @Autowired
    private SimpleBookRepository simpleBookRepository;

    @Autowired
    private SelectionBookRepository selectionBookRepository;

    public Page<SelectionBookListDto> loadSelectionsList(Pageable pageable){
        Page<SelectionEntity> selectionEntities = selectionRepository.findAll(pageable);
        return selectionEntities.map(source -> {
            SelectionBookListDto bookListDto = new SelectionBookListDto();
            bookListDto.setSelection(source);
            Page<SimpleBookEntity> simpleBookEntities  = loadBooksInSelection(new PageRequest(0,15),source.getSelectionId());

            bookListDto.setBooks(simpleBookEntities.getContent());
            return bookListDto;
        });
    }
    public Page<SimpleBookEntity> loadBooksInSelection(Pageable pageable, Long selectionId){
        return simpleBookRepository.findAllBySelectionId(selectionId,pageable);
    }

    public Page<SelectionBookAdditionInfoDto> loadSelectionBooksInfo(Long selectionId, Pageable pageable){
         Page<SelectionBooksEntity> selectionBooksEntities = selectionBookRepository.findAllBySelectionIdAndNeurolibBookIdIsNotNull(selectionId, pageable);
         return selectionBooksEntities.map(book -> {
             SelectionBookAdditionInfoDto selectionBookAdditionInfoDto = new SelectionBookAdditionInfoDto();
             selectionBookAdditionInfoDto.setSelectionBooksEntity(book);
             selectionBookAdditionInfoDto.setAuthors(facade.getAuthor(book.getNeurolibBookId()));
             selectionBookAdditionInfoDto.setBook(simpleBookRepository.findOne(book.getNeurolibBookId()));
             selectionBookAdditionInfoDto.setGenres(facade.getGenres(book.getNeurolibBookId()));
             Authentication auth = SecurityContextHolder.getContext().getAuthentication();
             if(!"anonymousUser".equals(auth.getPrincipal())) {
                 selectionBookAdditionInfoDto.setIsInShelf(bookshelfRepository.getOneByBookIdAndUserId(book.getNeurolibBookId(), (Long)auth.getPrincipal()) != null);
             }
             return selectionBookAdditionInfoDto;
         });
    }

    public SelectionEntity loadSelectionInfo(Long selectionId){
        return selectionRepository.findOne(selectionId);
    }

}
