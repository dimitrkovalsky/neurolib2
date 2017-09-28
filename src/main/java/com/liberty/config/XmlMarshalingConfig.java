package com.liberty.config;

import com.liberty.dto.sitemap.SitemapIndexLinkList;
import com.liberty.dto.sitemap.SitemapLink;
import com.liberty.dto.sitemap.SitemapUrl;
import com.liberty.dto.sitemap.SitemapUrlList;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.Annotations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by user on 04.09.2017.
 */
@Configuration
public class XmlMarshalingConfig {

    @Bean
    public XStream getXstream(){
        XStream xstream = new XStream(); // require XPP3 library
        Annotations.configureAliases(xstream, SitemapUrlList.class);
        Annotations.configureAliases(xstream, SitemapUrl.class);
        Annotations.configureAliases(xstream, SitemapLink.class);
        Annotations.configureAliases(xstream, SitemapIndexLinkList.class);
        return xstream;
    }


}
