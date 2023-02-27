package com.lec.spring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Write {
    //게시글 정보를 담는 VO 파일.

    private Long write_id;                        //게시글 식별번호(PK)
    private String write_title;             //게시글 제목
    private String write_content;           //게시글 내용
    @JsonIgnore
    private LocalDateTime write_regdate;    //게시글 등록일

    private User user;                      //게시글 작성자 정보를 담는 객체

    //첨부파일, 댓글
    @ToString.Exclude
    @Builder.Default
    private List<WriteFile> fileList = new ArrayList<>();
}
