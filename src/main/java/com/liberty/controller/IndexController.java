package com.liberty.controller;

import com.liberty.service.DataMinerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * User: Dimitr
 * Date: 26.04.2017
 * Time: 8:59
 */
@Controller
public class IndexController {

    @Autowired
    private DataMinerService dataMinerService;

    @RequestMapping("/")
    public String greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "index";
    }

    @RequestMapping("/book/{bookId}")
    public String book(@PathVariable Long bookId, Model model) {
        return "index";
    }
}
