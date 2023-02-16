package com.lec.spring.repository;

import com.lec.spring.domain.Notice;

import java.util.List;

public interface NoticeRepository {
    int save(Notice notice);

    Notice findById(long notice_id);

    List<Notice> findAll();

    int update(Notice notice);

    int delete(Notice notice);

    List<Notice> selectFromRow(int from, int rows);

    int countAll();
}
