package com.liberty.social.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by user on 09.05.2017.
 */
@Controller
public class AdministratorSecuredController {

    @PreAuthorize("hasAuthority('MODERATOR')")
    @RequestMapping(value="/admin", method = RequestMethod.GET)
    public String admin(Model model) {

        return "admin";

    }
}
