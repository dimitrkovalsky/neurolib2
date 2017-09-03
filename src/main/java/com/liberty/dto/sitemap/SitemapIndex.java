package com.liberty.dto.sitemap;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * Created by user on 03.09.2017.
 */
@XStreamAlias("sitemapindex")
public class SitemapIndex extends SitemapList<Sitemap> implements Serializable {

    private static final long serialVersionUID = -6641748820414216379L;
}
