package com.lec.spring.service;

import com.lec.spring.domain.QryResult;
import com.lec.spring.domain.QryResult_WineReview;
import com.lec.spring.domain.User;
import com.lec.spring.domain.WineReview;
import com.lec.spring.repository.WineReviewRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class WineReviewService {
    private WineReviewRepository wineReviewRepository;

    public WineReviewService(SqlSession sqlSession) {
        wineReviewRepository = sqlSession.getMapper(WineReviewRepository.class);
//        System.out.println("\t*WineService() 생성");
    }

    public QryResult searchReviewScore(Integer wine_serialkey, String wine_type){
        return   QryResult.builder()
                .reviewScore(wineReviewRepository.searchReviewScore(wine_serialkey, wine_type))
                .status("OK")
                .build();
    }

    public QryResult_WineReview wineCommentList (Integer wine_serialkey, String wine_type){
        return QryResult_WineReview.builder()
                .reviews(wineReviewRepository.wineCommentList(wine_serialkey, wine_type))
                .status("OK")
                .build();
    }
    public QryResult reviewInsert(Long user_uid, String wnrv_content, Integer wnrv_reviews, Integer wine_serialkey, String wine_type){
        /*TODO : user_id로 user_uid 가져오기*/
        WineReview review = WineReview.builder()
                .user(User.builder().user_uid(user_uid).build())
                .wnrv_content(wnrv_content)
                .wnrv_reviews(wnrv_reviews)
                .wine_serialkey(wine_serialkey)
                .wine_type(wine_type)
                .build();

        return  QryResult.builder()
                .count(wineReviewRepository.reviewInsert(review))
                .status("OK")
                .build();
    }
    public QryResult changeReview(Long wnrv_id, String wnrv_content){
        WineReview review = WineReview.builder()
                .wnrv_id(wnrv_id)
                .wnrv_content(wnrv_content)
//                .wnrv_reviews(TODO)
                .build();
        return QryResult.builder()
                .count(wineReviewRepository.changeReview(review))
                .status("OK")
                .build();
    }
    public int removeReview(Long wnrv_id){
        return wineReviewRepository.removeReview(wnrv_id);
    }





}
