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
public class Buy {
    private Long buy_quantity;                      //구매수량
    private User user;                              //구매자가 누구인지를 알기위한 사용자 정보 객체
    private Wine wine;                              //어느 와인을 구매했는지 알기위한 와인 정보 객체
    private LocalDateTime buy_regdate;              //와인 구매일자
    private String wine_paymentKey;


}
