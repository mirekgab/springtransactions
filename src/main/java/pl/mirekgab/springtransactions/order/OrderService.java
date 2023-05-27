package pl.mirekgab.springtransactions.order;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.mirekgab.springtransactions.invoice.Invoice;
import pl.mirekgab.springtransactions.invoice.InvoiceService;
import pl.mirekgab.springtransactions.invoiceitem.InvoiceItem;
import pl.mirekgab.springtransactions.invoiceitem.InvoiceItemService;
import pl.mirekgab.springtransactions.orderitem.OrderItem;
import pl.mirekgab.springtransactions.stockquantity.StockQuantityService;

import java.util.List;

@Service
@AllArgsConstructor
@Log
public class OrderService {
    private final OrderRepository orderRepository;
    private final InvoiceService invoiceService;
    private final InvoiceItemService invoiceItemService;
    private final StockQuantityService stockQuantityService;
    private final OrderToOrderDTOMapper mapper;

    @Transactional(propagation = Propagation.REQUIRED)
    public int completeOrder(long orderId) {
        log.info("start complete the order");
        createInvoiceAndChangeStockQuantity(orderId);
        changeStatus(orderId, OrderStatus.COMPLETED);
        log.info("order completed");
        return 1;
    }

    private void changeStatus(long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException(String.format("order with id=%d not exists", orderId)));
        order.setStatus(status);
        orderRepository.save(order);
    }

    private void createInvoiceAndChangeStockQuantity(long orderId) {
        log.info("start cerate invoice");
        Order order = findById(orderId);
        Invoice savedInvoice = invoiceService.createInvoice(order);
        createInvoiceItemAndChangeStockQuantity(savedInvoice, order);
        log.info("finished create invoice");
    }

    private void createInvoiceItemAndChangeStockQuantity(Invoice invoice, Order order) {
        for (OrderItem item : order.getOrderItemSet().stream().sorted().toList()) {
            InvoiceItem savedInvoiceItem = invoiceItemService.createInvoiceItemFromOrderItem(invoice, item);
            stockQuantityService.decreaseQuanity(savedInvoiceItem.getProduct().getId(), savedInvoiceItem.getStock().getId(), savedInvoiceItem.getQuantity());
        }
    }

    public Order findById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new RuntimeException(
                        String.format("order with id %d not found", orderId)
                ));
    }

    public OrderDTO findOrderById(Long orderId) {
        Order order = this.findById(orderId);
        return mapper.mapEntityToDTO(order);
    }

    public List<OrderDTO> findAll() {
        return mapper.mapEntityListToDTOList(orderRepository.findAll());
    }
}
