package com.lec.spring.domain;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wine {
    //와인 정보를 담는 VO파일.
    private Long wine_id;                       //와인 식별코드(PK)
    private Integer wine_serialkey;                //와인 시리얼 번호
    private String wine_winery;                 //와인 양조장

    private String wine_name;                   //와인명
    private String wine_location;               //와인 생상지

    @ToString.Exclude
    private String wine_img;                    //와인 이미지 주소값
    private LocalDateTime wine_regdate;         //와인 등록 일자
    private Long wine_price;                    //와인 가격
    private String wine_type;                   //와인 종류
    private Long wine_count;                    //와인 재고수
}
