package com.lec.spring.service;

import com.lec.spring.domain.Wine;
import com.lec.spring.domain.WineReview;
import com.lec.spring.repository.WineRepository;
import com.lec.spring.repository.WineReviewRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WineService {
    private WineRepository wineRepository;
    private WineReviewRepository wineReviewRepository;

    public WineService(SqlSession sqlSession) {
        wineRepository = sqlSession.getMapper(WineRepository.class);
        wineReviewRepository = sqlSession.getMapper(WineReviewRepository.class);
        System.out.println("\t*WineService() 생성");
    }

    public Wine wineDetail(String wine_type, Integer wine_serialkey, String wine_img, String wine_name, String wine_location){
        Wine wine = wineRepository.wineDetail(wine_type, wine_serialkey);

        return (wine == null)?
                (Wine.builder().wine_serialkey(wine_serialkey).wine_type(wine_type).wine_img(wine_img).wine_name(wine_name).wine_location(wine_location).build())
                : wine;
    }
    public List<WineReview> wineReviews(String wine_type, Integer wine_serialkey){
        return wineReviewRepository.wineCommentList(wine_serialkey, wine_type);
    }
    public int stockCheck(String wine_type, Integer wine_serialkey){
        return wineRepository.stockCheck(wine_type, wine_serialkey);
    }
    public int wineBuy(Integer wine_id, Integer quantity){
        return wineRepository.wineBuy(wine_id, quantity);
    }








}
