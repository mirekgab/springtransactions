package pl.mirekgab.springtransactions.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public void create(long orderId) {
        System.out.println("create invoice");
    }

    public Invoice save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }
}
