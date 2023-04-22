package pl.mirekgab.springtransactions.order;

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
@Sql(scripts = "classpath:clean-tables.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:data1.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class OrderControllerTest {

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

//    @BeforeAll
//    public static void beforeAll(@Autowired DataSource dataSource,
//                          @Autowired PlatformTransactionManager transactionManager) {
//        new TransactionTemplate(transactionManager).execute((ts) -> {
//                    try (Connection conn = dataSource.getConnection()) {
//                        ScriptUtils.executeSqlScript(conn, new ClassPathResource("clean-tables.sql"));
//                    } catch (SQLException e) {
//                        throw new RuntimeException(e);
//                    }
//                    return null;
//                }
//        );
//    }

    @Test
    void completeTheOrder() throws Exception {
        //given
        int orderId=1;
        int stockQuantityProductId1 = stockQuantityRepository.findByStockIdAndProductId(1L, 1L).get().getQuantity();
        int stockQuantityProductId2 = stockQuantityRepository.findByStockIdAndProductId(1L, 2L).get().getQuantity();
        int stockQuantityProductId3 = stockQuantityRepository.findByStockIdAndProductId(1L, 3L).get().getQuantity();
        long orderItemNumber = orderItemRepository.count();
        long invoiceItemNumber = invoiceItemRepository.count();
        long invoiceNumber = invoiceRepository.count();
        //when
        ResultActions perform = mockMvc.perform(
                        MockMvcRequestBuilders.get("/clientorder/"+orderId+"/completed")
                                .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isUnprocessableEntity());

        //then
        assertAll(
                () -> assertEquals(orderItemNumber, orderItemRepository.count(), "order items count"),
                () -> assertEquals(invoiceItemNumber, invoiceItemRepository.count(), "invoice items count"),
                () -> assertEquals(invoiceNumber, invoiceRepository.count(), "invoice count"),
                () -> assertEquals(stockQuantityProductId1,
                        stockQuantityRepository.findByStockIdAndProductId(1L, 1L).get().getQuantity(),
                        "stock quantity for product 1"),
                () -> assertEquals(stockQuantityProductId2,
                        stockQuantityRepository.findByStockIdAndProductId(1L, 2L).get().getQuantity(),
                        "stock quantity for product 2"),
                () -> assertEquals(stockQuantityProductId3,
                        stockQuantityRepository.findByStockIdAndProductId(1L, 3L).get().getQuantity(),
                        "stock quantity for product 3")
        );
        System.out.println("hello");
    }

}