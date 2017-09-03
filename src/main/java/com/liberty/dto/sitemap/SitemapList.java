package com.liberty.dto.sitemap;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 03.09.2017.
 */
public class SitemapList<T> {

    @XStreamImplicit
    private List<T> sitemapList = new ArrayList<>();

    @XStreamAsAttribute
    @XStreamAlias("xmlns")
    private final String XMLNS = "http://www.sitemaps.org/schemas/sitemap/0.9";

    public void add(T entry){
        sitemapList.add(entry);
    }

}
