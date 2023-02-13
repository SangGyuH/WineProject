package com.lec.spring.domain;

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
public class WineReview {
    //와인 리뷰 댓글을 담는 VO파일.
    
    private Long wnrv_id;                       //와인 리뷰 식별번호(PK)

    @ToString.Exclude
    private User user;                          //댓글 사용자 판별용 사용자 정보를 담는 객체

    @ToString.Exclude
    private Wine wine;                          //어느 와인에 달린 댓글인지 판별용 와인 정보를 담는 객체
    private String wnrv_content;                //와인리뷰 내용

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    //TODO  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul") 을 default로 남겨두었으나 pattern 값에는 원하는 값으로 넣은 후 '//TODO' 부분은 지워주세요
    @JsonProperty("wnrv_regdate")
    private LocalDateTime wnrv_regdate;         //와인리뷰 등록일자
    
    private Long wnrv_reviews;                  //와인리뷰 평점
    
}
