package com.lec.spring.repository;

import com.lec.spring.domain.Authority;
import com.lec.spring.domain.User;

import java.util.List;

public interface AuthorityRepository {
    //특정 이름(name)의 권한 읽어오기
    Authority findByName(String name);

    //특정 사용자(User)의 권한 읽어오기
    List<Authority> findByUser(User user);

    //특정 사용자(user_uid)에게 권한(authority_id) 추가
    int addAuthority(Long user_uid, Long authority_id);
}

