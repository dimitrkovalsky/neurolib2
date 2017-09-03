package com.liberty.service.impl;

import com.liberty.component.SitemapHandler;
import com.liberty.dto.sitemap.SitemapIndexLinkList;
import com.liberty.dto.sitemap.SitemapLink;
import com.liberty.dto.sitemap.SitemapUrl;
import com.liberty.dto.sitemap.SitemapUrlList;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.Annotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 03.09.2017.
 */
@Service
public class SitemapService {

    @Autowired
    private List<SitemapHandler> sitemapHandlers;

    @Autowired
    private XStream xStream;

    private Map<String, SitemapHandler> sitemapHandlerMap = new HashMap<>();

    @PostConstruct
    private void mapHandlers(){
        for(SitemapHandler handler: sitemapHandlers){
            sitemapHandlerMap.put(handler.getSitemapType(),handler);
        }
    }

    public String getIndexSitemap(){

        SitemapIndexLinkList sitemapIndexLinkList = new SitemapIndexLinkList();
        for(SitemapHandler handler: sitemapHandlers){
            sitemapIndexLinkList.addAll(handler.getSitemapLinks());
        }

        return xStream.toXML(sitemapIndexLinkList);
    }

    public String getSitemapAtPage(String type, Integer page){
        SitemapHandler handler = getSitemapHandler(type);
        SitemapUrlList sitemapUrlList = handler.getLinksAtPage(page);
        return xStream.toXML(sitemapUrlList);
    }

    private SitemapHandler getSitemapHandler(String type){
        return sitemapHandlerMap.get(type);
    }
}
