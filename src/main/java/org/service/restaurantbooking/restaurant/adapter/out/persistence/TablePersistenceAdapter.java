package org.service.restaurantbooking.restaurant.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.service.restaurantbooking.restaurant.domain.Table;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TablePersistenceAdapter {
    // 도메인 layer와 infrastructure layer를 연결
    private final TableRepository tableRepository;
    private final RestaurantRepository restaurantRepository;

    public Table save(Table table) {
        RestaurantJpaEntity restaurantEntity = restaurantRepository.findById(table.getRestaurantId())
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found with id: " + table.getRestaurantId()));
        
        TableJpaEntity tableEntity = TableJpaEntity.fromDomain(table, restaurantEntity);
        TableJpaEntity savedEntity = tableRepository.save(tableEntity);
        
        return savedEntity.toDomain();
    }
    
    public Optional<Table> findById(Long id) {
        return tableRepository.findById(id)
                .map(TableJpaEntity::toDomain);
    }
    
    public List<Table> findByRestaurantId(Long restaurantId) {
        return tableRepository.findByRestaurant_Id(restaurantId)
                .stream()
                .map(TableJpaEntity::toDomain)
                .collect(Collectors.toList());
    }
    
    public void deleteById(Long id) {
        tableRepository.deleteById(id);
    }
} 