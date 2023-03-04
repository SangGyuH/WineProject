package com.lec.spring.controller;

import com.lec.spring.domain.QryResult;
import com.lec.spring.service.BuyService;
import com.lec.spring.service.PointService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/buy")
public class BuyController {
    @Autowired
    private BuyService buyService;
    @Autowired
    private PointService pointService;

    @PostMapping("/historySave")
    @ResponseBody
    public QryResult historySave(
            HttpServletRequest request,
            @RequestParam("wine_id") Long wine_id,
            @RequestParam("quantity") Long quantity,
            @RequestParam("paymentKey") String paymentKey,
            @RequestParam("totalAmount") Integer totalAmount){
        int count = 0;
        Long user_uid = (Long) request.getSession().getAttribute("user_uid");
        if(buyService.historySave(user_uid, wine_id, quantity, paymentKey) > 0)
            count = pointService.pointInsert(user_uid, wine_id, totalAmount);   //포인트 등록

        return QryResult.builder().count(count).status("OK").build();
    }


}
