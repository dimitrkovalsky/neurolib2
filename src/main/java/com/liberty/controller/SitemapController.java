package com.liberty.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by user on 02.09.2017.
 */

@Controller
public class SitemapController {

    @RequestMapping("/sitemap.xml")
    @ResponseBody
    public String getSitemapIndex() {
        return "sitemapindex";
    }

    @RequestMapping("/sitemaps/{type}-{page}.xml")
    @ResponseBody
    public String getSitemap(@PathVariable("type") String type, @PathVariable("page") Integer page) {
        return type+" "+page;
    }
}
