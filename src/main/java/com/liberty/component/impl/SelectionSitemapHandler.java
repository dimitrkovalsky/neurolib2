package com.liberty.component.impl;

import com.liberty.component.AbstractIdSitemapHandler;
import com.liberty.model.SelectionEntity;
import com.liberty.repository.SelectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

/**
 * Created by user on 03.09.2017.
 */
@Component
public class SelectionSitemapHandler extends AbstractIdSitemapHandler<SelectionEntity> {

    @Autowired
    private SelectionRepository selectionRepository;

    @Override
    public String getSitemapType() {
        return "selections";
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
                .append("/selection/")
                .append(id)
                .toString();
    }

    @Override
    public Page<SelectionEntity> getPageWithData(Integer page, Integer size) {
        return selectionRepository.findAll(new PageRequest(page,size));
    }

    @Override
    public Long getEntryId(SelectionEntity entity) {
        return (long)entity.getSelectionId();
    }
}
