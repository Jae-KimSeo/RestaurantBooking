package org.service.restaurantbooking.restaurant.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.service.restaurantbooking.restaurant.application.port.in.ManageTableUseCase;
import org.service.restaurantbooking.restaurant.domain.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TableController.class)
@WithMockUser // 모든 테스트에 기본 사용자 인증 적용
class TableControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ManageTableUseCase manageTableUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("[GET] /api/restaurants/{restaurantId}/tables - 특정 레스토랑 테이블 목록 조회")
    void getAllTables() throws Exception {
        // given
        long restaurantId = 10L;
        Table table1 = Table.builder().id(1L).restaurantId(restaurantId).tableNumber(1).capacity(4).isAvailable(true).build();
        Table table2 = Table.builder().id(2L).restaurantId(restaurantId).tableNumber(2).capacity(2).isAvailable(false).build();
        given(manageTableUseCase.getTablesByRestaurant(restaurantId)).willReturn(List.of(table1, table2));

        // when / then
        mockMvc.perform(get("/api/restaurants/{restaurantId}/tables", restaurantId)
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].restaurantId").value(10L))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].available").value(false));
    }

    @Test
    @DisplayName("[POST] /api/restaurants/{restaurantId}/tables - 테이블 생성")
    void createTable() throws Exception {
        // given
        long restaurantId = 10L;
        TableRequest request = new TableRequest(3, 6, true);

        Table table = Table.builder()
                .id(100L)
                .restaurantId(restaurantId)
                .tableNumber(request.getTableNumber())
                .capacity(request.getCapacity())
                .isAvailable(request.isAvailable())
                .build();

        given(manageTableUseCase.createTable(any())).willReturn(100L);
        given(manageTableUseCase.getTable(100L)).willReturn(Optional.of(table));

        // when / then
        mockMvc.perform(post("/api/restaurants/{id}/tables", restaurantId)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(100L))
                .andExpect(jsonPath("$.restaurantId").value(10L))
                .andExpect(jsonPath("$.tableNumber").value(3))
                .andExpect(jsonPath("$.capacity").value(6))
                .andExpect(jsonPath("$.available").value(true));
    }

    @Test
    @DisplayName("[GET] /api/restaurants/{restaurantId}/tables/{tableId} - 특정 테이블 상세 조회")
    void getTable() throws Exception {
        // given
        long restaurantId = 10L;
        long tableId = 100L;
        Table table = Table.builder()
                .id(tableId)
                .restaurantId(restaurantId)
                .tableNumber(5)
                .capacity(4)
                .isAvailable(true)
                .build();

        given(manageTableUseCase.getTable(tableId)).willReturn(Optional.of(table));

        // when / then
        mockMvc.perform(get("/api/restaurants/{rid}/tables/{tid}", restaurantId, tableId)
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100L))
                .andExpect(jsonPath("$.restaurantId").value(10L))
                .andExpect(jsonPath("$.available").value(true));
    }

    @Test
    @DisplayName("[PUT] /api/restaurants/{restaurantId}/tables/{tableId} - 테이블 업데이트")
    void updateTable() throws Exception {
        // given
        long restaurantId = 10L;
        long tableId = 100L;
        TableRequest request = new TableRequest(7, 8, false);

        Table updatedTable = Table.builder()
                .id(tableId)
                .restaurantId(restaurantId)
                .tableNumber(7)
                .capacity(8)
                .isAvailable(false)
                .build();

        given(manageTableUseCase.updateTable(any())).willReturn(Optional.of(updatedTable));

        // when / then
        mockMvc.perform(put("/api/restaurants/{rid}/tables/{tid}", restaurantId, tableId)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tableNumber").value(7))
                .andExpect(jsonPath("$.capacity").value(8))
                .andExpect(jsonPath("$.available").value(false));
    }

    @Test
    @DisplayName("[PUT] /api/restaurants/{restaurantId}/tables/{tableId} - 잘못된 restaurantId이면 404 반환")
    void updateTableWrongRestaurantId() throws Exception {
        // given
        long restaurantId = 99L;
        long tableId = 100L;
        Table table = Table.builder()
                .id(tableId)
                .restaurantId(10L) // 다른 레스토랑
                .tableNumber(7)
                .capacity(8)
                .isAvailable(false)
                .build();

        // Controller의 로직상 updateTable 호출 시점까지는 문제없으나,
        // UseCase가 반환한 table의 restaurantId가 pathVariable의 restaurantId와 다르면 404가 반환됨.
        given(manageTableUseCase.updateTable(any())).willReturn(Optional.of(table));

        TableRequest request = new TableRequest(7, 8, false);

        // when / then
        mockMvc.perform(put("/api/restaurants/{rid}/tables/{tid}", restaurantId, tableId)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("[DELETE] /api/restaurants/{restaurantId}/tables/{tableId} - 테이블 삭제")
    void deleteTable() throws Exception {
        // given
        long restaurantId = 10L;
        long tableId = 100L;
        given(manageTableUseCase.getTable(tableId)).willReturn(Optional.of(
                Table.builder().id(tableId).restaurantId(restaurantId).build()
        ));
        given(manageTableUseCase.deleteTable(tableId)).willReturn(true);

        // when / then
        mockMvc.perform(delete("/api/restaurants/{rid}/tables/{tid}", restaurantId, tableId)
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNoContent());
        verify(manageTableUseCase, times(1)).deleteTable(tableId);
    }

    @Test
    @DisplayName("[DELETE] /api/restaurants/{restaurantId}/tables/{tableId} - 대상 테이블 없음")
    void deleteTable_NotFound() throws Exception {
        // given
        long restaurantId = 10L;
        long tableId = 999L;
        given(manageTableUseCase.getTable(tableId)).willReturn(Optional.empty());

        // when / then
        mockMvc.perform(delete("/api/restaurants/{rid}/tables/{tid}", restaurantId, tableId)
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNotFound());
        verify(manageTableUseCase, never()).deleteTable(anyLong());
    }
}