package pl.mirekgab.springtransactions.clientorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mirekgab.springtransactions.invoice.Invoice;
import pl.mirekgab.springtransactions.invoice.InvoiceService;
import pl.mirekgab.springtransactions.orderitem.OrderItem;
import pl.mirekgab.springtransactions.stockquantity.StockQuantityService;

import java.util.List;

@Service
public class ClientOrderService {
    private final ClientOrderRepository clientOrderRepository;
    private final InvoiceService invoiceService;
    private final StockQuantityService stockQuantityService;

    @Autowired
    public ClientOrderService(ClientOrderRepository clientOrderRepository, InvoiceService invoiceService, StockQuantityService stockQuantityService) {
        this.clientOrderRepository = clientOrderRepository;
        this.invoiceService = invoiceService;
        this.stockQuantityService = stockQuantityService;
    }

    public int completeOrder(long orderId) {
        System.out.println("start complete the order");
        createInvoice(orderId);


        System.out.println("change quantity and value in stock");
        System.out.println("approve an invoice");
        System.out.println("order completed");
        return 1;
    }

    private void createInvoice(long orderId) {
        ClientOrder clientOrder = findById(orderId);
        System.out.println("number of items: "+clientOrder.getOrderItemSet().size());

        Invoice invoice = new Invoice();
        invoice.setClientOrder(clientOrder);

        Invoice savedInvoice = invoiceService.save(invoice);
        for (OrderItem item : clientOrder.getOrderItemSet()) {

        }
    }

    public ClientOrder findById(Long orderId) {
        return clientOrderRepository.findById(orderId).orElseThrow(
                () -> new RuntimeException(
                        String.format("order with id %d not found", orderId)
                ));
    }
    public List<ClientOrder> findAll() {
        return clientOrderRepository.findAll();
    }
}
