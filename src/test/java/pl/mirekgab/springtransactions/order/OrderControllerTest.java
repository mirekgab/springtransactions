package pl.mirekgab.springtransactions.order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.mirekgab.springtransactions.orderitem.OrderItemRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
//@Sql(scripts = "classpath:data.sql")
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    void completeTheOrder() throws Exception {

        ResultActions perform = mockMvc.perform(
                        MockMvcRequestBuilders.get("/clientorder/1/completed")
                                .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk());

        System.out.println(perform.andReturn().getResponse().getContentAsString());
        System.out.println("end test");

    }
}