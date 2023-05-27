package pl.mirekgab.springtransactions.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.mirekgab.springtransactions.invoice.InvoiceRepository;
import pl.mirekgab.springtransactions.invoiceitem.InvoiceItemRepository;
import pl.mirekgab.springtransactions.orderitem.OrderItemRepository;
import pl.mirekgab.springtransactions.stock.StockRepository;
import pl.mirekgab.springtransactions.stockquantity.StockQuantityRepository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class OrderControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private InvoiceItemRepository invoiceItemRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private StockQuantityRepository stockQuantityRepository;

    @Test
    @DisplayName("order completed successfully")
    @Sql(scripts={"classpath:clean-tables.sql", "classpath:data-completeTheOrderNo1.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void orderCompletedSuccessfully() throws Exception {
        //given
        long orderId=1;
        OrderStatus orderStatusBefore = orderRepository.findById(orderId).get().getStatus();
        OrderStatus expectedOrderStatus = OrderStatus.COMPLETED;
        int expectedStockQuantityProductId1 = 8;
        int expectedStockQuantityProductId2 = 10;
        int expectedStockQuantityProductId3 = 10;
        long expectedInvoiceNumber = 1;
        long expectedInvoiceItemNumber = 3;
        //when
        ResultActions perform = mockMvc.perform(
                        MockMvcRequestBuilders.get("/clientorder/"+orderId+"/completed")
                                .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk());

        //then
        assertAll(
                () -> assertEquals(
                        OrderStatus.APPROVED,
                        orderStatusBefore),
                () -> assertEquals(
                        expectedOrderStatus,
                        orderRepository.findById(orderId).get().getStatus(), "order status"),
                () -> assertEquals(
                        expectedInvoiceNumber,
                        invoiceRepository.count(), "invoice count"),
                () -> assertEquals(
                        expectedInvoiceItemNumber,
                        invoiceItemRepository.count(), "invoice items count"),
                () -> assertEquals(
                        expectedStockQuantityProductId1,
                        stockQuantityRepository.findByStockIdAndProductId(1L, 1L).get().getQuantity(),
                        "stock quantity for product 1"),
                () -> assertEquals(
                        expectedStockQuantityProductId2,
                        stockQuantityRepository.findByStockIdAndProductId(1L, 2L).get().getQuantity(),
                        "stock quantity for product 2"),
                () -> assertEquals(
                        expectedStockQuantityProductId3,
                        stockQuantityRepository.findByStockIdAndProductId(1L, 3L).get().getQuantity(),
                        "stock quantity for product 3")
        );
    }

    @Test
    @DisplayName("insufficient quantity, throw an exception")
    @Sql(scripts={"classpath:clean-tables.sql", "classpath:data-completeTheOrderNo2.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void insufficientQuantityInOneOrderPosition() throws Exception {
        //given
        long orderId=1;
        OrderStatus orderStatusBefore = orderRepository.findById(orderId).get().getStatus();
        int stockQuantityProductId1 = stockQuantityRepository
                .findByStockIdAndProductId(1L, 1L).get().getQuantity();
        int stockQuantityProductId2 = stockQuantityRepository
                .findByStockIdAndProductId(1L, 2L).get().getQuantity();
        int stockQuantityProductId3 = stockQuantityRepository
                .findByStockIdAndProductId(1L, 3L).get().getQuantity();
        long invoiceNumber = invoiceRepository.count();
        long invoiceItemNumber = invoiceItemRepository.count();
        //when
        ResultActions perform = mockMvc.perform(
                        MockMvcRequestBuilders.get("/clientorder/"+orderId+"/completed")
                                .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isUnprocessableEntity());

        //then
        assertAll(
                () -> assertEquals(
                        orderStatusBefore,
                        orderRepository.findById(orderId).get().getStatus(), "order status"),
                () -> assertEquals(
                        invoiceNumber,
                        invoiceRepository.count(), "invoice count"),
                () -> assertEquals(
                        invoiceItemNumber,
                        invoiceItemRepository.count(), "invoice items count"),
                () -> assertEquals(
                        stockQuantityProductId1,
                        stockQuantityRepository.findByStockIdAndProductId(1L, 1L).get().getQuantity(),
                        "stock quantity for product 1"),
                () -> assertEquals(
                        stockQuantityProductId2,
                        stockQuantityRepository.findByStockIdAndProductId(1L, 2L).get().getQuantity(),
                        "stock quantity for product 2"),
                () -> assertEquals(
                        stockQuantityProductId3,
                        stockQuantityRepository.findByStockIdAndProductId(1L, 3L).get().getQuantity(),
                        "stock quantity for product 3")
        );
    }
}