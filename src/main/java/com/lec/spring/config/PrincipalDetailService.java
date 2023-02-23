package com.lec.spring.config;


import com.lec.spring.domain.User;
import com.lec.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override                                     // ↓ login.html username
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {   //인증이 끝나면 UserDetails(스프링시큐리티 객체) return

        System.out.println("loadUserByUsername(" + username + ")");

        // DB 조회하는 코드 작성 (mapper 만들기)(회원이 맞는지 아닌지) - loadUserByUsername 이 해야하는 일 1
        User user = userService.findByUsername(username);
        // username 의 user 가 db에 있다면 userDetails 생성해서 리턴 (PrincipalDetails) - loadUserByUsername 이 해야하는 일 2
        if (user != null) {
            PrincipalDetails userDetails = new PrincipalDetails(user);
            userDetails.setUserService(userService);
            return userDetails;
        }
        //해당 username 의 user 가 없다면 UsernameNotFoundException 을 throw 해주어야함. - loadUserByUsername 이 해야하는 일 3
        //절대 null 리턴하면 안됨(예외발생함).

        throw new UsernameNotFoundException(username);
    }
}
