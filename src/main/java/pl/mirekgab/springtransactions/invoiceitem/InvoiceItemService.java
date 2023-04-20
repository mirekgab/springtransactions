package pl.mirekgab.springtransactions.invoiceitem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceItemService {
    private final InvoiceItemRepository invoiceItemRepository;

    @Autowired
    public InvoiceItemService(InvoiceItemRepository invoiceItemRepository) {
        this.invoiceItemRepository = invoiceItemRepository;
    }

    public InvoiceItem save(InvoiceItem invoiceItem) {
        return invoiceItemRepository.saveAndFlush(invoiceItem);
    }
}
