package org.service.restaurantbooking.restaurant.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.service.restaurantbooking.restaurant.domain.Restaurant;

@Entity
@Table(name = "restaurant")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String address;
    private String phoneNumber;
    private int capacity;
    private String cuisine;
    private String operatingHours;
    
    private boolean acceptsBookings;
    private int maxPartySize;
    private int minAdvanceBookingHours;
    private int maxAdvanceBookingDays;
    
    // 초기값 설정
    {
        acceptsBookings = true;
        maxPartySize = 10;
        minAdvanceBookingHours = 1;
        maxAdvanceBookingDays = 30;
    }
    
    public static RestaurantJpaEntity fromDomain(Restaurant restaurant) {
        return RestaurantJpaEntity.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .phoneNumber(restaurant.getPhoneNumber())
                .capacity(restaurant.getCapacity())
                .cuisine(restaurant.getCuisine())
                .operatingHours(restaurant.getOperatingHours())
                .acceptsBookings(restaurant.isAcceptsBookings())
                .maxPartySize(restaurant.getMaxPartySize())
                .minAdvanceBookingHours(restaurant.getMinAdvanceBookingHours())
                .maxAdvanceBookingDays(restaurant.getMaxAdvanceBookingDays())
                .build();
    }
    
    public Restaurant toDomain() {
        return Restaurant.builder()
                .id(id)
                .name(name)
                .address(address)
                .phoneNumber(phoneNumber)
                .capacity(capacity)
                .cuisine(cuisine)
                .operatingHours(operatingHours)
                .acceptsBookings(acceptsBookings)
                .maxPartySize(maxPartySize)
                .minAdvanceBookingHours(minAdvanceBookingHours)
                .maxAdvanceBookingDays(maxAdvanceBookingDays)
                .build();
    }
}