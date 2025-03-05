package org.service.restaurantbooking.restaurant.application.service;

import lombok.RequiredArgsConstructor;
import org.service.restaurantbooking.restaurant.adapter.in.web.TableResponse;
import org.service.restaurantbooking.restaurant.adapter.out.persistence.TablePersistenceAdapter;
import org.service.restaurantbooking.restaurant.application.port.in.ManageTableUseCase;
import org.service.restaurantbooking.restaurant.domain.Table;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TableService implements ManageTableUseCase {

    private final TablePersistenceAdapter tablePersistenceAdapter;

    @Override
    public Long createTable(CreateTableCommand command) {
        Table table = Table.builder()
                .restaurantId(command.restaurantId())
                .tableNumber(command.tableNumber())
                .capacity(command.capacity())
                .isAvailable(command.isAvailable())
                .build();

        Table saved = tablePersistenceAdapter.save(table);
        return saved.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Table> getTablesByRestaurant(Long restaurantId) {
        return tablePersistenceAdapter.findByRestaurantId(restaurantId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Table> getTable(Long tableId) {
        return tablePersistenceAdapter.findById(tableId);
    }

    @Override
    public Optional<Table> updateTable(UpdateTableCommand command) {
        // 기존 테이블 조회 후 업데이트
        Optional<Table> tableOpt = tablePersistenceAdapter.findById(command.tableId());
        return tableOpt.map(table -> {
            table.updateDetails(command.tableNumber(), command.capacity());
            table.setAvailability(command.isAvailable());
            return tablePersistenceAdapter.save(table);
        });
    }

    @Override
    public boolean deleteTable(Long tableId) {
        Optional<Table> tableOpt = tablePersistenceAdapter.findById(tableId);
        if (tableOpt.isEmpty()) {
            return false;
        }
        tablePersistenceAdapter.deleteById(tableId);
        return true;
    }
}