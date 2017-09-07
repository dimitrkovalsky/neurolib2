package com.liberty.service;

/**
 * Created by user on 07.09.2017.
 */
public interface SitemapService {

    String getIndexSitemap();

    String getSitemapAtPage(String type, Integer page);
}
