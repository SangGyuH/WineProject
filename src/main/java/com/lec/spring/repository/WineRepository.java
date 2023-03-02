package com.lec.spring.repository;

import com.lec.spring.domain.Wine;

public interface WineRepository {

    Wine wineDetail(String wine_type, Integer wine_serialkey);
    int stockCheck(String wine_type, Integer wine_serialkey);
    int wineBuy(Integer wine_id, Integer quantity);


}
