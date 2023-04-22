package pl.mirekgab.springtransactions.invoiceitem;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.mirekgab.springtransactions.errorhandler.AppRuntimeException;
import pl.mirekgab.springtransactions.invoice.Invoice;
import pl.mirekgab.springtransactions.orderitem.OrderItem;
import pl.mirekgab.springtransactions.stockquantity.StockQuantityService;

@Service
@AllArgsConstructor
public class InvoiceItemService {
    private final InvoiceItemRepository invoiceItemRepository;
    private final StockQuantityService stockQuantityService;

    public InvoiceItem save(InvoiceItem invoiceItem) {
        return invoiceItemRepository.saveAndFlush(invoiceItem);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public InvoiceItem createInvoiceItemFromOrderItem(Invoice invoice, OrderItem item) {
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoice(invoice);
        invoiceItem.setProduct(item.getProduct());
        invoiceItem.setStock(item.getStock());

        int availableQuantity = stockQuantityService.availableQuantity(
                invoiceItem.getStock().getId(),
                invoiceItem.getProduct().getId());
        if (availableQuantity < item.getQuantity()) {
            throw new AppRuntimeException(String.format("quantity in stock %d is less than required %d", availableQuantity, item.getQuantity()));
        }
        //check quantity in stock
        invoiceItem.setQuantity(item.getQuantity());
        invoiceItem.setNet(item.getNet());
        return save(invoiceItem);
    }
}
