package com.lec.spring.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notice {
    //공지사항 정보를 담는 VO파일.

    private Long notice_id;                     //공지사항 식별코드(PK)
    private User user;                          //작성자 누구인지? 사용자 정보 객체

    private String notice_title;                //공지사항 제목

    private String notice_content;              //공지사항 내용

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone="Asia/Seoul")
    @JsonProperty("wnrv_regdate")
    private LocalDateTime notice_regdate;       //공지사항 등록일자

}
