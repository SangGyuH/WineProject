package com.lec.spring.repository;

import com.lec.spring.domain.NoticeFile;

import java.util.List;
import java.util.Map;

public interface NoticeFileRepository {

    int insert(List<Map<String, Object>> list, Long notice_id);

    List<NoticeFile> findByNoticeId(Long notice_id);

    int save(NoticeFile noticeFile);

    NoticeFile findById(Long notice_file_id);

    List<NoticeFile> findByIds(Long[] notice_file_ids);

    int deleteByIds(Long[] notice_file_ids);

    int delete(NoticeFile file);
}
