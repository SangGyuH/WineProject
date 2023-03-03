package com.lec.spring.service;

import com.lec.spring.domain.Authority;
import com.lec.spring.domain.User;
import com.lec.spring.domain.UserPoint;
import com.lec.spring.domain.Wine;
import com.lec.spring.repository.AuthorityRepository;
import com.lec.spring.repository.PointRepository;
import com.lec.spring.repository.UserRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointService {
    private PointRepository pointRepository;
    private AuthorityRepository authorityRepository;

    public PointService(SqlSession sqlsession) {
        pointRepository = sqlsession.getMapper(PointRepository.class);
        authorityRepository = sqlsession.getMapper(AuthorityRepository.class);
    }
    public long pointRatio(long authority_id, int totalAmount){
        long resultPoint = 0;
        int id = Integer.parseInt(Long.toString(authority_id));
        switch (id){
            case 1: resultPoint = (int)(totalAmount * 0.01); break;
            case 2: resultPoint = (int)(totalAmount * 0.02); break;
            case 3: resultPoint = (int)(totalAmount * 0.03); break;
            case 4: resultPoint = (int)(totalAmount * 0.05); break;
        }
        
        return resultPoint;
    }

    public int pointInsert(Long user_uid, Long wine_id, Integer totalAmount){
        //point 설정
        //List가 아니라 Authority 아닌가요?
        Authority authority =  authorityRepository.findByUser(User.builder().user_uid(user_uid).build()).get(0);
        Long user_point = pointRatio(authority.getAuthority_id(), totalAmount);

        //builder
        UserPoint point = UserPoint.builder()
                .user(User.builder().user_uid(user_uid).build())
                .wine(Wine.builder().wine_id(wine_id).build())
                .user_point(user_point)
                .build();
        return pointRepository.pointInsert(point);
    }

}
