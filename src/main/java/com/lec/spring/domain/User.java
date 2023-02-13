package com.lec.spring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    //사용자 정보를 담고 있는 VO 파일.
    //사용자 비밀번호 재확인용 변수는 '비밀번호 재확인' 기능을 사용하려면 주석을 해제해주세요.

    private long user_uid;              //사용자 식별코드(PK)
    private String user_id;             //사용자 아이디

    @JsonIgnore
    private String user_pw;             //사용자 비밀번호

//    private String user_repw;           //사용자 비밀번호 재확인용
    private String user_name;           //사용자 이름
    private String user_email;          //사용자 이메일
    private String user_phone;          //사용자 휴대폰 번호

    private String user_addr1;           //사용자 주소(도로명 주소)
    private String user_addr2;           //사용자 주소(상세주소)
    private String user_addr3;           //사용자 주소(우편번호)

    //TODO
}
