package com.lec.spring.repository;


import com.lec.spring.domain.Wine;

import java.util.List;

public interface PurchaseRepository {
    List<Wine> getWineListByUseruid(Long user_uid);
}
