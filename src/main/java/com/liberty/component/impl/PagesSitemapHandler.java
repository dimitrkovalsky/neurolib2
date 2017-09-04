package com.liberty.component.impl;

import com.liberty.component.SitemapHandler;
import com.liberty.dto.sitemap.SitemapLink;
import com.liberty.dto.sitemap.SitemapUrl;
import com.liberty.dto.sitemap.SitemapUrlList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by user on 04.09.2017.
 */
@Component
public class PagesSitemapHandler implements SitemapHandler {

    @Autowired
    private Environment environment;

    private SitemapUrlList sitemapUrlList = new SitemapUrlList();


    public String getDomainName(){
        return environment.getProperty("server.domain.name");
    }

    public String formatSitemapLinks(Integer page){
        return new StringBuilder()
                .append(getDomainName())
                .append("/sitemaps/")
                .append(getSitemapType())
                .append("-").append(page)
                .append(".xml")
                .toString();
    }

    @Override
    public String getSitemapType() {
        return "pages";
    }

    @Override
    public List<SitemapLink> getSitemapLinks() {
        List<SitemapLink> links = new ArrayList<>();
        SitemapLink sitemapLink = new SitemapLink();
        sitemapLink.setLocation(formatSitemapLinks(0));
        links.add(sitemapLink);
        return links;
    }

    @Override
    public SitemapUrlList getLinksAtPage(Integer page) {
        return sitemapUrlList;
    }

    private SitemapUrl generateUrl(String path){
        SitemapUrl url = new SitemapUrl();
        url.setLocation(getDomainName()+path);
        url.setChangeFrequency("daily");
        url.setPriority(0.8f);
        return url;
    }

    @PostConstruct
    private void prepareLinks(){
        sitemapUrlList.add(generateUrl("/"));
        sitemapUrlList.add(generateUrl("/genres"));
        sitemapUrlList.add(generateUrl("/book/random"));
        sitemapUrlList.add(generateUrl("/author/random"));
    }
}
