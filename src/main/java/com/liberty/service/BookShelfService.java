package com.liberty.service;

import com.liberty.dto.BookShelfEntryDto;

import java.util.List;

/**
 * Created by user on 18.05.2017.
 */
public interface BookShelfService {
    List<BookShelfEntryDto> getShelfEntry(Long userId);

    void addBookToShelf(Long userId, Long bookId);

    void deleteBookFromShelf(Long userId,Long bookId);

    void rateBook(Long userId,Long bookId, Integer rate);

}
