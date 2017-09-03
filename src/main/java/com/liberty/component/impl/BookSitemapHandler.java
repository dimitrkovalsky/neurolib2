package com.liberty.component.impl;

import com.liberty.component.AbstractIdSitemapHandler;
import com.liberty.model.SimpleBookEntity;
import com.liberty.repository.SimpleBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;


/**
 * Created by user on 03.09.2017.
 */
@Component
public class BookSitemapHandler extends AbstractIdSitemapHandler<SimpleBookEntity> {

    @Autowired
    private SimpleBookRepository bookRepository;

    @Override
    public String getSitemapType() {
        return "books";
    }

    @Override
    public Float getPriority(){
        return 0.2f;
    }

    @Override
    public String getChangeFrequency(){
        return "weekly";
    }

    @Override
    public String formatUrl(Long id){
        return new StringBuilder()
                .append(getDomainName())
                .append("/book/")
                .append(id)
                .toString();
    }

    @Override
    public Page<SimpleBookEntity> getPageWithData(Integer page, Integer size) {
        return bookRepository.findAllByDeletedIsFalse(new PageRequest(page,size));
    }

    @Override
    public Long getEntryId(SimpleBookEntity entity) {
        return entity.getBookId();
    }


}
