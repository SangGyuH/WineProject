package com.lec.spring.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WriteReview {
    //게시글의 댓글을 위판 VO파일.

    private Long wr_id;                         //댓글의 식별코드(PK)

    @ToString.Exclude
    private User user;                          //댓글 작성자 정보 객체

    private Write write;                        //어느 글의 댓글인지를 확인하기 위한 게시글 객체

    private String wr_content;                  //댓글 내용

    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    @JsonProperty("wr_regdate")
    private LocalDateTime wr_regdate;           //댓글 등록 일자

}
