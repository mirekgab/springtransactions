package pl.mirekgab.springtransactions.order;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
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

    public int completeOrder(long orderId) {
        log.info("start complete the order");
        createInvoice(orderId);
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException(String.format("order with id=%d not exists", orderId)));
        order.setStatus(OrderStatus.COMPLETED);

        //change order status to complete
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

    private void createInvoice(long orderId) {
        log.info("start cerate invoice");
        Order order = findById(orderId);

        Invoice invoice = new Invoice();
        invoice.setOrder(order);

        Invoice savedInvoice = invoiceService.save(invoice);

        for (OrderItem item : order.getOrderItemSet()) {
            createInvoiceItem(savedInvoice, item);
        }
        log.info("finished create invoice");
    }

    private void createInvoiceItem(Invoice invoice, OrderItem item) {
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoice(invoice);
        invoiceItem.setProduct(item.getProduct());
        invoiceItem.setStock(item.getStock());

        int availableQuantity = stockQuantityService.availableQuantity(
                invoiceItem.getStock().getId(),
                invoiceItem.getProduct().getId());
        if (availableQuantity < item.getQuantity()) {
            throw new RuntimeException(String.format("quantity in stock %d is less than required %d", availableQuantity, item.getQuantity()));
        }
        //check quantity in stock
        invoiceItem.setQuantity(item.getQuantity());
        invoiceItem.setNet(item.getNet());

        InvoiceItem savedInvoiceItem = invoiceItemService.save(invoiceItem);

        stockQuantityService.decreaseQuanity(savedInvoiceItem.getProduct().getId(), savedInvoiceItem.getStock().getId(), savedInvoiceItem.getQuantity());
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
