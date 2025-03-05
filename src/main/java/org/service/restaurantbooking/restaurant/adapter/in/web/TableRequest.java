package org.service.restaurantbooking.restaurant.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableRequest {
    private int tableNumber;
    private int capacity;
    private boolean isAvailable;
}