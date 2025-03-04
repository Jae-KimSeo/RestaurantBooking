package org.service.restaurantbooking.restaurant.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Slot {
    private Long id;
    private Long tableId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private SlotStatus status;
    
    public enum SlotStatus {
        AVAILABLE, 
        RESERVED, 
        OCCUPIED, 
        CANCELLED
    }
}
