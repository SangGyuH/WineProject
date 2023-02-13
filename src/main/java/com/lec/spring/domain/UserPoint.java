package com.lec.spring.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPoint {
    //사용자의 포인트 및 구매일자를 담는 VO객체.
    
    private Long user_point;                    //사용자 포인트
    
    private User user;                          //누구의 포인트인지 판별하기 위한 사용자 정보를 담는 객체
    
    private Wine wine;                          //어느 와인을 구매하여 얻은 포인트인지 판별하기 위한 와인 정보를 담는 객체

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    //TODO  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul") 을 default로 남겨두었으나 pattern 값에는 원하는 값으로 넣은 후 '//TODO' 부분은 지워주세요
    @JsonProperty("regdate")
    private LocalDateTime regdate;              //구매일자
}
