package com.liberty.controller;

import com.liberty.dto.AuthorBornDto;
import com.liberty.dto.LoaderDto;
import com.liberty.service.impl.AuthorBornService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * User: Dimitr
 * Date: 25.04.2017
 * Time: 8:05
 */
@RestController
public class AuthorBornController {

    @Autowired
    private AuthorBornService service;

    @RequestMapping(path = "/api/authorborn", method = RequestMethod.POST)
    public LoaderDto<List<AuthorBornDto>> get(@RequestParam(value = "page",required = false,defaultValue = "0") Integer page,
                         @RequestParam(value = "date",required = false) String date) {

        return service.loadAuthorsBornAt( date,  page);
    }

}
