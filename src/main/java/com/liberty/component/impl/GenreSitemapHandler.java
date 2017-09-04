package com.liberty.component.impl;

import com.liberty.component.AbstractIdSitemapHandler;
import com.liberty.model.GenreEntity;
import com.liberty.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

/**
 * Created by user on 04.09.2017.
 */
@Component
public class GenreSitemapHandler extends AbstractIdSitemapHandler<GenreEntity> {

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public String getSitemapType() {
        return "genres";
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
                .append("/genre/")
                .append(id)
                .toString();
    }

    @Override
    public Page<GenreEntity> getPageWithData(Integer page, Integer size) {
        return genreRepository.findAll(new PageRequest(page,size));
    }

    @Override
    public Long getEntryId(GenreEntity entity) {
        return (long)entity.getGenreId();
    }


}
