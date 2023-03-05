package com.lec.spring.service;

import com.lec.spring.domain.Authority;
import com.lec.spring.domain.User;
import com.lec.spring.domain.Write;
import com.lec.spring.repository.AuthorityRepository;
import com.lec.spring.repository.UserRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;

    @Autowired
    public UserService(SqlSession sqlSession){  // MyBatis 가 생성한 SqlSession 빈(bean) 객체 주입
        userRepository = sqlSession.getMapper(UserRepository.class);
        authorityRepository = sqlSession.getMapper(AuthorityRepository.class);
        System.out.println(getClass().getName() + "() 생성");
    }

    // username(회원 아이디) 의 user 정보 읽어오기
    public User findByUsername(String id){
        return userRepository.findByUsername(id); //findByUserName -> mybatis 가 만들어줌(repository)
    }

    // 특정 username(회원 아이디) 의 회원이 존재하는지 확인
    public boolean isExist(String username){
        User user = findByUsername(username);
        return (user != null) ? true : false;
    }

    public int update(User user){
        int result = 0;
        user.setUser_pw(passwordEncoder.encode(user.getUser_pw()));
        result = userRepository.update(user);
        return result;
    }

    // 신규 회원 등록(회원가입)
    public int register(User user){
        user.setUser_id(user.getUser_id().toUpperCase());       //DB에 회원아이디 대문자로 저장
        // password 암호화(config 에 encoder 생성 완료)해서 저장
        user.setUser_pw(passwordEncoder.encode(user.getUser_pw())); // form 에서 입력했던 패스워드를 인코딩해서 저장한다
        userRepository.save(user);  // 새로 회원 저장. id 값을 user 에 받아온다.

        // 신규회원은 ROLE_MEMBER_SILVER 권한 기본적으로 부여하기(-> Authority repository 만들기)
//        Authority auth = authorityRepository.findByName("ROLE_SILVER");
//        System.out.println("auth: " + auth);

        // 사용자의 ID와 authority 아이디 꺼낸 후 넣기(회원가입까지 만들기 위함)
        Long user_id = user.getUser_uid();
        System.out.println("\t*register : user_uid:" + user_id);
        return authorityRepository.addAuthority(user_id, 2l);   //2: role_silver
    }

    public int deleteById(String user_id){
        int result;

        result = userRepository.deleteByUserid(user_id);

        return result;
    }

    // 특정 사용자의 authority(들)
    public List<Authority> selectAuthoritiesById(Long id){      // 여기서 id는 user_uid
        User user = userRepository.findById(id);
        System.out.println("##### user : " + user);
        return authorityRepository.findByUser(user);
    }
}









