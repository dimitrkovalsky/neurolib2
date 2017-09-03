package com.liberty.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by user on 02.09.2017.
 */

@Controller
public class SitemapController {
    @RequestMapping("/sitemap.xml}")
    public String getSitemapIndex() {
        return "genres";
    }
}
