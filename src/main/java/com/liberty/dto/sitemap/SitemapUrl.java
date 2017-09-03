package com.liberty.dto.sitemap;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by user on 03.09.2017.
 */
@Data
@XStreamAlias("url")
public class SitemapUrl implements Serializable{

    private static final long serialVersionUID = 2454965394503432254L;

    //<loc>https://www.livelib.ru/books/added</loc>
    @XStreamAlias("loc")
    private String location;

   // <changefreq>hourly</changefreq>
    @XStreamAlias("changefreq")
    private String changeFrequency;

    //<lastmod>2013-11-18</lastmod>
    @XStreamAlias("lastmod")
    private String lastModification;

   // <priority>0.75</priority>
    @XStreamAlias("priority")
    private Float priority;
}
