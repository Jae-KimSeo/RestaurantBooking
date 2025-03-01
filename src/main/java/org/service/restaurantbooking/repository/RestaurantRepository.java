package org.service.restaurantbooking.repository;

import org.service.restaurantbooking.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    // 기본 CRUD 작업은 JpaRepository에서 제공됨
    
    // 추가 쿼리 메서드는 필요할 때 추가할 수 있음
    // 예: 이름으로 레스토랑 찾기
    Restaurant findByName(String name);
} 