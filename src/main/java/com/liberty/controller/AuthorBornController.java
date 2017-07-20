package com.liberty.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * User: Dimitr
 * Date: 25.04.2017
 * Time: 8:05
 */
@RestController
public class AuthorBornController {

    @RequestMapping(path = "/authorborn", method = RequestMethod.POST)
    public String get(@RequestParam(value = "page",required = false) Long page,
                      @RequestParam(value = "date",required = false) String date) {
        return "{\"page\":"+page+",\"date\":\""+date+"\"}";
    }

}
