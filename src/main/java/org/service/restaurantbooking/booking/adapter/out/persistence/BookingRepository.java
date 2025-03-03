package org.service.restaurantbooking.booking.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingJpaEntity, Long> {
    List<BookingJpaEntity> findByRestaurantId(Long restaurantId);
}