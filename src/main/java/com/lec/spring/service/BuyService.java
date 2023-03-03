package com.lec.spring.service;

import com.lec.spring.domain.Buy;
import com.lec.spring.domain.User;
import com.lec.spring.domain.Wine;
import com.lec.spring.repository.BuyRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class BuyService {

    private BuyRepository buyRepository;

    public BuyService(SqlSession sqlSession){
        buyRepository = sqlSession.getMapper(BuyRepository.class);
    }

    public int historySave(Long user_uid, Long wine_id,Long quantity,String paymentKey ){
        Buy buy = Buy.builder()
                .user(User.builder().user_uid(user_uid).build())
                .wine(Wine.builder().wine_id(wine_id).build())
                .buy_quantity(quantity)
                .wine_paymentKey(paymentKey)
                .build();
        //buyRepository.historySave(buy) <- 이게 정상 작업(>0)이 되었다면 포인트 적립
        buyRepository.historySave(buy);

        return buyRepository.historySave(buy);
    }
}
