package com.liberty.service.impl;

import com.liberty.dto.BookShelfEntryDto;
import com.liberty.error.ValidationException;
import com.liberty.facade.RecommendationFacade;
import com.liberty.model.SimpleBookEntity;
import com.liberty.model.UserBookshelfEntity;
import com.liberty.repository.UserBookshelfRepository;
import com.liberty.repository.UserRepository;
import com.liberty.service.BookShelfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by user on 18.05.2017.
 */

@Service
@Slf4j
public class BookShelfServiceImpl implements BookShelfService {

    private static final Integer MAX_RATE = 5;

    @Autowired
    private UserBookshelfRepository bookshelf;

    @Autowired
    private RecommendationFacade facade;

    @Autowired
    private UserRepository userRepository;

    public List<BookShelfEntryDto> getShelfEntry(Long userId){
        List<UserBookshelfEntity> userBookshelfEntities  = bookshelf.getAllByUserId(userId);
        return userBookshelfEntities.stream().map(userBookshelfEntity -> {
            BookShelfEntryDto bookShelfEntryDto = new BookShelfEntryDto();
            SimpleBookEntity book = facade.getBook(userBookshelfEntity.getBookId());
            bookShelfEntryDto.setAuthors(facade.getAuthor(book.getBookId()));
            bookShelfEntryDto.setGenres(facade.getGenres(book.getBookId()));
            bookShelfEntryDto.setBook(book);
            bookShelfEntryDto.setUserBookRate(userBookshelfEntity.getRate());
            return bookShelfEntryDto;
        }).collect(Collectors.toList());
    }


    public void addBookToShelf(Long userId, Long bookId){
        validateUserAndBook(userId,bookId);
        if(bookshelf.getOneByBookIdAndUserId( bookId, userId)!=null){
            throw new ValidationException("Book already added to user shelf");
        }
        addBookToShelfUnsafe(userId,bookId,null);
    }

    public void deleteBookFromShelf(Long userId,Long bookId){
        validateUserAndBook(userId,bookId);
         UserBookshelfEntity userBookshelfEntity = bookshelf.getOneByBookIdAndUserId(bookId,userId);
        if(userBookshelfEntity==null){
            throw new ValidationException("The book is not found on the shelf");
        }else {
            bookshelf.delete(userBookshelfEntity);
        }
    }

    public void rateBook(Long userId,Long bookId, Integer rate){
        validateUserAndBook(userId,bookId);
        if(rate>MAX_RATE&&rate<0){
            throw new ValidationException("Rate out of bounds");
        }
        UserBookshelfEntity userBookshelfEntity = bookshelf.getOneByBookIdAndUserId(bookId,userId);
        if(userBookshelfEntity==null){
            addBookToShelfUnsafe(userId, bookId, rate);
        }else {
            userBookshelfEntity.setRate(rate);
            bookshelf.save(userBookshelfEntity);
        }

    }

    private void validateUserAndBook(Long userId, Long bookId){
        if(facade.getBook(bookId)==null){
            throw new ValidationException("Book not found");
        }
        if(userRepository.getOne(userId)==null){
            throw new ValidationException("User not found");
        }
    }

    private UserBookshelfEntity addBookToShelfUnsafe(Long userId, Long bookId,Integer rate){
        UserBookshelfEntity bookshelfEntity = new UserBookshelfEntity();
        bookshelfEntity.setBookId(bookId);
        bookshelfEntity.setUserId(userId);
        return bookshelf.save(bookshelfEntity);
    }
}
