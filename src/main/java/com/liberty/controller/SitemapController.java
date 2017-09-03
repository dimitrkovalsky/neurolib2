package com.liberty.controller;

import com.liberty.component.SitemapHandler;
import com.liberty.service.impl.SitemapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by user on 02.09.2017.
 */

@Controller
public class SitemapController {

    @Autowired
    private SitemapService sitemapService;

    @RequestMapping("/sitemap.xml")
    @ResponseBody
    public String getSitemapIndex() {
         return sitemapService.getIndexSitemap();
    }

    @RequestMapping("/sitemaps/{type}-{page}.xml")
    @ResponseBody
    public String getSitemap(@PathVariable("type") String type, @PathVariable("page") Integer page) {
        return sitemapService.getSitemapAtPage(type, page);
    }
}
