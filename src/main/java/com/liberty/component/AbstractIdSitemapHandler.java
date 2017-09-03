package com.liberty.component;

import com.liberty.dto.sitemap.SitemapLink;
import com.liberty.dto.sitemap.SitemapUrl;
import com.liberty.dto.sitemap.SitemapUrlList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 03.09.2017.
 */
public abstract class AbstractIdSitemapHandler<T> implements SitemapHandler  {

    public abstract Float getPriority();

    public abstract String getChangeFrequency();

    public String getDomainName(){
        return "http://192.168.1.2:7777";
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

    public abstract String formatUrl(Long id) ;

    public abstract Page<T> getPageWithData(Integer page, Integer size);

    public abstract Long getEntryId(T t);

    @Override
    public List<SitemapLink> getSitemapLinks() {
        Page<T> page =  getPageWithData(0,5000);
        List<SitemapLink> links = new ArrayList<>();
        Integer totalPages = page.getTotalPages();
        for (int i = 0; i <totalPages; i++) {
            SitemapLink sitemapLink = new SitemapLink();
            sitemapLink.setLocation(formatSitemapLinks(i));
            links.add(sitemapLink);
        }
        return links;
    }

    @Override
    public SitemapUrlList getLinksAtPage(Integer page) {
        Page<T> simpleBookEntities =  getPageWithData(page,5000);
        SitemapUrlList sitemapUrlList = new SitemapUrlList();
        simpleBookEntities.forEach(source -> {
            SitemapUrl sitemapUrl = new SitemapUrl();
            sitemapUrl.setPriority(getPriority());
            sitemapUrl.setChangeFrequency(getChangeFrequency());
            sitemapUrl.setLocation(formatUrl(getEntryId(source)));
            sitemapUrlList.add(sitemapUrl);
        });
        return sitemapUrlList;
    }

}
