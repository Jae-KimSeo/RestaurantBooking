package org.service.restaurantbooking.restaurant.adapter.out.persistence;

import java.time.LocalDateTime;

import org.service.restaurantbooking.restaurant.domain.Slot;
import org.service.restaurantbooking.restaurant.domain.Slot.SlotStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@jakarta.persistence.Table(name = "table_slot")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SlotJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id", nullable = false)
    private TableJpaEntity table;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private SlotStatus slotStatus;

    public static SlotJpaEntity fromDomain(Slot slot, TableJpaEntity table) {
        return SlotJpaEntity.builder()
                .id(slot.getId())
                .table(table)
                .startTime(slot.getStartTime())
                .endTime(slot.getEndTime())
                .slotStatus(slot.getStatus())
                .build();
    }

    public Slot toDomain() {
        return Slot.builder()
                .id(id)
                .tableId(table != null ? table.getId() : null)
                .startTime(startTime)
                .endTime(endTime)
                .status(slotStatus)
                .build();
    }
}