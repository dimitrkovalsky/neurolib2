package com.liberty.component;

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
public class BookSitemapHandler extends AbstractIdSitemapHandler<SimpleBookEntity>{

    @Autowired
    private SimpleBookRepository bookRepository;

    @Override
    public String getSitemapType() {
        return "books";
    }

    private String getDomainName(){
        return "http://192.168.1.2:7777";
    }

    @Override
    Float getPriority(){
        return 0.2f;
    }

    @Override
    String getChangeFrequency(){
        return "weekly";
    }

    @Override
    String formatSitemapLinks(Integer page){
       return new StringBuilder()
               .append(getDomainName())
               .append("/sitemaps/")
               .append(getSitemapType())
               .append("-").append(page)
               .append(".xml")
               .toString();
    }

    @Override
    String formatUrl(Long id){
        return new StringBuilder()
                .append(getDomainName())
                .append("/book/")
                .append(id)
                .toString();
    }

    @Override
    Page<SimpleBookEntity> getPageWithData(Integer page, Integer size) {
        return bookRepository.findAllByDeletedIsFalse(new PageRequest(0,50000));
    }

    @Override
    Long getEntryId(SimpleBookEntity entity) {
        return entity.getBookId();
    }


}
