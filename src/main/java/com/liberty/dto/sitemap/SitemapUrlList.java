package com.liberty.dto.sitemap;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * Created by user on 03.09.2017.
 */
@XStreamAlias("urlset")
public class SitemapUrlList extends SitemapList<SitemapUrl> implements Serializable{

    private static final long serialVersionUID = 5449616526429931584L;
}
