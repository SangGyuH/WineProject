package com.lec.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeFile {
    //게시글에 달리는 첨부파일을 위한 VO 파일.

    private Long notice_file_id;                     //첨부파일의 식별코드(PK)
    private Notice notice;                          //어느 공지사항에 붙는 파일인지 구분용 파일객체

    private String source;                          //원본 파일명

    private String file;                            //저장되는(rename된) 파일명

    private boolean isImage;                        //이미지 파일인지 판별 여부용
}
