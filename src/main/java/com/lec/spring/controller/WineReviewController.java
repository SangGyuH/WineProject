package com.lec.spring.controller;

import com.lec.spring.domain.QryResult;
import com.lec.spring.domain.QryResult_WineReview;
import com.lec.spring.service.WineReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
public class WineReviewController {

    @Autowired
    private WineReviewService wineReviewService;

    @PostMapping("/showScore")
    public QryResult showScore(@RequestParam("wine_serialkey") Integer wine_serialkey, @RequestParam("wine_type") String wine_type ){
        return wineReviewService.searchReviewScore(wine_serialkey, wine_type);
    }

    @PostMapping("/list")
    public QryResult_WineReview setList(@RequestParam("wine_serialkey") Integer wine_serialkey, @RequestParam("wine_type") String wine_type ){
        return wineReviewService.wineCommentList(wine_serialkey, wine_type);
    }
    @PostMapping("/insert")
    public QryResult reviewInset(
            @RequestParam("wnrv_content") String wnrv_content,
            @RequestParam("wnrv_reviews") Integer wnrv_reviews,
            @RequestParam("wine_serialkey") Integer wine_serialkey,
            @RequestParam("wine_type") String wine_type
    ){
        return wineReviewService.reviewInsert("2", wnrv_content, wnrv_reviews, wine_serialkey, wine_type);
    }
    @PostMapping("/update")
    public QryResult reviewUpdate(
            @RequestParam("wnrv_content") String wnrv_content,
            @RequestParam("wnrv_id") Long wnrv_id
    ){
        return wineReviewService.changeReview(wnrv_id, wnrv_content);
    }
    @GetMapping("/delete")
    public void removeReview(@RequestParam("wnrv_id") Long wnrv_id){
        wineReviewService.removeReview(wnrv_id);
    }


}
