package com.liberty.component;

import com.liberty.dto.sitemap.SitemapLink;
import com.liberty.dto.sitemap.SitemapUrlList;

import java.util.List;

/**
 * Created by user on 03.09.2017.
 */
public interface SitemapHandler {

    String getSitemapType();

    List<SitemapLink> getSitemapLinks();

    SitemapUrlList getLinksAtPage(Integer page);
}
