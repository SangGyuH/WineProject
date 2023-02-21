package com.lec.spring.repository;

import com.lec.spring.domain.Write;

import java.util.List;

public interface WriteRepository {

    // 새글 작성 <- Write
    int save(Write write);

    // 특정 id 글 내용 읽기
    Write findById(long write_id);

    // 전체 글 목록 : 최신순
    List<Write> findAll();

    // 특정 id 글 수정 (제목, 내용)
    int update(Write write);

    // 특정 id 글 삭제하기
    int delete(Write write);

    // 전체 글의 개수
    int countAll();

}
