package com.lec.spring.service;

import com.lec.spring.domain.Wine;
import com.lec.spring.repository.PurchaseRepository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {
    PurchaseRepository purchaseRepository;

    @Autowired
    public PurchaseService(SqlSession sqlSession){  // MyBatis 가 생성한 SqlSession 빈(bean) 객체 주입
        purchaseRepository = sqlSession.getMapper(PurchaseRepository.class);
    }

    public List<Wine> getWineListByUseruid(Long user_uid){
        return purchaseRepository.getWineListByUseruid(user_uid);
    }
}









