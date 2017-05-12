package com.liberty.dao;

import com.liberty.model.FullBookEntity;

/**
 * Created by user on 12.05.2017.
 */
public class SearchBookDAO {

    private Long bookId;

    private String title;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SearchBookDAO(){}

    public SearchBookDAO(FullBookEntity fullBookEntity){
        this.bookId = fullBookEntity.getBookId();
        this.title = fullBookEntity.getTitle();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SearchBookDAO)) return false;

        SearchBookDAO that = (SearchBookDAO) o;

        if (getBookId() != null ? !getBookId().equals(that.getBookId()) : that.getBookId() != null) return false;
        return getTitle() != null ? getTitle().equals(that.getTitle()) : that.getTitle() == null;
    }

    @Override
    public int hashCode() {
        int result = getBookId() != null ? getBookId().hashCode() : 0;
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        return result;
    }
}
