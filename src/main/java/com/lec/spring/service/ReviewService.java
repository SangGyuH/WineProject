package com.lec.spring.service;


import com.lec.spring.domain.*;
import com.lec.spring.repository.ReviewRepository;
import com.lec.spring.repository.UserRepository;
import com.lec.spring.repository.WriteRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private ReviewRepository reviewRepository;
    private UserRepository userRepository;

    private WriteRepository writeRepository;

    @Autowired
    public ReviewService(SqlSession sqlSession){
        reviewRepository = sqlSession.getMapper(ReviewRepository.class);
        userRepository = sqlSession.getMapper(UserRepository.class);
        writeRepository = sqlSession.getMapper(WriteRepository.class);

        System.out.println("CommentService() 생성");
    }

    // 특정 글(id) 의 댓글 목록
    public QryCommentList list(Long write_id) {
        QryCommentList list = new QryCommentList();

        List<WriteReview> reviews = reviewRepository.findByWrite(write_id);

        list.setCount(reviews.size());
        list.setList(reviews);
        list.setStatus("OK");

        return list;
    }

    // 특정 글(writeId) 에 특정 사용자(userId) 가 댓글 작성
    public QryResult write(Long write_id, Long user_uid, String wr_content) {
        User user = userRepository.findById(user_uid);
        Write write = writeRepository.findById(write_id);

        WriteReview writeReview = WriteReview.builder()
                .user(user)
                .wr_content(wr_content)
                .write(write)
                //.write_id(write_id)
                .build();

        reviewRepository.save(writeReview);

        QryResult result = QryResult.builder()
                .count(1)
                .status("OK")
                .build();

        return result;
    }

    // 특정 댓글(id) 삭제
    public QryResult delete(Long wr_id) {

        int count = reviewRepository.deleteById(wr_id);
        String status = "FAIL";

        if(count > 0) status = "OK";

        QryResult result = QryResult.builder()
                .count(count)
                .status(status)
                .build();

        return result;

    }
}
