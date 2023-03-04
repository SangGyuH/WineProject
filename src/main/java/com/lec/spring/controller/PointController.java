package com.lec.spring.controller;

import com.lec.spring.domain.QryResult;
import com.lec.spring.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/point")
public class PointController {

    @Autowired
    private PointService pointService;

    @PostMapping("/totalByUid")
    @ResponseBody
    public QryResult totalByUid(@RequestParam("user_uid") Long user_uid){
        return QryResult.builder().count(pointService.pointByUid(user_uid)).status("OK").build();
    }
    @PostMapping("/cut")
    @ResponseBody
    public QryResult cut(
            @RequestParam("point") Long point,
            @RequestParam("user_uid") Long user_uid,
            @RequestParam("wine_id") Long wine_id
            ){
        return QryResult.builder().count(pointService.pointCut((point * -1), user_uid, wine_id)).status("OK").build();
    }


}
