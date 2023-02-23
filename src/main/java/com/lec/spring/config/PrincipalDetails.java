package com.lec.spring.config;

import com.lec.spring.domain.Authority;
import com.lec.spring.domain.User;
import com.lec.spring.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PrincipalDetails implements UserDetails {
    private UserService userService;
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    private User user;      // 로그인 한 사용자
    public User getUser(){
        return user;
    }

    public PrincipalDetails(User user) {
        System.out.println("PrincipalDetails() 생성 : " +user);
        this.user = user;
    }

    // getAuthorities() 는 현재 로그인한 사용자의 권한 정보가 필요할때마다 수시로 호출된다. 혹은 필요할때마다 직접 호출해 사용할 수도 있다.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("getAuthorities() 호출");

        Collection<GrantedAuthority> collect = new ArrayList<>();

        List<Authority> list = userService.selectAuthoritiesById(user.getUser_uid());     //DB 에서 user 의 권한(들) 읽어오기

        for(Authority auth : list){
            collect.add(new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return auth.getAuthority();
                }

                // thymeleaf 등에서 활용할 문자열
                @Override
                public String toString(){
                    return auth.getAuthority();
                }
            });
        }

        return collect;
    }

    @Override
    public String getPassword() {
        return user.getUser_pw();
    }

    @Override
    public String getUsername() {
        return user.getUser_name();
    }


    // 사용 X
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정이 잠긴건 아닌지?
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //계정 credential이 만료되었는지?
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정이 활성화 되었는지?
    @Override
    public boolean isEnabled() {
        return true;
    }
}
