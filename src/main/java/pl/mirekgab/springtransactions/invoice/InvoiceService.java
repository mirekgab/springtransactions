package pl.mirekgab.springtransactions.invoice;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.mirekgab.springtransactions.order.Order;

@Service
@AllArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public Invoice createInvoice(Order order) {
        Invoice invoice = new Invoice();
        invoice.setOrder(order);
        return invoiceRepository.saveAndFlush(invoice);
    }

}
