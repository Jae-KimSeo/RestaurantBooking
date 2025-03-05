package org.service.restaurantbooking.restaurant.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.service.restaurantbooking.restaurant.adapter.out.persistence.TablePersistenceAdapter;
import org.service.restaurantbooking.restaurant.application.port.in.ManageTableUseCase;
import org.service.restaurantbooking.restaurant.domain.Table;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TableServiceTest {

    @Mock
    private TablePersistenceAdapter tablePersistenceAdapter;

    @InjectMocks
    private TableService tableService;

    @BeforeEach
    void setUp() {
        // MockitoAnnotations.openMocks(this); // @ExtendWith(MockitoExtension.class) 사용하므로 불필요
    }

    @Test
    @DisplayName("createTable: 새 테이블을 성공적으로 생성하면, 생성된 테이블 ID를 반환한다.")
    void createTable() {
        // given
        ManageTableUseCase.CreateTableCommand command =
                new ManageTableUseCase.CreateTableCommand(10L, 5, 4, true);

        Table savedTable = Table.builder()
                .id(100L)
                .restaurantId(10L)
                .tableNumber(5)
                .capacity(4)
                .isAvailable(true)
                .build();

        when(tablePersistenceAdapter.save(any(Table.class))).thenReturn(savedTable);

        // when
        Long resultId = tableService.createTable(command);

        // then
        assertThat(resultId).isEqualTo(100L);
        verify(tablePersistenceAdapter, times(1)).save(any(Table.class));
    }

    @Test
    @DisplayName("getTablesByRestaurant: 레스토랑 ID로 테이블 리스트를 가져온다.")
    void getTablesByRestaurant() {
        // given
        long restaurantId = 10L;
        Table table1 = Table.builder().id(1L).restaurantId(restaurantId).tableNumber(1).capacity(4).isAvailable(true).build();
        Table table2 = Table.builder().id(2L).restaurantId(restaurantId).tableNumber(2).capacity(2).isAvailable(false).build();

        when(tablePersistenceAdapter.findByRestaurantId(restaurantId)).thenReturn(List.of(table1, table2));

        // when
        List<Table> result = tableService.getTablesByRestaurant(restaurantId);

        // then
        assertThat(result).hasSize(2);
        verify(tablePersistenceAdapter, times(1)).findByRestaurantId(restaurantId);
    }

    @Test
    @DisplayName("getTable: 특정 ID의 테이블을 가져온다.")
    void getTable() {
        // given
        long tableId = 100L;
        Table table = Table.builder()
                .id(tableId)
                .restaurantId(10L)
                .tableNumber(3)
                .capacity(4)
                .isAvailable(true)
                .build();
        when(tablePersistenceAdapter.findById(tableId)).thenReturn(Optional.of(table));

        // when
        Optional<Table> result = tableService.getTable(tableId);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(tableId);
        verify(tablePersistenceAdapter, times(1)).findById(tableId);
    }

    @Test
    @DisplayName("updateTable: 존재하는 테이블을 업데이트한다.")
    void updateTable() {
        // given
        long tableId = 100L;
        Table existing = Table.builder()
                .id(tableId)
                .restaurantId(10L)
                .tableNumber(3)
                .capacity(4)
                .isAvailable(true)
                .build();

        ManageTableUseCase.UpdateTableCommand command =
                new ManageTableUseCase.UpdateTableCommand(tableId, 5, 6, false);

        Table updated = Table.builder()
                .id(tableId)
                .restaurantId(10L)
                .tableNumber(5)
                .capacity(6)
                .isAvailable(false)
                .build();

        when(tablePersistenceAdapter.findById(tableId)).thenReturn(Optional.of(existing));
        when(tablePersistenceAdapter.save(any(Table.class))).thenReturn(updated);

        // when
        Optional<Table> result = tableService.updateTable(command);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getTableNumber()).isEqualTo(5);
        assertThat(result.get().getCapacity()).isEqualTo(6);
        assertThat(result.get().isAvailable()).isFalse();

        verify(tablePersistenceAdapter, times(1)).findById(tableId);
        verify(tablePersistenceAdapter, times(1)).save(any(Table.class));
    }

    @Test
    @DisplayName("deleteTable: 존재하는 테이블이면 true를 반환하고, 실제로 삭제한다.")
    void deleteTable() {
        // given
        long tableId = 123L;
        Table existing = Table.builder().id(tableId).restaurantId(10L).build();
        when(tablePersistenceAdapter.findById(tableId)).thenReturn(Optional.of(existing));

        // when
        boolean deleted = tableService.deleteTable(tableId);

        // then
        assertThat(deleted).isTrue();
        verify(tablePersistenceAdapter, times(1)).deleteById(tableId);
    }

    @Test
    @DisplayName("deleteTable: 존재하지 않는 테이블이면 false를 반환한다.")
    void deleteTableNonExistent() {
        // given
        long tableId = 999L;
        when(tablePersistenceAdapter.findById(tableId)).thenReturn(Optional.empty());

        // when
        boolean result = tableService.deleteTable(tableId);

        // then
        assertThat(result).isFalse();
        verify(tablePersistenceAdapter, never()).deleteById(anyLong());
    }
}