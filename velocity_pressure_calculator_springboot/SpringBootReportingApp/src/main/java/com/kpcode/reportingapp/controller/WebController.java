package com.kpcode.reportingapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * @Author kaveri
 * @create 11/04/21
 */


@Controller
public class WebController {

    /**
     * show login page
     *
     * @return
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
