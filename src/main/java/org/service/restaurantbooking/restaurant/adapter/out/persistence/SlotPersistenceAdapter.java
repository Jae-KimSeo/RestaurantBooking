package org.service.restaurantbooking.restaurant.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.service.restaurantbooking.restaurant.domain.Slot;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SlotPersistenceAdapter {

    private final SlotRepository slotRepository;
    private final TableRepository tableRepository;

    public Slot save(Slot slot) {
        TableJpaEntity tableEntity = tableRepository.findById(slot.getTableId())
                .orElseThrow(() -> new IllegalArgumentException("Table not found with id: " + slot.getTableId()));
        
        SlotJpaEntity slotEntity = SlotJpaEntity.fromDomain(slot, tableEntity);
        SlotJpaEntity savedEntity = slotRepository.save(slotEntity);
        
        return savedEntity.toDomain();
    }
    
    public Optional<Slot> findById(Long id) {
        return slotRepository.findById(id)
                .map(SlotJpaEntity::toDomain);
    }
    
    public List<Slot> findByTableId(Long tableId) {
        return slotRepository.findByTable_Id(tableId)
                .stream()
                .map(SlotJpaEntity::toDomain)
                .collect(Collectors.toList());
    }
    
    public void deleteById(Long id) {
        slotRepository.deleteById(id);
    }
}
