package com.lec.spring.repository;

import com.lec.spring.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private SqlSession sqlSession;

    @Test
    void test1(){
        UserRepository userRepository = sqlSession.getMapper(UserRepository.class);
        // TODO 웹개발3 9강 0시 45분~
        System.out.println("테스트완료");
    }

}