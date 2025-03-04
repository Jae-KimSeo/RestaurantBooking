package org.service.restaurantbooking.restaurant.adapter.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableRepository extends JpaRepository<TableJpaEntity, Long> {
    List<TableJpaEntity> findByRestaurant_Id(Long restaurantId);
} 