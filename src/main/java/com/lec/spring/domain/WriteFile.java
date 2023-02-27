package com.lec.spring.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WriteFile {
    //게시글에 달리는 첨부파일을 위한 VO 파일.

    private Long write_file_id;                     //첨부파일의 식별코드(PK)

    @ToString.Exclude
    private Write write;                            //어느 글에 붙는 첨부파일인지를 위한 게시글 객체

    private String source;                          //원본 파일명

    private String file;                            //저장되는(rename된) 파일명

    private boolean isImage;                        //이미지 파일인지 판별 여부용
}
