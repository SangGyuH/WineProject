package com.lec.spring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private long user_uid;              //사용자 식별코드(PK)
    private String user_id;             //사용자 아이디
    private String user_name;           //사용자 이름
    @JsonIgnore
    private String user_pw;             //사용자 비밀번호
    @JsonIgnore
    private String user_repw;           //사용자 비밀번호 재확인용
    private String user_point;          //사용자 포인트

    private String user_email;          //사용자 이메일
    private String user_phone;          //사용자 휴대폰 번호

    private String user_addr1;           //사용자 주소(도로명 주소)
    private String user_addr2;           //사용자 주소(상세주소)
    private String user_addr3;           //사용자 주소(우편번호)

    private List<Authority> authorities = new ArrayList<>(); // 권한

    public void setUsername(String username){
        this.user_id = username;
    }

    public void setName(String name){
        this.user_name = name;
    }

    public void setPassword(String password){
        this.user_pw = password;
    }

    public void setRe_password(String re_password){
        this.user_repw = re_password;
    }

    public void addAuthority(Authority... authorities){
        if(authorities != null){
            Collections.addAll(this.authorities, authorities);
        }
    }



}




