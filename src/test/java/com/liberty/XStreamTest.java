package com.liberty;

import com.liberty.dto.sitemap.Sitemap;
import com.liberty.dto.sitemap.SitemapIndex;
import com.liberty.dto.sitemap.SitemapUrl;
import com.liberty.dto.sitemap.SitemapUrlList;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.Annotations;
import org.junit.Test;

/**
 * Created by user on 03.09.2017.
 */

public class XStreamTest {

    @Test
    public void testSerializer(){
        XStream xstream = new XStream(); // require XPP3 library
        Annotations.configureAliases(xstream, SitemapUrlList.class);
        Annotations.configureAliases(xstream, SitemapUrl.class);
        Annotations.configureAliases(xstream, Sitemap.class);
        Annotations.configureAliases(xstream, SitemapIndex.class);
        // Serialize
        SitemapUrl url = new SitemapUrl();
        url.setChangeFrequency("daily");
        url.setLastModification("10-05-2013");
        url.setLocation("http://samolisov.blogspot.com/2007/11/xstream-java-xml.html");
        url.setPriority(0.4f);

        SitemapUrlList urlSet = new SitemapUrlList();

        urlSet.add(url);
        String xml = xstream.toXML(urlSet);
        System.out.println(xml);


        SitemapIndex sitemapIndex = new SitemapIndex();
        Sitemap sitemap = new Sitemap();
        sitemap.setLocation("http://samolisov.blogspot.com/2007/11/xstream-java-xml.html");
        sitemapIndex.add(sitemap);
        System.out.println(xstream.toXML(sitemapIndex));
    }
}
