package com.lec.spring.controller;

import com.lec.spring.domain.QryCommentList;
import com.lec.spring.domain.QryResult;
import com.lec.spring.domain.User;
import com.lec.spring.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController     // data 를 response 한다 ('VIEW' 를 리턴하는게 아니다)
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    public ReviewController() {
        System.out.println(getClass().getName() + "() 생성");
    }


    @GetMapping("/list")
    public QryCommentList list(Long write_id){
        QryCommentList list = reviewService.list(write_id);
        return list;
    }

    @PostMapping("/write")
    public QryResult write(
            @RequestParam("write_id") Long writeId,
            @RequestParam("user_uid") Long userUid,
            String wr_content){
        return reviewService.write(writeId, userUid, wr_content);
    }

    @PostMapping("/delete")
    public QryResult delete(Long wr_id){
        return reviewService.delete(wr_id);
    }

}
