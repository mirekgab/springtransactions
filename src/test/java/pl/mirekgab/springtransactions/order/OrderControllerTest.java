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
import pl.mirekgab.springtransactions.invoiceitem.InvoiceItemRepository;
import pl.mirekgab.springtransactions.orderitem.OrderItemRepository;
import pl.mirekgab.springtransactions.stock.StockRepository;

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

        ResultActions perform = mockMvc.perform(
                        MockMvcRequestBuilders.get("/clientorder/1/completed")
                                .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isUnprocessableEntity());

        assertAll(
                () -> assertEquals(3L, orderItemRepository.count(), "order items count"),
                () -> assertEquals(0L, invoiceItemRepository.count(), "invoice items count")
        );
        System.out.println("hello");
    }

}