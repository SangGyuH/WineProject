package com.lec.spring.repository;

import com.lec.spring.domain.UserPoint;

public interface PointRepository {
    public int pointInsert(UserPoint point);
    public int pointByUid(Long user_uid);
}
