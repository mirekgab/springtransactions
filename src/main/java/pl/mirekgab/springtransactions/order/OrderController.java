package pl.mirekgab.springtransactions.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clientorder")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderDTO> getAllOrders() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public OrderDTO getById(@PathVariable Long id) {
        return orderService.findOrderById(id);
    }

    @GetMapping("/{id}/completed")
    public String completeOrder(@PathVariable Long id) {
        return String.valueOf(orderService.completeOrder(id));
    }
}
