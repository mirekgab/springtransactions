package pl.mirekgab.springtransactions.clientorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clientorder")
public class ClientOrderController {
    private final ClientOrderService clientOrderService;

    @Autowired
    public ClientOrderController(ClientOrderService clientOrderService) {
        this.clientOrderService = clientOrderService;
    }

    @GetMapping
    public List<ClientOrder> getAllOrders() {
        return clientOrderService.findAll();
    }

    @GetMapping("/{id}/completed")
    public String completeOrder(@PathVariable Long id) {
        return String.valueOf(clientOrderService.completeOrder(id));
    }
}
