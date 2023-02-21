package com.lec.spring.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class HomeController {
    public HomeController() {
        System.out.println(getClass().getName() + "()생성");
    }

    @RequestMapping("/vvine")
    public void vvine() {
    }

    @RequestMapping("/auth")
    @ResponseBody
    public Authentication auth() { // org.springframework.security.core.Authentication
        return SecurityContextHolder.getContext().getAuthentication();
    }
}

