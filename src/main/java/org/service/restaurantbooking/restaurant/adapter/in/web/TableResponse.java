package org.service.restaurantbooking.restaurant.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableResponse {
    private Long id;
    private Long restaurantId;
    private int tableNumber;
    private int capacity;
    private boolean available;
}