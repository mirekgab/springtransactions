package pl.mirekgab.springtransactions.invoice;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.mirekgab.springtransactions.order.Order;

@Service
@AllArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    //@Transactional(label = "transaction-invoice-service-save", propagation = Propagation.MANDATORY)
    public Invoice save(Invoice invoice) {
        return invoiceRepository.saveAndFlush(invoice);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public Invoice createInvoice(Order order) {
        Invoice invoice = new Invoice();
        invoice.setOrder(order);
        return this.save(invoice);
    }
}
