package com.lec.spring.repository;


import com.lec.spring.domain.WriteReview;

import java.util.List;

public interface ReviewRepository {

    // 특정 글(write_id) 의 댓글 목록
    List<WriteReview> findByWrite(Long write_id);

    // 댓글 작성 <-- Comment
    int save(WriteReview writeReview);

    // 특정 댓글 (id) 삭제
    int deleteById(Long wr_id);

}
