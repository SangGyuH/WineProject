package com.lec.spring.controller;

import com.lec.spring.domain.QryResult;
import com.lec.spring.service.BuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/buy")
public class BuyController {
    @Autowired
    private BuyService buyService;

    @PostMapping("historySave")
    @ResponseBody
    public QryResult historySave(
            @RequestParam("user_uid") Long user_uid,
            @RequestParam("wine_id") Long wine_id,
            @RequestParam("quantity") Long quantity,
            @RequestParam("paymentKey") String paymentKey ){
        int count = buyService.historySave(user_uid, wine_id, quantity, paymentKey );
        //정상 등록
        return QryResult.builder().count(count).status("OK").build();
    }


}
