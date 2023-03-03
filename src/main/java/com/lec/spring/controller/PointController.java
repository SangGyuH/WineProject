package com.lec.spring.controller;

import com.lec.spring.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/point")
public class PointController {

    @Autowired
    private PointService pointService;

}
