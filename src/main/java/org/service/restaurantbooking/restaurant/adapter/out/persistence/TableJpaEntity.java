package org.service.restaurantbooking.restaurant.adapter.out.persistence;

import org.service.restaurantbooking.restaurant.domain.Table;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@jakarta.persistence.Table(name = "restaurant_table")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private RestaurantJpaEntity restaurant;
    
    private int tableNumber;
    private int capacity;
    private boolean isAvailable;
    
    public static TableJpaEntity fromDomain(Table table, RestaurantJpaEntity restaurant) {
        return TableJpaEntity.builder()
                .id(table.getId())
                .restaurant(restaurant)
                .tableNumber(table.getTableNumber())
                .capacity(table.getCapacity())
                .isAvailable(table.isAvailable())
                .build();
    }
    
    public Table toDomain() {
        return Table.builder()
                .id(id)
                .restaurantId(restaurant != null ? restaurant.getId() : null)
                .tableNumber(tableNumber)
                .capacity(capacity)
                .isAvailable(isAvailable)
                .build();
    }
} 