package com.lec.spring.service;

import com.lec.spring.domain.User;
import com.lec.spring.domain.UserPoint;
import com.lec.spring.domain.Wine;
import com.lec.spring.repository.PointRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class PointService {
    private PointRepository pointRepository;

    public PointService(SqlSession sqlsession) {
        pointRepository = sqlsession.getMapper(PointRepository.class);
    }

    public int pointInsert(Long user_uid, Long wine_id, Long user_point){
        //point 설정


        //builder
        UserPoint point = UserPoint.builder()
                .user(User.builder().user_uid(user_uid).build())
                .wine(Wine.builder().wine_id(wine_id).build())
//                .user_point(user_point)
                .build();
        return pointRepository.pointInsert(point);
    }

}
