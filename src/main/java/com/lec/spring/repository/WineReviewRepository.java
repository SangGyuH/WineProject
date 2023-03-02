package com.lec.spring.repository;

import com.lec.spring.domain.WineReview;

import java.util.List;

public interface WineReviewRepository {
    //하나의 와인에 대한 평점 조회
    double searchReviewScore(Integer wine_serialkey, String wine_type);
    List<WineReview> wineCommentList (Integer wine_serialkey, String wine_type);
    public int reviewInsert(WineReview review);
    public int changeReview(WineReview review);
    public int removeReview(Long wnrv_id);
}
