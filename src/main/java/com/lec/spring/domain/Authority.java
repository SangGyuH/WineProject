package com.lec.spring.domain;

public class Authority {
    //사용자의 권한을 정의하는 파일.
    //사용자 권한 = ADMIN (관리자)
    //사용자 권한 = MEMBER (일반 사용자)
    //  MEMBER 의 등급분류
    //  MEMBER_SILVER
    //  MEMBER_GOLD
    //  MEMBER_DIAMOND

    private Long id;                //권한 pk

    private String authority;       //권한명 : ROLE_ADMIN, ROLE_MEMBER_SILVER, ROLE_MEMBER_GOLD, ROLE_MEMBER_DIAMOND;

}
