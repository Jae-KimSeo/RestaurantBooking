package org.service.restaurantbooking.restaurant.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Table {
    private Long id;
    private Long restaurantId;
    private int tableNumber;
    private int capacity;
    private boolean isAvailable;
    
    public void updateDetails(int tableNumber, int capacity) {
        this.tableNumber = tableNumber;
        this.capacity = capacity;
    }
    
    public void setAvailability(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
