package org.service.restaurantbooking.restaurant.adapter.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlotRepository extends JpaRepository<SlotJpaEntity, Long> {
    List<SlotJpaEntity> findByTable_Id(Long tableId);
}
