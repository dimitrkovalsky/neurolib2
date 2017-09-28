package com.liberty.component.impl;

import com.liberty.component.AbstractIdSitemapHandler;
import com.liberty.model.AuthorEntity;
import com.liberty.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

/**
 * Created by user on 03.09.2017.
 */
@Component
public class AuthorSitemapHandler extends AbstractIdSitemapHandler<AuthorEntity> {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public String getSitemapType() {
        return "authors";
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
                .append("/author/")
                .append(id)
                .toString();
    }

    @Override
    public Page<AuthorEntity> getPageWithData(Integer page, Integer size) {
        return authorRepository.findAll(new PageRequest(page,size));
    }

    @Override
    public Long getEntryId(AuthorEntity entity) {
        return (long)entity.getAuthorId();
    }
}
