package com.lec.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Authority {
    //사용자의 권한을 정의하는 파일.
    //사용자 권한 = ADMIN (관리자)
    //사용자 권한 = MEMBER (일반 사용자)
    //  MEMBER 의 등급분류
    //  MEMBER_SILVER
    //  MEMBER_GOLD
    //  MEMBER_DIAMOND

    private Long authority_id;                //권한 pk

    private String authority;       //권한명 : ROLE_ADMIN, ROLE_MEMBER_SILVER, ROLE_MEMBER_GOLD, ROLE_MEMBER_DIAMOND;

}
