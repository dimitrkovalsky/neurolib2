package com.liberty.controller;

import com.liberty.error.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by user on 11.05.2017.
 */
@Controller
public class NotFoundController {
    /*
    * This controller catch all requests except resources and other controllers then throw exception
    **/
    @RequestMapping(value = {"{path:(?!css|jquery|js|social).*$}",
                             "{path:(?!css|jquery|js|social).*$}/**"},headers = "Accept=text/html")
    public void notFoundController(HttpServletRequest request){
        throw new NotFoundException( request.getRequestURI());
    }
}
